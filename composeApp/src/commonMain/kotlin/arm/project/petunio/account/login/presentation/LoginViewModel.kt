package arm.project.petunio.account.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arm.project.petunio.account.data.AuthRepository
import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.AuthModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Empty())

    val loginResponse: StateFlow<Resource<Boolean>>
        get() = _loginResponse

    fun login(email: String, password: String) {
        try {
            _loginResponse.value = Resource.Fetching()
            val authCredentials =
                AuthModel(email = email, password = password)
            viewModelScope.launch {
                _loginResponse.value = repository.login(authCredentials)
            }
        } catch (error: Exception) {
            _loginResponse.value = Resource.Error(message = error.message)
        }
    }

}
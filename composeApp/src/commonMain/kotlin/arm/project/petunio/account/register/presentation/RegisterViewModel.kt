package arm.project.petunio.account.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arm.project.petunio.account.data.AuthRepository
import arm.project.petunio.account.data.UserRepository
import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.AuthModel
import arm.project.petunio.models.Role
import arm.project.petunio.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _registerResponse: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Empty())
    private val _userResponse: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Empty())

    val registerResponse: StateFlow<Resource<String>>
        get() = _registerResponse
    val userResponse: StateFlow<Resource<Boolean>>
        get() = _userResponse


    fun register(username: String, email: String, password: String) {
        try {
            _registerResponse.value = Resource.Fetching()
            val authCredentials =
                AuthModel(email = email, password = password)
            viewModelScope.launch {
                val response = authRepository.register(authCredentials)
                _registerResponse.value = response

                if (response.data != null)
                _userResponse.value = userRepository.createUser(
                    createNewUserMap(
                        uid = response.data,
                        email = email,
                        username = username
                    )
                )
            }
        } catch (error: Exception) {
            _registerResponse.value = Resource.Error(message = error.message)
        }
    }

    fun createNewUserMap(uid: String, email: String, username: String): User {
        val isoTime = Clock.System.now().toString()

        return User(
            uid = uid,
            username = username,
            email = email,
            role = Role.User,
            createdAt = isoTime,
            pfpUrl = null
        )
    }

}
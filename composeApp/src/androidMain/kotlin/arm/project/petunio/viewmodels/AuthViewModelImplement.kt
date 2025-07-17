package arm.project.petunio.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arm.project.petunio.models.Role
import arm.project.petunio.models.User
import arm.project.petunio.repositories.AuthRepository
import arm.project.petunio.repositories.UserRepository
import arm.project.petunio.services.provideAuthRepository
import arm.project.petunio.services.provideUserRepository
import arm.project.petunio.viewmodels.states.LoginState
import arm.project.petunio.viewmodels.states.RegisterState
import arm.project.petunio.viewmodels.states.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock

class AuthViewModelImplement (
    private val userRepository: UserRepository = provideUserRepository(),
    private val authRepository: AuthRepository = provideAuthRepository()
): ViewModel(), AuthViewModel {

    private val _userState = MutableStateFlow(UserState())
    override val userState: StateFlow<UserState> = _userState
    private val _registerState = MutableStateFlow(RegisterState())
    override val registerState: StateFlow<RegisterState> = _registerState
    private val _loginState = MutableStateFlow(LoginState())
    override val loginState: StateFlow<LoginState> = _loginState

    override fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState(isLoading = true)
            try {
                val result = authRepository.register(email, password)

                val currentTime = Clock.System.now()

                userRepository.createUser(
                    User(
                        uid = result,
                        username = username,
                        email = email,
                        role = Role.User,
                        createdAt = currentTime,
                        pfpUrl = ""
                    )
                )

                _registerState.value = RegisterState(
                    isLoading = false,
                    isSuccess = true
                )

            } catch (e: Exception) {
                _registerState.value = RegisterState(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred during user registration"
                )
            }
        }
    }

    override fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)
            try {
                authRepository.login(email, password)

                loadCurrentUser()
                _loginState.value = LoginState(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _loginState.value = LoginState(errorMessage = e.message)
            }
        }
    }

    override fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _userState.value = UserState(user = null)
        }
    }

    override fun loadCurrentUser() {
        viewModelScope.launch {
            _userState.value = UserState(isLoading = true)
            try {
                val user = userRepository.getUser()
                _userState.value = UserState(user = user, isSuccess = true, isLoading = false)
            } catch (e: Exception) {
                _userState.value = UserState(errorMessage = e.message)
            }
        }
    }
}
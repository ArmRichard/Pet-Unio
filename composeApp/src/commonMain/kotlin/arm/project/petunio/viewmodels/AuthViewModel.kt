package arm.project.petunio.viewmodels

import arm.project.petunio.models.User
import arm.project.petunio.viewmodels.states.UserState
import arm.project.petunio.viewmodels.states.RegisterState
import arm.project.petunio.viewmodels.states.LoginState
import kotlinx.coroutines.flow.StateFlow

interface AuthViewModel {
    val userState: StateFlow<UserState>
    val registerState: StateFlow<RegisterState>
    val loginState: StateFlow<LoginState>

    fun register(email: String, password: String, username: String)
    fun login(email: String, password: String)
    fun logout()
    fun loadCurrentUser()

}
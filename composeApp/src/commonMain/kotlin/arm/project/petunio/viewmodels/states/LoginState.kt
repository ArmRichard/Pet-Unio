package arm.project.petunio.viewmodels.states

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

package arm.project.petunio.viewmodels.states

import arm.project.petunio.models.User

data class UserState (
    val user: User? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
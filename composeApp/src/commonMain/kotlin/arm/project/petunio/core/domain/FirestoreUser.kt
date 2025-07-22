package arm.project.petunio.core.domain

data class FirestoreUser (
    val username: String? = null,
    val email: String? = null,
    val role: Role = Role.User,
    val createdAt: String? = null,
    val pfpUrl: String? = null
)
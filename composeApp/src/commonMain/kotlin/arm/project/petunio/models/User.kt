package arm.project.petunio.models

import kotlin.time.Instant

data class User (
    val uid: String,
    val username: String? = null,
    val email: String? = null,
    val role: Role = Role.User,
    val createdAt: Instant? = null,
    val pfpUrl: String? = null
)
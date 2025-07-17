package arm.project.petunio.repositories

interface AuthRepository {
    suspend fun register(email: String, password: String): String
    suspend fun login(email: String, password: String): String
    fun logout()
    fun currentUserId(): String?
}
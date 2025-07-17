package arm.project.petunio.repositories

import arm.project.petunio.models.User

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun removeUser()
}
package arm.project.petunio.account.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.User

interface UserRepository {
    suspend fun getCurrentUser(): Resource<User>
    suspend fun getUser(uid: String): Resource<User>
    suspend fun createUser(user: User): Resource<Boolean>
    suspend fun updateUser(user: User): Resource<Boolean>
    suspend fun removeUser(): Resource<Boolean>
}

expect fun provideUserRepository(): UserRepository
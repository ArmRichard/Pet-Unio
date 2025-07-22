package arm.project.petunio.account.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.models.User

interface UserRepository {
    suspend fun getUser(): Resource<User>
    suspend fun createUser(user: User): Resource<Boolean>
    suspend fun updateUser(user: User): Resource<Boolean>
    suspend fun removeUser(): Resource<Boolean>
}

expect fun provideUserRepository(): UserRepository
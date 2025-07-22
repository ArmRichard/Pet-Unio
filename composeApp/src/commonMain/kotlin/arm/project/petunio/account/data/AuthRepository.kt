package arm.project.petunio.account.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.AuthModel

interface AuthRepository {
    suspend fun register(authModel: AuthModel): Resource<String>
    suspend fun login(authModel: AuthModel): Resource<Boolean>
    fun logout()
    fun currentUserId(): String?
}

expect fun provideAuthRepository(): AuthRepository
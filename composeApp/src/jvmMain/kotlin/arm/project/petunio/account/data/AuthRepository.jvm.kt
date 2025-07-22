package arm.project.petunio.account.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.AuthModel

interface AuthRepository {
    actual suspend fun register(authModel: AuthModel): Resource<Boolean>
    actual suspend fun login(authModel: AuthModel): Resource<Boolean>
    actual fun logout()
    actual fun currentUserId(): String?
}

actual fun provideAuthRepository() {
}
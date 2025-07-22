package arm.project.petunio.account.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.AuthModel
import arm.project.petunio.services.requireLoggedInUid
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AndroidAuthRepository(): AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun register(authModel: AuthModel): Resource<String> {
        try {
            auth.createUserWithEmailAndPassword(authModel.email, authModel.password).await()

            val uid = requireLoggedInUid()

            return Resource.Success(uid)
        } catch (error: Exception) {
            return Resource.Error(message = error.message)
        }

    }

    override suspend fun login(authModel: AuthModel): Resource<Boolean> {
        try {
            auth.signInWithEmailAndPassword(authModel.email, authModel.password).await()

            return Resource.Success(true)
        } catch (error: Exception) {
            return Resource.Error(message = error.message)
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun currentUserId(): String? {
        return auth.currentUser?.uid
    }

}

actual fun provideAuthRepository(): AuthRepository = AndroidAuthRepository()
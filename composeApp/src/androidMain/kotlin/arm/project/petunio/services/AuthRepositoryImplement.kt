package arm.project.petunio.services

import arm.project.petunio.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual fun provideAuthRepository(): AuthRepository = object : AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun register(email: String, password: String): String =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = it.user?.uid
                    if (uid != null) { cont.resume(uid) }
                    else cont.resumeWithException(IllegalStateException("UID is null"))
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }

    override suspend fun login(email: String, password: String): String =
        suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = it.user?.uid
                    if (uid != null) { cont.resume(uid) }
                    else cont.resumeWithException(IllegalStateException("UID is null"))
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
    }

    override fun logout() {
        auth.signOut()
    }

    override fun currentUserId(): String? {
        return auth.currentUser?.uid
    }

}
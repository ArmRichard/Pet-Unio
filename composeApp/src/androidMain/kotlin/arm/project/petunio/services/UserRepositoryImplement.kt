package arm.project.petunio.services

import android.content.res.Resources
import arm.project.petunio.models.User
import arm.project.petunio.repositories.UserRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resumeWithException

actual fun provideUserRepository(): UserRepository = object : UserRepository {
    private val db = Firebase.firestore
    private val userCollection = db.collection("users")
    private val mapper = provideUserFirestoreMapper()

    override suspend fun getUser(): User? {
        val uid = requireLoggedInUid()

        return suspendCoroutine { cont ->

            userCollection.document(uid).get()
                .addOnSuccessListener { doc ->
                    if (doc != null && doc.exists()) {
                        val user = mapper.fromFirestoreMapToUser(doc.data)
                        cont.resume(user)
                    } else {
                        cont.resumeWithException(
                            Resources.NotFoundException("User document not found")
                        )
                    }
                }
                .addOnFailureListener {
                    cont.resumeWithException(
                        Resources.NotFoundException("Something went wrong while fetching User document.")
                    )
                }
        }
    }

    override suspend fun createUser(user: User) {
        val uid = requireLoggedInUid()

        suspendCoroutine { cont ->
            userCollection.document(uid)
                .set(mapper.fromUserToFirestoreMap(user))
                .addOnSuccessListener { cont.resume(Unit) }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                    return@addOnFailureListener
                }
        }
    }

    override suspend fun updateUser(user: User) {
        val uid = requireLoggedInUid()

        suspendCoroutine { cont ->
            val updates = mutableMapOf<String, Any>()

            user.username?.let { updates["username"] = it }
            user.email?.let { updates["email"] = it }
            user.pfpUrl?.let { updates["pfp_url"] = it }
            user.createdAt?.let { updates["created_at"] = it.toTimestamp() as Timestamp }

            if (updates.isNotEmpty()) {
                userCollection.document(uid).update(updates)
                    .addOnSuccessListener {
                        cont.resume(Unit)
                    }
                    .addOnFailureListener {
                        cont.resumeWithException(it)
                    }
            }
        }
    }

    override suspend fun removeUser() {
        val uid = requireLoggedInUid()

        suspendCoroutine { cont ->
            userCollection.document(uid)
                .delete()
                .addOnSuccessListener {
                    cont.resume(Unit)
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
    }

}
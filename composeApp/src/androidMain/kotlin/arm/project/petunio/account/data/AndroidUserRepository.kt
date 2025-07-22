package arm.project.petunio.account.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.FirestoreUser
import arm.project.petunio.core.domain.User
import arm.project.petunio.services.requireLoggedInUid
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AndroidUserRepository(): UserRepository {
    private val db = Firebase.firestore
    private val userCollection = db.collection("users")

    override suspend fun getCurrentUser(): Resource<User> {
        return try {
            val uid = requireLoggedInUid()

            val snapshot = userCollection.document(uid).get().await()

            val user = snapshot.toObject(User::class.java)

            if (snapshot.exists()) {
                if (user != null) Resource.Success(user)
                else Resource.Error(message = "User data is null")
            } else {
                Resource.Error(message = "Could not find user")
            }
        } catch (error: Exception) {
            Resource.Error(message = error.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getUser(uid: String): Resource<User> {
        return try {
            val snapshot = userCollection.document(uid).get().await()

            val user = snapshot.toObject(User::class.java)

            if (snapshot.exists()) {
                if (user != null) Resource.Success(user)
                else Resource.Error(message = "User data is null")
            } else {
                Resource.Error(message = "Could not find user")
            }
        } catch (error: Exception) {
            Resource.Error(message = error.message ?: "An unknown error occurred")
        }
    }

    override suspend fun createUser(user: User): Resource<Boolean> {

        return try {
            val uid = requireLoggedInUid()

            if (uid == user.uid) {
                val firestoreUser = FirestoreUser(
                    username = user.username,
                    email = user.email,
                    role = user.role,
                    createdAt = user.createdAt,
                    pfpUrl = user.pfpUrl
                )

                userCollection.document(uid)
                    .set(firestoreUser)
                    .await()

                Resource.Success(true)
            } else {
                Resource.Error(message = "User id does not match the current signed in user.")
            }
        } catch (error: Exception) {
            Resource.Error(message = "Failed to create user: " +
                    (error.message ?: "an unknown error occurred"))
        }
    }

    override suspend fun updateUser(user: User): Resource<Boolean> {
        return try {
            val uid = requireLoggedInUid()
            val updates = mutableMapOf<String, Any>()

            user.username?.let { updates["username"] = it }
            user.email?.let { updates["email"] = it }
            user.createdAt?. let { updates["created_at"] = it }
            user.pfpUrl?.let { updates["pfp_url"] = it }

            if (updates.isNotEmpty()) {
                userCollection.document(uid)
                    .update(updates)
                    .await()

                Resource.Success(true)
            } else {
                Resource.Error(message = "No updates have been made")
            }
        } catch (error: Exception) {
            Resource.Error(message = error.message)
        }
    }

    override suspend fun removeUser(): Resource<Boolean> {
        return try {
            val uid = requireLoggedInUid()

            userCollection.document(uid)
                .delete()
                .await()

            Resource.Success(true)
        } catch (error: Exception) {
            Resource.Error(message = error.message)
        }
    }

}

actual fun provideUserRepository(): UserRepository = AndroidUserRepository()
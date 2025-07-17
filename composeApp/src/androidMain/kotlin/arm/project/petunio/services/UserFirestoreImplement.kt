package arm.project.petunio.services

import arm.project.petunio.models.Role
import arm.project.petunio.models.User
import arm.project.petunio.repositories.UserFirestoreMapper
import com.google.firebase.Timestamp
import kotlin.time.Clock

actual fun provideUserFirestoreMapper(): UserFirestoreMapper = object : UserFirestoreMapper {

    override fun fromUserToFirestoreMap(user: User): Map<String, Any?> {
        return mapOf(
            "uid" to user.uid,
            "username" to user.username,
            "email" to user.email,
            "role" to user.role,
            "created_at" to user.createdAt?.toTimestamp(),
            "pfp_url" to ""
        )
    }

    override fun fromFirestoreMapToUser(map: Map<String?, Any?>?): User? {
        return User(
            uid = map?.get("uid") as? String ?: return null,
            username = map["username"] as? String ?: "",
            email = map["email"] as? String ?: "",
            role = map["role"] as? Role ?: Role.User,
            createdAt = (map["created_at"] as? Timestamp?)?.toTimeInstant() ?: Clock.System.now(),
            pfpUrl = map["pfp_url"] as? String ?: ""
        )
    }
}
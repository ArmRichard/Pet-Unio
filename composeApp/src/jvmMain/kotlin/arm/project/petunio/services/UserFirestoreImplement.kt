package arm.project.petunio.repositories

import arm.project.petunio.models.User

class UserFirestoreImplement : UserFirestoreMapper {
    override fun fromUserToFirestoreMap(user: User): Map<String, Any> {
        // simple stub implementation
        return mapOf("uid" to user.uid)
    }

    override fun fromFirestoreMapToUser(map: Map<String, Any>): User? {
        // stub returns null or simple user
        return null
    }
}

package arm.project.petunio.repositories

import arm.project.petunio.models.User

interface UserFirestoreMapper {

    fun fromUserToFirestoreMap(user: User): Map<String, Any?>
    fun fromFirestoreMapToUser(map: Map<String?, Any?>?): User?
}
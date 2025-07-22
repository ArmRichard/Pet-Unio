package arm.project.petunio

import arm.project.petunio.core.domain.User
import arm.project.petunio.services.provideUserFirestoreMapper
import kotlin.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class TestPrints {

    @Test
    fun main() {
        val mapper = provideUserFirestoreMapper()

        val originalUser = User(
            uid = "abc123",
            username = "johndoe",
            email = "john@example.com",
            role = "user",
            createdAt = Instant.DISTANT_PAST,
            pfpUrl = "http://example.com/image.jpg"
        )

        val firestoreMap = mapper.fromUserToFirestoreMap(originalUser)
        println("Firestore Map: $firestoreMap")

        val restoredUser = mapper.fromFirestoreMapToUser(firestoreMap)
        println("Restored User: $restoredUser")

        assertEquals(originalUser, restoredUser)
    }
}
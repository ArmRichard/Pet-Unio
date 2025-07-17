package arm.project.petunio

import arm.project.petunio.models.User
import arm.project.petunio.services.provideUserFirestoreMapper
import kotlin.time.Clock


class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun main() {
        println("Starting map...")
        val mapper = provideUserFirestoreMapper()

        val originalUser = User(
            uid = "abc123",
            username = "johndoe",
            email = "john@example.com",
            role = "user",
            createdAt = Clock.System.now(),
            pfpUrl = "http://example.com/image.jpg"
        )

        println("Original User: $originalUser")

        val firestoreMap = mapper.fromUserToFirestoreMap(originalUser)
        println("Firestore Map: ${firestoreMap.values}")

        val restoredUser = mapper.fromFirestoreMapToUser(firestoreMap)
        println("Restored User: $restoredUser")

        if (restoredUser == null) {
            return
        }

        if ( originalUser == restoredUser) {
            println("\nConversion has succeeded!")
        } else {
            println("\nConversion failed miserably.")
        }
    }

}
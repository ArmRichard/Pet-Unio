package arm.project.petunio.core.domain

data class FirestoreNote(
    val title: String? = null,
    val content: String? = null,
    val createdAt: String? = null,
    val authorId: String
)

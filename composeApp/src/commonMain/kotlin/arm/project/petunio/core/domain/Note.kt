package arm.project.petunio.core.domain

data class Note(
    val id: String,
    val title: String? = null,
    val content: String? = null,
    val createdAt: String? = null,
    val authorId: String
)

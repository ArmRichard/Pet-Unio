package arm.project.petunio.notes.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.FirestoreNote
import arm.project.petunio.core.domain.Note
import arm.project.petunio.services.requireLoggedInUid
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AndroidNoteRepository: NoteRepository {

    private val db = Firebase.firestore

    private val noteCollection = db.collection("notes")

    override suspend fun getAllNotes(): Resource<List<Note>> {
        return try {
            val result = noteCollection.get().await()

            val notes = result.documents.mapNotNull { doc ->
                val note = doc.toObject(Note::class.java)
                note?.copy(id = doc.id)
            }

            Resource.Success(notes)
        } catch (error: Exception) {
            Resource.Error(message = error.message)
        }
    }

    override suspend fun createNote(note: FirestoreNote): Resource<Boolean> {
        return try {
            requireLoggedInUid()

            val noteRef = noteCollection.document()

            noteRef.set(note).await()

            Resource.Success(true)
        } catch (error: Exception) {
            Resource.Error(message = "Failed to create note: " +
                    (error.message ?: "an unknown error occurred"))
        }
    }

    override suspend fun updateNote(note: Note): Resource<Boolean> {
        return try {
            val uid = requireLoggedInUid()

            if (uid == note.authorId) {
                val updates = mutableMapOf<String, Any>()

                note.title?.let { updates["title"] = it }
                note.content?.let { updates["content"] = it }

                if (updates.isNotEmpty()) {
                    noteCollection.document(note.id)
                        .update(updates)
                        .await()

                    Resource.Success(true)
                } else {
                    Resource.Error(message = "No updates have been made")
                }
            } else {
                Resource.Error(message = "Note can only be edited by the author", data = false)
            }
        } catch (error: Exception) {
            Resource.Error(message = error.message)
        }
    }

    override suspend fun removeNote(note: Note): Resource<Boolean> {
        return try {
            val uid = requireLoggedInUid()

            if (uid == note.authorId) {
                noteCollection.document(note.id)
                    .delete().await()

                Resource.Success(true)
            } else {
                Resource.Error(message = "Note can only be deleted by the author", data = false)
            }
        } catch (error: Exception) {
            Resource.Error(message = error.message)
        }
    }
}

actual fun provideNoteRepository(): NoteRepository = AndroidNoteRepository()
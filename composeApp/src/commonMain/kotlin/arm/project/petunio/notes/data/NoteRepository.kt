package arm.project.petunio.notes.data

import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.domain.FirestoreNote
import arm.project.petunio.core.domain.Note

interface NoteRepository {

    suspend fun getAllNotes(): Resource<List<Note>>
    suspend fun createNote(note: FirestoreNote): Resource<Boolean>
    suspend fun updateNote(note: Note): Resource<Boolean>
    suspend fun removeNote(note: Note): Resource<Boolean>
}

expect fun provideNoteRepository(): NoteRepository
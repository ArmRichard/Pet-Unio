package arm.project.petunio.services

import arm.project.petunio.models.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Date
import kotlin.time.Instant

fun Instant.toTimestamp(): Timestamp? {
    val date = Date(this.toEpochMilliseconds())
    return Timestamp(date)
}

fun Timestamp.toTimeInstant(): Instant? {
    return Instant.fromEpochMilliseconds(this.toDate().time)
}

fun requireLoggedInUid(): String = Firebase.auth.currentUser?.uid
    ?: throw IllegalStateException("No logged-in user found.")
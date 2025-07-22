package arm.project.petunio.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun requireLoggedInUid(): String = Firebase.auth.currentUser?.uid
    ?: throw IllegalStateException("No logged-in user found.")
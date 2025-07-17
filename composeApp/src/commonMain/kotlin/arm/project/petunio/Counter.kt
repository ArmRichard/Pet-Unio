package arm.project.petunio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Counter {

    var counter by mutableStateOf(0)
        private set

    fun increment() {
        counter++
    }
}
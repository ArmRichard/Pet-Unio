package arm.project.petunio.account.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import arm.project.petunio.core.presentation.Colors

@Composable
fun Button(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(300.dp)
            .padding(1.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Colors.primary,
            backgroundColor = Colors.container
        )
    ) {
        Text(text = text)
    }
}
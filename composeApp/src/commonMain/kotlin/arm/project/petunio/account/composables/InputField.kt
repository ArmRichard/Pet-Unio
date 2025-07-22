package arm.project.petunio.account.composables

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arm.project.petunio.core.presentation.Colors

@Composable
fun InputField(value: String, onChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(text = placeholder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Colors.primary,
            focusedBorderColor = Colors.primary,
            unfocusedBorderColor = Colors.container,
            cursorColor = Colors.secondary,
            focusedLabelColor = Colors.primary,
            unfocusedLabelColor = Colors.secondary
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        ),
        modifier = Modifier.width(300.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        visualTransformation = if (placeholder == "Password") PasswordVisualTransformation() else VisualTransformation.None
    )
}
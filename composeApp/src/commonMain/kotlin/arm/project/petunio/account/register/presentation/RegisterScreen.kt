package arm.project.petunio.account.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import arm.project.petunio.account.composables.Button
import arm.project.petunio.account.composables.InputField
import arm.project.petunio.account.data.provideAuthRepository
import arm.project.petunio.account.data.provideUserRepository
import arm.project.petunio.core.data.Resource
import arm.project.petunio.core.presentation.Colors

class RegisterScreen(
    private val navController: NavHostController
) {

    @Composable
    fun Create() {
        val authRepository = remember { provideAuthRepository() }
        val userRepository = remember { provideUserRepository() }
        val viewModel = remember { RegisterViewModel(authRepository, userRepository) }

        var username: String by remember { mutableStateOf("") }
        var email: String by remember { mutableStateOf("") }
        var password: String by remember { mutableStateOf("") }

        var error: String by remember { mutableStateOf("") }
        var showError: Boolean by remember { mutableStateOf(false) }

        val registerResponse = viewModel.registerResponse.collectAsStateWithLifecycle()
        val userResponse = viewModel.userResponse.collectAsStateWithLifecycle()

        LaunchedEffect(registerResponse.value) {
            when (val response = registerResponse.value) {
                is Resource.Error -> {
                    error = response.message ?: "An unknown error occurred"
                    showError = true
                }

                is Resource.Success -> {
//                    navController.navigate(route = "login") {
//                        popUpTo(0)
//                    }
                    println("Successful register")
                }

                else -> {}
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (registerResponse.value) {
                is Resource.Fetching -> {
                    LoadingView()
                }

                else -> {
                    RegisterForm(
                        username = username,
                        email = email,
                        password = password,
                        onUsernameChange = { username = it },
                        onEmailChange = { email = it },
                        onPasswordChange = { password = it },
                        viewModel
                    )
                }
            }

            if (showError) {
                AlertDialog(
                    onDismissRequest = { showError = false },
                    title = { Text(text = "Something went wrong") },
                    text = { Text(text = error) },
                    shape = RoundedCornerShape(10.dp),
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showError = false
                            }
                        ) {
                            Text(
                                text = "Okay!",
                                color = Colors.secondary
                            )
                        }
                    }
                )
            }
        }

    }

    @Composable
    fun RegisterForm(
        username: String,
        email: String,
        password: String,
        onUsernameChange: (String) -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        viewModel: RegisterViewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(value = username, onChange = onUsernameChange, placeholder = "Username")
            Spacer(modifier = Modifier.padding(5.dp))
            InputField(value = email, onChange = onEmailChange, placeholder = "Email")
            Spacer(modifier = Modifier.padding(5.dp))
            InputField(value = password, onChange = onPasswordChange, placeholder = "Password")
            Spacer(modifier = Modifier.padding(5.dp))
            Button(text = "Register", onClick = { viewModel.register(username, email, password) })
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "Already have an account? Login here.",
                modifier = Modifier
                    .clickable(onClick = { navController.navigate("login") })
                    .pointerHoverIcon(PointerIcon.Hand),
                color = Colors.secondary
            )
        }
    }

    @Composable
    fun LoadingView() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Colors.primary)
        }
    }

}
package arm.project.petunio.account.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import arm.project.petunio.account.data.provideAuthRepository


class LoginScreen() {

    @Composable
    fun Create() {
        val firebaseAuth = remember { provideAuthRepository() }
        val viewModel = remember { LoginViewModel(firebaseAuth) }
    }
}
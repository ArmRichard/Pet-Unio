package arm.project.petunio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import arm.project.petunio.account.register.presentation.RegisterScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

//@Composable
//@Preview
//fun App() {
//    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        val counter = remember { Counter() }
//
//        Column(
//            modifier = Modifier
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = {
//                showContent = !showContent
//                if (showContent) {
//                    counter.increment()
//                }
//                Greeting().main()
//            }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                val counter = remember { counter.counter }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                    Text("You have clicked $counter times")
//                }
//            }
//        }
//    }
//}

@Composable
@Preview
fun App() {

    var startDestination: String? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        startDestination = "accountRef"
    }

    val navController: NavHostController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavHost(navController = navController, startDestination = startDestination ?: "accountRef") {

            navigation(route = "accountRef", startDestination = "register") {
                composable(route = "register") {
                    RegisterScreen(navController = navController).Create()
                }
            }
        }
    }

}

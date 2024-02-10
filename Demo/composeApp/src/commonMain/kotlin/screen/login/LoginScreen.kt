package screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.nekzabirov.navigatio.common.state.rememberNavController
import com.nekzabirov.viewmodel.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen(mViewModel: LoginViewModel = viewModel(LoginViewModel::class) { LoginViewModel() }) {
    val navController = rememberNavController("main")

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(it)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            val isLoading by produceState(false) {
                mViewModel.state
                    .onEach { value = it is LoginState.Loading }
                    .launchIn(this)
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Row {
                    Button(
                        onClick = { mViewModel.sendEvent(LoginEvent.Register(email, password)) },
                        enabled = email.isNotEmpty() && password.length >= 8
                    ) {
                        Text("Register")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { mViewModel.sendEvent(LoginEvent.Login(email, password)) },
                        enabled = email.isNotEmpty() && password.length >= 8
                    ) {
                        Text("Login")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("phone_auth") }) {
                Text("Phone auth")
            }
        }
    }

    LaunchedEffect(mViewModel) {
        mViewModel.state
            .onEach {
                when (it) {
                    is screen.login.LoginState.LoginFail -> snackbarHostState.showSnackbar("Login fail: ${it.error}")
                    screen.login.LoginState.LoginSuccess -> snackbarHostState.showSnackbar("Login success")
                    is screen.login.LoginState.RegisterFail -> snackbarHostState.showSnackbar("Register fail: ${it.error}")
                    screen.login.LoginState.RegisterSuccess -> snackbarHostState.showSnackbar("Register success")
                    else -> {}
                }
            }
            .launchIn(this)
    }
}
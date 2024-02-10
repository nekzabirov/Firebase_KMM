package screen.phoneauth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nekzabirov.viewmodel.viewModel
import getKActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun PhoneAuthScreen(mViewModel: PhoneAuthViewModel = viewModel(PhoneAuthViewModel::class) { PhoneAuthViewModel() }) {
    val activity = getKActivity()
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
            val state by remember { mViewModel.state }.collectAsState()

            var phoneNumber by remember { mutableStateOf("") }
            var smsCode by remember { mutableStateOf("") }

            TextField(
                phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone number") },
                enabled = state !is PhoneAuthState.Verified
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state is PhoneAuthState.Verified) {
                TextField(
                    smsCode,
                    onValueChange = { smsCode = it },
                    label = { Text("Code") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isLoading by produceState(false) {
                mViewModel.state
                    .onEach { value = it is PhoneAuthState.Loading }
                    .launchIn(this)
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = {
                    if (state is PhoneAuthState.Verified) {
                        mViewModel.sendEvent(
                            PhoneAuthEvent.Confirm(
                                (state as PhoneAuthState.Verified).verificationID,
                                smsCode
                            )
                        )
                    } else {
                        mViewModel.sendEvent(PhoneAuthEvent.Verify(activity, phoneNumber))
                    }
                }, enabled = phoneNumber.isNotEmpty()) {
                    Text("Next")
                }
            }
        }
    }

    LaunchedEffect(mViewModel) {
        mViewModel.state
            .onEach {
                when (it) {
                    is PhoneAuthState.Fail -> snackbarHostState.showSnackbar("Fail: ${it.error}")
                    is PhoneAuthState.Confirmed -> snackbarHostState.showSnackbar("Auth success")
                    else -> {}
                }
            }
            .launchIn(this)
    }
}
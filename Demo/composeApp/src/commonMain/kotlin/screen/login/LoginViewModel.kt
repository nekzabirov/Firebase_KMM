package screen.login

import com.nekzabirov.firebaseauth.KFirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import screen.ViewModel

sealed interface LoginEvent {
    data class Register(val email: String, val password: String): LoginEvent
    data class Login(val email: String, val password: String): LoginEvent
}

sealed interface LoginState {
    data object Empty: LoginState
    data object Loading: LoginState
    data class  RegisterFail(val error: String): LoginState
    data object RegisterSuccess: LoginState
    data class  LoginFail(val error: String): LoginState
    data object LoginSuccess: LoginState
}

class LoginViewModel: ViewModel() {
    private val auth = KFirebaseAuth.instance

    private val _state = MutableStateFlow<LoginState>(LoginState.Empty)
    val state: StateFlow<LoginState> = _state

    private val _event = Channel<LoginEvent>(100)

    init {
        _event.receiveAsFlow()
            .onEach {
                when (it) {
                    is LoginEvent.Login -> login(it.email, it.password)
                    is LoginEvent.Register -> register(it.email, it.password)
                }
            }
            .launchIn(this)
    }

    private fun login(email: String, password: String) = launch(Dispatchers.Unconfined) {
        startLoding()

        try {
            auth.signInWithEmailAndPassword(email, password)
            _state.value = LoginState.LoginSuccess
        } catch (e: Exception) {
            _state.value = LoginState.LoginFail(e.message ?: "Unknown VM")
        }
    }

    private fun register(email: String, password: String) = launch(Dispatchers.Unconfined) {
        startLoding()

        try {
            auth.createUserWithEmailAndPassword(email, password)
            _state.value = LoginState.RegisterSuccess
        } catch (e: Exception) {
            _state.value = LoginState.LoginFail(e.message ?: "Unknown VM")
        }
    }

    private fun startLoding() {
        _state.value = LoginState.Loading
    }

    fun sendEvent(event: LoginEvent) {
        _event.trySend(event)
    }
}
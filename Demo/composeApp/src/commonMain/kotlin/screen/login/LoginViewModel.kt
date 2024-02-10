package screen.login

import com.nekzabirov.firebaseauth.KFirebaseAuth
import com.nekzabirov.viewmodel.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import screen.ViewModel
import kotlin.reflect.KClass

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

class LoginViewModel: ViewModel<LoginState, LoginEvent>(LoginState.Empty) {

    private val auth = KFirebaseAuth.instance

    override fun process(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
            is LoginEvent.Register -> register(event.email, event.password)
        }
    }

    private fun login(email: String, password: String) = launch(Dispatchers.Unconfined) {
        startLoading()

        try {
            auth.signInWithEmailAndPassword(email, password)
            _state.value = LoginState.LoginSuccess
        } catch (e: Exception) {
            _state.value = LoginState.LoginFail(e.message ?: "Unknown VM")
        }
    }

    private fun register(email: String, password: String) = launch(Dispatchers.Unconfined) {
        startLoading()

        try {
            auth.createUserWithEmailAndPassword(email, password)
            _state.value = LoginState.RegisterSuccess
        } catch (e: Exception) {
            _state.value = LoginState.LoginFail(e.message ?: "Unknown VM")
        }
    }

    private fun startLoading() {
        _state.value = LoginState.Loading
    }
}
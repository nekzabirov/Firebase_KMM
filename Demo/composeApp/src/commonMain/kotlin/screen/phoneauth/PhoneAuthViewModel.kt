package screen.phoneauth

import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseauth.KFirebaseAuth
import com.nekzabirov.firebaseauth.provider.phoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import screen.ViewModel

sealed interface PhoneAuthState {
    data object Empty: PhoneAuthState

    data object Loading: PhoneAuthState

    data class Fail(val error: String): PhoneAuthState

    data class Verified(val verificationID: String): PhoneAuthState

    data object Confirmed: PhoneAuthState
}

sealed interface PhoneAuthEvent {
    data class Verify(val activity: KActivity, val phoneNumber: String): PhoneAuthEvent

    data class Confirm(val verificationID: String, val code: String): PhoneAuthEvent
}

class PhoneAuthViewModel: ViewModel<PhoneAuthState, PhoneAuthEvent>(PhoneAuthState.Empty) {
    private val auth = KFirebaseAuth.instance

    init {
        println("Nek PhoneAuthViewModel init")
    }

    override fun process(event: PhoneAuthEvent) {
        when (event) {
            is PhoneAuthEvent.Verify -> verify(event.activity, event.phoneNumber)
            is PhoneAuthEvent.Confirm -> confirm(event.verificationID, event.code)
        }
    }

    private fun verify(activity: KActivity, phoneNumber: String) = launch(Dispatchers.Unconfined) {
        startLoading()

        try {
            val verID = phoneAuthProvider().verifyPhoneNumber(activity, phoneNumber)
            _state.value = PhoneAuthState.Verified(verID)
        } catch (e: Exception) {
            _state.value = PhoneAuthState.Fail(e.message ?: "Unknown")
        }
    }

    private fun confirm(verificationID: String, code: String) = launch(Dispatchers.Unconfined) {
        startLoading()

        try {
            val cred = phoneAuthProvider().getCredential(verificationID, code)
            auth.signInWithCredential(cred)
            _state.value = PhoneAuthState.Confirmed
        } catch (e: Exception) {
            _state.value = PhoneAuthState.Fail(e.message ?: "Unknown")
        }
    }

    private fun startLoading() {
        _state.value = PhoneAuthState.Loading
    }
}
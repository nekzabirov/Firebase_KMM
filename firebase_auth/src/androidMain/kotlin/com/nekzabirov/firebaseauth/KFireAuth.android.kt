package com.nekzabirov.firebaseauth

import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.nekzabirov.firebaseapp.AuthFail
import com.nekzabirov.firebaseauth.user.KFireUser
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class KFireAuth {
    actual inner class Phone {
        actual suspend fun verifyPhoneNumber(phoneNumber: String): String = suspendCoroutine { cont ->
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

                    override fun onVerificationFailed(p0: FirebaseException) {
                        cont.resumeWithException(AuthFail(p0.localizedMessage ?: "Unknown"))
                    }

                    override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        cont.resume(verificationId)
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        actual suspend fun getCredential(verificationID: String, verificationCode: String): KFireCredential {
            return PhoneAuthProvider
                .getCredential(verificationID, verificationCode)
        }

    }

    actual inner class Google internal actual constructor() {
        /*actual suspend fun request(clientID: String): String = suspendCoroutine { cont ->
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .requestProfile()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(activity, gso)

            val startForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode != Activity.RESULT_OK) {
                    cont.resumeWithException(AuthFail("User declined google sing in"))
                    return@registerForActivityResult
                }

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                if (idToken != null)
                    cont.resume(idToken)
                else
                    cont.resumeWithException(AuthFail("Google auth fail"))
            }

            val signInIntent = googleSignInClient.signInIntent

            startForResult.launch(signInIntent)
        }*/

        actual suspend fun getCredential(idToken: String): KFireCredential {
            return GoogleAuthProvider.getCredential(idToken, null)
        }
    }

    actual inner class Facebook internal actual constructor() {
        /*actual suspend fun request(): String = suspendCoroutine { cont ->
            val callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        cont.resume(result.accessToken.token)
                    }

                    override fun onCancel() {
                        cont.resumeWithException(AuthFail("User declined facebook login"))
                    }

                    override fun onError(error: FacebookException) {
                        cont.resumeWithException(AuthFail(error = error.localizedMessage ?: "Unknow"))
                    }
                }
            )

            LoginManager.getInstance()
                .logInWithReadPermissions(activity, listOf("public_profile"))
        }*/

        actual suspend fun getCredential(accessToken: String): KFireCredential {
            return FacebookAuthProvider.getCredential(accessToken)
        }
    }

    actual companion object {
        actual fun getInstance(): KFireAuth {
            return KFireAuth()
        }
    }

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    actual val currentUser: KFireUser?
        get() = firebaseAuth.currentUser?.let {
            KFireUser(it)
        }

    actual val phone: Phone by lazy { Phone() }

    actual val google: Google by lazy { Google() }

    actual val facebook: Facebook by lazy { Facebook() }

    actual suspend fun signInWithCredential(credential: KFireCredential): Boolean {
        val result = firebaseAuth.signInWithCredential(credential)
            .await()

        return result.user != null
    }
}

actual typealias KFireCredential = AuthCredential
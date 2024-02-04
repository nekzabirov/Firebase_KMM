import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseapp.KContext
import com.nekzabirov.firebaseapp.KFirebaseApp
import com.nekzabirov.navigatio.common.host.NavHost
import com.nekzabirov.navigatio.common.state.rememberNavController
import screen.login.LoginScreen
import screen.phoneauth.PhoneAuthScreen

@Composable
fun App() {
    KFirebaseApp.initializeApp(getKContext())

    MaterialTheme {
        val navController = rememberNavController("main")

        NavHost(modifier = Modifier.fillMaxSize(), navigationController = navController, startRoute = "login") {
            composable("login") { LoginScreen() }
            composable("phone_auth") { PhoneAuthScreen() }
        }
    }
}

@Composable
expect fun getKContext(): KContext

@Composable
expect fun getKActivity(): KActivity
import androidx.compose.runtime.Composable
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseapp.KContext
import platform.Foundation.NSUserDefaults

@Composable
actual fun getKContext(): KContext = NSUserDefaults()

@Composable
actual fun getKActivity(): KActivity {
    return MainViewController()
}

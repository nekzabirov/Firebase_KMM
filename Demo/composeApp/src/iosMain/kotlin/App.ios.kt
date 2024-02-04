import androidx.compose.runtime.Composable
import com.nekzabirov.firebaseapp.KContext
import platform.Foundation.NSUserDefaults

@Composable
actual fun getKContext(): KContext = NSUserDefaults()
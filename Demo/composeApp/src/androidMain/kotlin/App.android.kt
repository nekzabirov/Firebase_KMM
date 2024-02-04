import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.nekzabirov.firebaseapp.KActivity
import com.nekzabirov.firebaseapp.KContext

@Composable
actual fun getKContext(): KContext {
    val context = LocalContext.current as ComponentActivity

    return context.application
}

@Composable
actual fun getKActivity(): KActivity {
    val context = LocalContext.current as ComponentActivity

    return context
}
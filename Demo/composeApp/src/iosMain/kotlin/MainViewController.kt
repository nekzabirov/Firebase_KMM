import androidx.compose.ui.window.ComposeUIViewController

private val instance by lazy { ComposeUIViewController { App() } }

fun MainViewController() = instance

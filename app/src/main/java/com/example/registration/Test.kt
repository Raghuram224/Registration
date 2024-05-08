import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.registration.navigation.TEST_KEY

@Composable
fun TestScreen(
    navController: NavController
) {
    Text(text = "Test screen")
    Log.i("test backstack id",navController.currentBackStackEntry?.arguments?.getString(TEST_KEY).toString())


}
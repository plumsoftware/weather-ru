package ru.plumsoftware.weatherforecast.presentation.main.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.authorization.component.AuthorizationComponent
import ru.plumsoftware.weatherforecast.ui.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                AuthorizationScreen(
                    component = AuthorizationComponent(
                        storeFactory = DefaultStoreFactory(),
                        output = ::authOutput
                    )
                )
            }
        }
    }

    private fun authOutput(output: AuthorizationComponent.Output) {
        when (output) {
            AuthorizationComponent.Output.OpenLocationScreen -> Toast.makeText(
                applicationContext,
                "TEST",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @Composable
    private fun MainContent(){

    }
}

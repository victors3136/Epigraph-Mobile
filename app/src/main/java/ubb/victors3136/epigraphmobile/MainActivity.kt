package ubb.victors3136.epigraphmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.bringIntoViewResponder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ubb.victors3136.epigraphmobile.theme.ThemeProvider

@Composable
fun Main() {
    val textColor = ThemeProvider.get().primaryTextColor()
    val backgroundColor = ThemeProvider.get().backgroundColor()
    val buttonColor = ThemeProvider.get().accentColor()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            content = { innerPadding ->
                Column(modifier = Modifier.padding(24.dp)) {
                    Greeting(
                        name = "World",
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                        color = textColor,
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = Color.White
                        ),
                        content = { Text("Toggle theme") },
                        onClick = { ThemeProvider.toggle() }
                    )
                }
            }
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, color: Color = ThemeProvider.get().primaryTextColor()) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = color,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}
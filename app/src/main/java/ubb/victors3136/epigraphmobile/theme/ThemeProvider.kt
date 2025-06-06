package ubb.victors3136.epigraphmobile.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

interface Theme {
    fun backgroundColor(): Color
    fun primaryTextColor(): Color
    fun secondaryTextColor(): Color
    fun accentColor(): Color
}

private object DarkTheme : Theme {
    override fun backgroundColor(): Color = Color(0xFF455A64)

    override fun primaryTextColor(): Color = Color(0xFFD5D4D4)

    override fun secondaryTextColor(): Color = Color(0xFF757575)

    override fun accentColor(): Color = Color(0xFF009688)
}

private object LightTheme: Theme {
    override fun backgroundColor(): Color  = Color(0xFFCFD8DC)

    override fun primaryTextColor(): Color = Color(0xFF212121)

    override fun secondaryTextColor(): Color = Color(0xFF3D3D3D)

    override fun accentColor(): Color = Color(0xFF009688)
}

object ThemeProvider {

    private var darkMode = mutableStateOf(false);

    fun get() : Theme = if(darkMode.value) DarkTheme else LightTheme

    fun toggle() {
        darkMode.value = !darkMode.value
    }
}
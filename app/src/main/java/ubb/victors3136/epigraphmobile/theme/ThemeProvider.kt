package ubb.victors3136.epigraphmobile.theme

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

interface Theme {
    fun primaryBg(): Color
    fun secondaryBg(): Color
    fun primaryText(): Color
    fun secondaryText(): Color
    fun accentColor(): Color
}

private object DarkTheme : Theme {
    override fun primaryBg(): Color = Color(0xFF455A64)

    override fun secondaryBg(): Color = Color(0xFF1A475D)

    override fun primaryText(): Color = Color(0xFFD5D4D4)

    override fun secondaryText(): Color = Color(0xFF757575)

    override fun accentColor(): Color = Color(0xFF006058)
}

private object LightTheme : Theme {
    override fun primaryBg(): Color = Color(0xFFCFD8DC)

    override fun secondaryBg(): Color = Color(0xFF248EBD)

    override fun primaryText(): Color = Color(0xFF212121)

    override fun secondaryText(): Color = Color(0xFF3D3D3D)

    override fun accentColor(): Color = Color(0xFF009688)
}

object ThemeProvider {

    private var darkMode = mutableStateOf(true)

    fun get(): Theme = if (darkMode.value) DarkTheme else LightTheme

    fun toggle() {
        darkMode.value = !darkMode.value
    }
}
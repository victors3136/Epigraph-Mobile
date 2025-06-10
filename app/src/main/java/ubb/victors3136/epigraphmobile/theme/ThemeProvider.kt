package ubb.victors3136.epigraphmobile.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

interface Theme {
    fun primaryBg(): Color
    fun secondaryBg(): Color
    fun primaryText(): Color
    fun secondaryText(): Color
    fun primaryAccent(): Color
    fun secondaryAccent(): Color
    fun recordButtonColor(): Color
}

private object DarkTheme : Theme {
    override fun primaryBg(): Color = Color(0xFF2C393F)

    override fun secondaryBg(): Color = Color(0xFF1A475D)

    override fun primaryText(): Color = Color(0xFFD5D4D4)

    override fun secondaryText(): Color = Color(0xFF757575)

    override fun primaryAccent(): Color = Color(0xFF006058)

    override fun secondaryAccent(): Color = Color(0xFF00603E)

    override fun recordButtonColor(): Color = Color.Red
}

private object LightTheme : Theme {
    override fun primaryBg(): Color = Color(0xFFB2C4CC)

    override fun secondaryBg(): Color = Color(0xFF248EBD)

    override fun primaryText(): Color = Color(0xFF212121)

    override fun secondaryText(): Color = Color(0xFF3D3D3D)

    override fun primaryAccent(): Color = Color(0xFF009688)

    override fun secondaryAccent(): Color = Color(0xFF00965A)

    override fun recordButtonColor(): Color = Color(0xFF940000)
}
enum class ThemeMode {
    DARK,
    LIGHT
}


object ThemeProvider {

    private var mode = mutableStateOf(ThemeMode.DARK)

    fun get(): Theme = when (mode.value) {
        ThemeMode.DARK -> DarkTheme
        ThemeMode.LIGHT -> LightTheme
    }

    fun toggle() {
        mode.value = when(mode.value) {
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.DARK
        }
    }
    fun mode() = mode.value
}
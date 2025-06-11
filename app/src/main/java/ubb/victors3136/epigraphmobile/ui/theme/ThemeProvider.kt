package ubb.victors3136.epigraphmobile.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

interface Theme {
    fun primaryBg(): Color
    fun secondaryBg(): Color
    fun primaryText(): Color
    fun secondaryText(): Color
    fun primaryAccent(): Color
    fun secondaryAccent(): Color
    fun redLike(): Color
    fun greenLike(): Color
}

private object DarkTheme : Theme {
    override fun primaryBg(): Color = Color(0xFF151B1E)

    override fun secondaryBg(): Color = Color(0xFF123342)

    override fun primaryText(): Color = Color(0xFFD5D4D4)

    override fun secondaryText(): Color = Color(0xFF757575)

    override fun primaryAccent(): Color = Color(0xFF006058)

    override fun secondaryAccent(): Color = Color(0xFF00603E)

    override fun redLike(): Color = Color(0xFFBE0000)

    override fun greenLike(): Color = Color(0xFF146E00)
}

private object LightTheme : Theme {
    override fun primaryBg(): Color = Color(0xFFC0D3DC)

    override fun secondaryBg(): Color = Color(0xFF4A7486)

    override fun primaryText(): Color = Color(0xFF212121)

    override fun secondaryText(): Color = Color(0xFF3D3D3D)

    override fun primaryAccent(): Color = Color(0xFF009688)

    override fun secondaryAccent(): Color = Color(0xFF00965A)

    override fun redLike(): Color = Color(0xFF9B0101)

    override fun greenLike(): Color = Color(0xFF0E5200)
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
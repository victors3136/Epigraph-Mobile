package ubb.victors3136.epigraphmobile

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import ubb.victors3136.epigraphmobile.theme.ThemeProvider

@Composable
fun EpigraphTextField(value: String, label: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) },
        label = { EpigraphTextBox(label) },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = ThemeProvider.get().primaryText()
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = ThemeProvider.get().primaryText(),
            focusedBorderColor = ThemeProvider.get().primaryAccent(),
            focusedLabelColor = ThemeProvider.get().primaryAccent(),
            focusedPlaceholderColor = ThemeProvider.get().primaryText(),
            unfocusedTextColor = ThemeProvider.get().secondaryText(),
            unfocusedBorderColor = ThemeProvider.get().secondaryAccent(),
            unfocusedLabelColor = ThemeProvider.get().secondaryAccent(),
            unfocusedPlaceholderColor = ThemeProvider.get().secondaryText(),
            cursorColor = ThemeProvider.get().primaryAccent(),
        )
    )
}
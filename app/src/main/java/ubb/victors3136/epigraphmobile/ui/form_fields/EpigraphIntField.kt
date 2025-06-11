package ubb.victors3136.epigraphmobile.ui.form_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox

@Composable
fun EpigraphIntField(value: String,
                     label: String,
                     modifier: Modifier = Modifier,
                     onChange: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = { newValue: String ->
            if (newValue.all { it.isDigit() }) {
                onChange(newValue)
            }
        },
        label = { EpigraphTextBox(label) },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = ThemeProvider.get().primaryText()
        ),
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Companion.Default.copy(keyboardType = KeyboardType.Companion.Number),
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
package ubb.victors3136.epigraphmobile.ui.form_fields

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox

@Composable
fun EpigraphCheckbox(
    checked: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    onChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChange,
            colors = CheckboxDefaults.colors(
                checkedColor = ThemeProvider.get().primaryAccent(),
                uncheckedColor = ThemeProvider.get().secondaryAccent(),
                checkmarkColor = ThemeProvider.get().primaryText()
            )
        )
        Spacer(modifier = Modifier.Companion.width(8.dp))
        EpigraphTextBox(text = label)
    }
}
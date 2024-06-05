package com.rieg.currencyconverterapp.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rieg.currencyconverterapp.R
import com.rieg.currencyconverterapp.domain.models.ThemeMode

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsUiState by settingsViewModel.settingsUiState.collectAsState()
    SettingsDialog(
        settingsUiState = settingsUiState,
        onDismiss = onDismiss,
        onChangeModeTheme = settingsViewModel::updateThemeMode
    )
}

@Composable
private fun SettingsDialog(
    settingsUiState: SettingsUiState,
    onDismiss: () -> Unit,
    onChangeModeTheme: (ThemeMode) -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.settings_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                ThemeModePanel(
                    themeMode = settingsUiState.themeMode,
                    onChangeModeTheme = onChangeModeTheme
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = stringResource(id = R.string.settings_dismiss_settings_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
                )
        })

}


@Composable
fun ThemeModePanel(
    themeMode: String,
    onChangeModeTheme: (ThemeMode) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.setting_theme_mode_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Column(
            modifier = Modifier.selectableGroup()
        ) {
            ThemeModeChooser(
                text = stringResource(id = R.string.setting_theme_mode_system),
                selected = themeMode == ThemeMode.SYSTEM.name,
                onClick = { onChangeModeTheme(ThemeMode.SYSTEM) }
            )
            ThemeModeChooser(
                text = stringResource(id = R.string.setting_theme_mode_light),
                selected = themeMode == ThemeMode.LIGHT.name,
                onClick = { onChangeModeTheme(ThemeMode.LIGHT) }
            )
            ThemeModeChooser(
                text = stringResource(id = R.string.setting_theme_mode_dark),
                selected = themeMode == ThemeMode.DARK.name,
                onClick = { onChangeModeTheme(ThemeMode.DARK) }
            )
        }
    }
}

@Composable
fun ThemeModeChooser(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsDialogPreview() {
    SettingsDialog(settingsUiState = SettingsUiState(themeMode = "LIGHT"), onDismiss = { /*TODO*/ }) {
        
    }
}
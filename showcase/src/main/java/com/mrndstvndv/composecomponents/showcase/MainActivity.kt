package com.mrndstvndv.composecomponents.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrndstvndv.composecomponents.settings.SettingsDivider
import com.mrndstvndv.composecomponents.settings.SettingsGroup
import com.mrndstvndv.composecomponents.settings.SettingsHeader
import com.mrndstvndv.composecomponents.settings.SettingsNavigationRow
import com.mrndstvndv.composecomponents.settings.SettingsSection
import com.mrndstvndv.composecomponents.settings.SettingsSingleChoiceSegmentedButtons
import com.mrndstvndv.composecomponents.settings.SettingsSliderRow
import com.mrndstvndv.composecomponents.settings.SettingsSwitch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeComponentsTheme {
                ShowcaseScreen()
            }
        }
    }
}

@Composable
private fun ComposeComponentsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        content = content,
    )
}

@Composable
private fun ShowcaseScreen() {
    var isSyncEnabled by rememberSaveable { mutableStateOf(true) }
    var isSoundEnabled by rememberSaveable { mutableStateOf(false) }
    var volume by rememberSaveable { mutableFloatStateOf(0.65f) }
    var selectedMode by rememberSaveable { mutableStateOf("Balanced") }
    var destination by rememberSaveable { mutableStateOf<String?>(null) }

    BackHandler(enabled = destination != null) {
        destination = null
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (destination == null) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                SettingsHeader(
                    title = "Settings components",
                    subtitle = "Reusable Compose settings UI from Search",
                )

                SettingsSection(title = "Navigation rows") {
                    SettingsGroup {
                        SettingsNavigationRow(
                            title = "General",
                            subtitle = "App behavior and defaults",
                            icon = Icons.Outlined.Settings,
                            onClick = { destination = "General" },
                        )
                        SettingsDivider()
                        SettingsNavigationRow(
                            title = "Appearance",
                            subtitle = "Theme and display options",
                            icon = Icons.Outlined.Palette,
                            onClick = { destination = "Appearance" },
                            showBadge = true,
                        )
                        SettingsDivider()
                        SettingsNavigationRow(
                            title = "About",
                            subtitle = "Version and open source licenses",
                            icon = Icons.Outlined.Info,
                            onClick = { destination = "About" },
                            showChevron = false,
                        )
                    }
                }

                SettingsSection(
                    title = "Switches",
                    subtitle = "Optional icon and subtitle, enabled and disabled states",
                ) {
                    SettingsGroup {
                        SettingsSwitch(
                            title = "Background sync",
                            subtitle = "Keep settings up to date across devices",
                            icon = Icons.Outlined.Tune,
                            checked = isSyncEnabled,
                            onCheckedChange = { isSyncEnabled = it },
                        )
                        SettingsDivider()
                        SettingsSwitch(
                            title = "Sound effects",
                            subtitle = "This control is currently unavailable",
                            checked = isSoundEnabled,
                            enabled = false,
                            onCheckedChange = { isSoundEnabled = it },
                        )
                    }
                }

                SettingsSection(title = "Slider rows") {
                    SettingsGroup {
                        SettingsSliderRow(
                            title = "Volume",
                            subtitle = "Adjust in 5% increments",
                            value = volume,
                            onValueChange = { volume = it },
                            steps = 19,
                            valueText = (volume * 100).roundToInt().toString() + "%",
                        )
                        SettingsDivider()
                        SettingsSliderRow(
                            title = "Disabled slider",
                            value = 0.35f,
                            onValueChange = {},
                            valueText = "35%",
                            enabled = false,
                        )
                    }
                }

                SettingsSection(
                    title = "Single choice",
                    subtitle = "Choose a mode",
                ) {
                    SettingsSingleChoiceSegmentedButtons(
                        options = listOf("Quiet", "Balanced", "Fast"),
                        selectedOption = selectedMode,
                        enabled = true,
                        label = { it },
                        onOptionSelected = { selectedMode = it },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        text = "Selected mode: " + selectedMode,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                    SettingsSingleChoiceSegmentedButtons(
                        options = listOf("On", "Off"),
                        selectedOption = "On",
                        enabled = false,
                        label = { it },
                        onOptionSelected = {},
                        modifier = Modifier.fillMaxWidth(),
                        showSelectedIcon = false,
                    )
                }

            }
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
            ) {
                SettingsHeader(
                    title = destination.orEmpty(),
                    subtitle = "Tap back to return to the component showcase",
                    onBack = { destination = null },
                )
                SettingsSection(title = "Example destination") {
                    SettingsGroup {
                        SettingsSwitch(
                            title = "Example preference",
                            subtitle = "State is controlled by the hosting app",
                            checked = isSyncEnabled,
                            onCheckedChange = { isSyncEnabled = it },
                        )
                        SettingsDivider()
                        SettingsNavigationRow(
                            title = "Return to showcase",
                            onClick = { destination = null },
                        )
                    }
                }
            }
        }
    }
}

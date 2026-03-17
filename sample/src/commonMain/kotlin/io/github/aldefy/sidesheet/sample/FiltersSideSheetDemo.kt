package io.github.aldefy.sidesheet.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aldefy.sidesheet.ModalSideSheet
import io.github.aldefy.sidesheet.SideSheetDefaults
import io.github.aldefy.sidesheet.rememberSideSheetState
import kotlinx.coroutines.launch

private val filterLabels = listOf("Events", "Personal", "Projects", "Reminders", "Family")
private val formatOptions = listOf("All", "Read", "Unread", "Starred", "Attachments")

@Composable
fun FiltersSideSheetDemo(
    onDismiss: () -> Unit,
) {
    val sheetState = rememberSideSheetState()
    val scope = rememberCoroutineScope()

    // Capture theme values before Popup (Popup on desktop doesn't inherit MaterialTheme)
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Filter state — "Projects" and "Reminders" pre-selected to match screenshot
    val checkedLabels = remember {
        mutableStateMapOf(
            "Events" to false,
            "Personal" to false,
            "Projects" to true,
            "Reminders" to true,
            "Family" to false,
        )
    }
    val selectedFormat = remember { mutableStateOf("All") }

    ModalSideSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        colors = SideSheetDefaults.colors(
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface,
            scrimColor = SideSheetDefaults.ScrimColor,
        ),
    ) {
        // Re-provide MaterialTheme inside the Popup for text styling
        MaterialTheme(colorScheme = colorScheme, typography = typography) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp),
            ) {
                // Header: "Filters" title + close X
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Filters",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    IconButton(onClick = {
                        scope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }

                // Labels section header
                Text(
                    text = "Labels",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Label checkboxes
                filterLabels.forEach { label ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = checkedLabels[label] == true,
                            onCheckedChange = { checkedLabels[label] = it },
                        )
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 4.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))

                Spacer(modifier = Modifier.height(16.dp))

                // Format section header
                Text(
                    text = "Format",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 24.dp),
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Format filter chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    formatOptions.take(3).forEach { format ->
                        FilterChip(
                            selected = selectedFormat.value == format,
                            onClick = { selectedFormat.value = format },
                            label = { Text(format) },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    formatOptions.drop(3).forEach { format ->
                        FilterChip(
                            selected = selectedFormat.value == format,
                            onClick = { selectedFormat.value = format },
                            label = { Text(format) },
                        )
                    }
                }
            }
        }
    }
}

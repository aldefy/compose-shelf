package io.github.aldefy.sidesheet.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

data class EmailItem(
    val sender: String,
    val subject: String,
    val preview: String,
    val date: String,
    val isStarred: Boolean = false,
    val avatarColor: Color,
    val label: String? = null,
)

val sampleEmails = listOf(
    EmailItem(
        sender = "Ali Connors",
        subject = "Brunch this weekend?",
        preview = "I'll be in your neighborhood doing errands and was hoping to stop by for...",
        date = "2 hours ago",
        avatarColor = Color(0xFF6750A4),
        label = "Personal",
    ),
    EmailItem(
        sender = "Google Play",
        subject = "Your subscription renewed",
        preview = "Your YouTube Premium subscription has been renewed for another month...",
        date = "5 hours ago",
        avatarColor = Color(0xFF4285F4),
    ),
    EmailItem(
        sender = "Scott Hooper",
        subject = "Project Update — Q1 Review",
        preview = "Hey team, I wanted to share the latest updates on our quarterly review...",
        date = "Yesterday",
        isStarred = true,
        avatarColor = Color(0xFF34A853),
        label = "Projects",
    ),
    EmailItem(
        sender = "Maya Chen",
        subject = "Reminder: Team standup at 10am",
        preview = "Just a quick reminder that we have our weekly standup tomorrow at 10am...",
        date = "Yesterday",
        avatarColor = Color(0xFFEA4335),
        label = "Reminders",
    ),
    EmailItem(
        sender = "Travel Alerts",
        subject = "Flight confirmation",
        preview = "Your flight from BLR to DEL on March 22 has been confirmed. Booking...",
        date = "November 8",
        avatarColor = Color(0xFFFBBC05),
        label = "Events",
    ),
    EmailItem(
        sender = "Mom",
        subject = "Dinner on Sunday?",
        preview = "Hi dear, are you free this Sunday for dinner? Dad wants to try that new...",
        date = "November 5",
        avatarColor = Color(0xFFE8A0BF),
        label = "Family",
    ),
    EmailItem(
        sender = "Jira",
        subject = "TRAV-142 moved to Done",
        preview = "The issue TRAV-142: Fix side sheet animation has been moved to Done...",
        date = "September 26",
        avatarColor = Color(0xFF0052CC),
        label = "Projects",
    ),
    EmailItem(
        sender = "Calendar",
        subject = "Upcoming: Design review",
        preview = "You have a design review meeting tomorrow at 3pm with the UX team...",
        date = "September 20",
        avatarColor = Color(0xFF7B9EBF),
        label = "Reminders",
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleApp() {
    MaterialTheme {
        var showFilters by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Inbox") },
                    actions = {
                        IconButton(onClick = { showFilters = true }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filters",
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showFilters = true }) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Open filters",
                    )
                }
            },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                itemsIndexed(sampleEmails) { index, email ->
                    EmailRow(email = email)
                    if (index < sampleEmails.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 72.dp),
                            color = MaterialTheme.colorScheme.outlineVariant,
                        )
                    }
                }
            }
        }

        // Filters side sheet (M3 email example)
        if (showFilters) {
            FiltersSideSheetDemo(
                onDismiss = { showFilters = false },
            )
        }
    }
}

@Composable
private fun EmailRow(email: EmailItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        // Avatar circle
        Column(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(email.avatarColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = email.sender.first().toString(),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
            )
        }

        // Email content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = email.sender,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = email.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = email.subject,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = email.preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = if (email.isStarred) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = if (email.isStarred) "Starred" else "Not starred",
                    tint = if (email.isStarred) Color(0xFFFBBC05) else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

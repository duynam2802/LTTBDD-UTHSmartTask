package com.example.uthsmarttask.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.data.model.Subtask
import com.example.uthsmarttask.data.model.TaskItem
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.components.ScreenLevel
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    AppScaffold(
        navController = navController,
        title = "Home",
        screenLevel = ScreenLevel.MAIN,
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.tasks) { task ->
                        TaskCard(task = task, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: TaskItem,  navController: NavController) {
    val isTaskCompleted = task.subtasks.all { it.isCompleted }

    val backgroundColor = when (task.priority.lowercase()) {
        "high" -> Color(0xFFCE8B94)
        "medium" -> Color(0xFFDDE3B5)
        "low" -> Color(0xFFB9E9FF)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("taskDetail/${task.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    modifier = Modifier.clip(RoundedCornerShape(14.dp)),
                    checked = isTaskCompleted,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF080000),
                        uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = task.description,
                        maxLines = 2,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status: ${task.status}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = task.dueDate,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val sampleTasks = listOf(
        TaskItem(
            id = 1,
            title = "Complete Android Project",
            description = "Finish the UI, integrate API, and write documentation.",
            status = "In Progress",
            priority = "High",
            category = "Work",
            dueDate = "2024-03-26T09:00",
            createdAt = "2024-03-24T09:00:00Z",
            updatedAt = "2024-03-25T09:00:00Z",
            subtasks = listOf(
                Subtask(11, "Team Meeting", true),
                Subtask(12, "Prepare slides", false)
            ),
            attachments = listOf(),
            reminders = listOf()
        ),
        TaskItem(
            id = 2,
            title = "Doctor Appointment",
            description = "Check-up with Dr. Smith at 9 AM",
            status = "Pending",
            priority = "Medium",
            category = "Health",
            dueDate = "2024-03-26T09:00",
            createdAt = "2024-03-24T09:00:00Z",
            updatedAt = "2024-03-25T09:00:00Z",
            subtasks = listOf(
                Subtask(11, "Confirm appointment", true),
                Subtask(12, "Bring documents", true)
            ),
            attachments = listOf(),
            reminders = listOf()
        )
    )

    UTHSmartTaskTheme {
        AppScaffold(
            navController = rememberNavController(),
            title = "Home",
            screenLevel = ScreenLevel.MAIN,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sampleTasks) { task ->
                    TaskCard(task, navController = rememberNavController())
                }
            }
        }
    }
}

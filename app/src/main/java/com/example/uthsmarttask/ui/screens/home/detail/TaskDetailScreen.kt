package com.example.uthsmarttask.ui.screens.home.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Arrangementimport
import androidx.compose.foundation.layout.Box

//androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.data.model.Attachment
import com.example.uthsmarttask.data.model.Subtask
import com.example.uthsmarttask.data.model.TaskItem
import com.example.uthsmarttask.ui.components.AppScaffold
import com.example.uthsmarttask.ui.components.ScreenLevel
import com.example.uthsmarttask.ui.theme.PoppinsMedium
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme
import com.example.uthsmarttask.ui.screens.home.detail.*



@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: Int,
    viewModel: TaskDetailViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    // Gọi load dữ liệu khi taskId thay đổi
    LaunchedEffect(taskId) {
        viewModel.loadTaskById(taskId)
    }

    // Xử lý sau khi xóa task thành công
    LaunchedEffect(uiState.isTaskDeleted) {
        if (uiState.isTaskDeleted) {
            Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    // Xử lý khi có lỗi tải task ban đầu
    LaunchedEffect(uiState.error, uiState.task, uiState.isLoading) {
        // Nếu không loading, không có task, và có lỗi -> chuyển sang màn hình empty
        if (!uiState.isLoading && uiState.task == null && uiState.error != null) {
            navController.navigate("empty") {
                popUpTo("empty") { inclusive = true }
            }
        }
    }

    AppScaffold(
        navController = navController,
        title = "Task Detail",
        screenLevel = ScreenLevel.SUB,
        actions = {
            // Chỉ hiện nút xóa khi task đã được tải thành công
            if (uiState.task != null) {
                IconButton(onClick = { viewModel.deleteTask() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Task"
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                // 1. Trạng thái loading ban đầu
                uiState.isLoading && uiState.task == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // 2. Trạng thái tải task thành công
                uiState.task != null -> {
                    val task = uiState.task!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize() // Chiếm toàn bộ không gian
                            .padding(16.dp)
                    ) {
                        Text(
                            task.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            task.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 25.dp, horizontal = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .height(80.dp)
                                .background(Color(0xFFE3BAC0)),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InfoItem(
                                icon = Icons.Filled.Apps,
                                label = "Category",
                                value = task.category
                            )
                            InfoItem(
                                icon = Icons.Filled.NoteAlt,
                                label = "Status",
                                value = task.status
                            )
                            InfoItem(
                                icon = Icons.Filled.WorkspacePremium,
                                label = "Priority",
                                value = task.priority
                            )
                        }

                        SubtaskList(subtasks = task.subtasks)
                        Spacer(modifier = Modifier.height(16.dp))
                        AttachmentList(
                            attachments = task.attachments,
                            onAttachmentClick = { attachment ->
                                try {
                                    uriHandler.openUri(attachment.fileUrl)
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Could not open link: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                }

                // 3. (Optional) Xử lý các trường hợp lỗi khác nếu cần
                // Ví dụ, khi không loading và không có task (đã được xử lý bởi LaunchedEffect ở trên)
                else -> {
                    // Có thể hiển thị một thông báo trống ở đây nếu muốn
//                   Box(
//                       modifier = Modifier.fillMaxSize(),
//                       contentAlignment = Alignment.Center
//                   ) {
//                       Text("No data available or an error occurred.")
//                   }
                }
            }

            // Hiển thị loading indicator overlay khi đang thực hiện hành động (ví dụ: xóa)
            if (uiState.isLoading && uiState.task != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)), // Lớp nền mờ
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}


@Composable
private fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

    }
}

@Composable
fun SubtaskList(
    subtasks: List<Subtask>,
    modifier: Modifier = Modifier
) {
    // Kiểm tra nếu không có subtask thì không hiển thị gì cả
    if (subtasks.isEmpty()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách giữa các item
    ) {
        // Tiêu đề "Subtasks"
        Text(
            text = "Subtasks",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsMedium
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            subtasks.forEach { subtask ->
                SubtaskItem(subtask = subtask)
            }
        }
    }
}


@Composable
fun SubtaskItem(
    subtask: Subtask,
    modifier: Modifier = Modifier
) {

    var isChecked by remember(subtask.isCompleted) {
        mutableStateOf(subtask.isCompleted)
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE6E6E6)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF080000),
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(
                text = subtask.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun AttachmentList(
    attachments: List<Attachment>,
    modifier: Modifier = Modifier,
    onAttachmentClick: (Attachment) -> Unit
) {
    // Kiểm tra nếu không có attachment thì không hiển thị gì cả
    if (attachments.isEmpty()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách giữa các item
    ) {
        // Tiêu đề "Attachments"
        Text(
            text = "Attachments",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsMedium
        )

        // Dùng Column để lặp qua danh sách
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            attachments.forEach { attachment ->
                AttachmentItem(
                    attachment = attachment,
                    onClick = { onAttachmentClick(attachment) }
                )
            }
        }
    }
}


@Composable
fun AttachmentItem(
    attachment: Attachment,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE6E6E6)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Attachment,
                contentDescription = "Attachment icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = attachment.fileName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TaskDetailScreenPreview() {
    val navController = rememberNavController()

    // Task mẫu để xem preview
    val sampleTask = TaskItem(
        id = 1,
        title = "Doctor Appointment",
        description = "Check-up with Dr. Smith at 9 AM at the city clinic.",
        status = "Pending",
        priority = "High",
        category = "Health",
        dueDate = "2024-03-26T09:00",
        createdAt = "2024-03-24T09:00:00Z",
        updatedAt = "2024-03-25T09:00:00Z",
        subtasks = listOf(
            Subtask(1, "Confirm appointment", true),
            Subtask(2, "Bring medical records", false),
            Subtask(3, "Prepare questions for doctor", false)
        ),
        attachments = listOf(
            Attachment(id = 1, fileName = "medical_report.pdf", fileUrl = "https://example.com/report.pdf"),
            Attachment(id = 2, fileName = "insurance_card.jpg", fileUrl = "https://example.com/card.jpg")
        ),

        reminders = listOf()
    )

    UTHSmartTaskTheme {
        AppScaffold(
            navController = navController,
            title = "Task Detail",
            screenLevel = ScreenLevel.SUB,
            // Thêm action để preview khớp với thực tế
            actions = {
                IconButton(onClick = { /* Do nothing in preview */ }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Task"
                    )
                }
            }
        )  { padding ->
            // --- BẮT ĐẦU SAO CHÉP GIAO DIỆN TỪ `TaskDetailScreen` ---
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    sampleTask.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    sampleTask.description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .height(80.dp)
                        .background(Color(0xFFE3BAC0)),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InfoItem(
                        icon = Icons.Filled.Apps,
                        label = "Category",
                        value = sampleTask.category
                    )
                    InfoItem(
                        icon = Icons.Filled.NoteAlt,
                        label = "Status",
                        value = sampleTask.status
                    )
                    InfoItem(
                        icon = Icons.Filled.WorkspacePremium,
                        label = "Priority",
                        value = sampleTask.priority
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Gọi các composable con với dữ liệu mẫu
                SubtaskList(subtasks = sampleTask.subtasks)
                Spacer(modifier = Modifier.height(16.dp))
                AttachmentList(
                    attachments = sampleTask.attachments,
                    onAttachmentClick = {
                        // Không cần làm gì trong preview
                    }
                )
            }
            // --- KẾT THÚC SAO CHÉP GIAO DIỆN ---
        }
    }
}

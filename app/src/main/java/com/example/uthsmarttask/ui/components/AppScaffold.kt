package com.example.uthsmarttask.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttask.R
import com.example.uthsmarttask.ui.theme.UTHSmartTaskTheme

enum class ScreenLevel { MAIN, SUB }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavController,
    title: String,
    screenLevel: ScreenLevel = ScreenLevel.MAIN,
    onBackClicked: (() -> Unit)? = null,
    showSaveButton: Boolean = false,
    onSaveClicked: () -> Unit = {},
    showActionButton: Boolean = false,
    onActionClicked: () -> Unit = {},
    showMoreMenu: Boolean = false,
    moreMenuItems: @Composable ColumnScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            when (screenLevel) {
                ScreenLevel.MAIN -> MainTopBar(title = title)
                ScreenLevel.SUB -> SubTopBar(
                    title = title,
                    showSaveButton = showSaveButton,
                    onSaveClicked = onSaveClicked,
                    showActionButton = showActionButton,
                    onActionClicked = onActionClicked,
                    showMoreMenu = showMoreMenu,
                    moreMenuItems = moreMenuItems,
                    actions = actions,
                    onBackClicked = (onBackClicked ?: { navController.popBackStack() }) as () -> Unit
                )
            }
        },
        bottomBar = {
            if (screenLevel == ScreenLevel.MAIN) BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            content(PaddingValues())
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showActionButton: Boolean = false,
    onActionClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.uth_logo),
                    contentDescription = "Logo UTHSmartTask",
                    modifier = Modifier
                        .size(45.dp)
                        .background(Color(0xFFE9F7FA), RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Inside
                )

                // Title
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "SmartTasks",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 1.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "A simple and efficient to-do app",
                        fontSize = 10.sp,
                        lineHeight = 1.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Optional Action Button
                if (showActionButton) {
                    IconButton(onClick = onActionClicked) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Action"
                        )
                    }
                }

                // Menu
                IconButton(onClick = { /* menu */ }) {
                    Icon(
                        imageVector = Icons.Default.FormatListBulleted,
                        contentDescription = "Menu"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubTopBar(
    title: String,
    showSaveButton: Boolean,
    onSaveClicked: () -> Unit,
    showActionButton: Boolean,
    onActionClicked: () -> Unit,
    showMoreMenu: Boolean,
    moreMenuItems: @Composable ColumnScope.() -> Unit,
    onBackClicked: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {} // ✅ giữ nguyên
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.padding(start = 12.dp, end = 24.dp),
        navigationIcon = {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF03B4FA))
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            if (showSaveButton) {
                TextButton(onClick = onSaveClicked) {
                    Text("Lưu")
                }
            }

            if (showActionButton) {
                IconButton(onClick = onActionClicked) {
                    Icon(Icons.Default.Add, contentDescription = "Action")
                }
            }

            if (showMoreMenu) {
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        moreMenuItems()
                    }
                }
            }

            actions()
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Nhật ký") },
            selected = selectedIndex == 0,
            onClick = { selectedIndex = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Sức khỏe") },
            label = { Text("Dữ liệu") },
            selected = selectedIndex == 1,
            onClick = { selectedIndex = 1 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Hồ sơ") },
            label = { Text("Hồ sơ") },
            selected = selectedIndex == 2,
            onClick = { selectedIndex = 2 }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    UTHSmartTaskTheme {
        val navController = rememberNavController()
        AppScaffold(
            navController = navController,
            title = "Nhật ký",
            screenLevel = ScreenLevel.SUB,
            showSaveButton = true,
            showActionButton = true,
            onActionClicked = { /* hành động khi nhấn nút */ }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Nội dung chính màn hình")
            }
        }
    }
}

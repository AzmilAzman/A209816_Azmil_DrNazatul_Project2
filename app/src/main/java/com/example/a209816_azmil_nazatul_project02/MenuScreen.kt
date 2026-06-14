package com.example.a209816_azmil_nazatul_project02

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController, viewModel: FeedbackViewModel) {
    var expanded by remember { mutableStateOf(false) }

    val history by viewModel.historyList.collectAsState()

    val countAduan       = history.count { it.category == "Aduan" }
    val countPertanyaan  = history.count { it.category == "Pertanyaan" }
    val countCadangan    = history.count { it.category == "Cadangan" }
    val countPenghargaan = history.count { it.category == "Penghargaan" }
    val countTotal       = history.size

    Scaffold(
        topBar = { PeoplesVoiceTopBar(title = "People's Voice", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.None
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Aduan", Icons.Default.Warning, Modifier.weight(1f)) {
                    viewModel.setCategory("Aduan")
                    navController.navigate("form")
                }
                MenuButton("Pertanyaan", Icons.Default.Info, Modifier.weight(1f)) {
                    viewModel.setCategory("Pertanyaan")
                    navController.navigate("form")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Cadangan", Icons.Default.ThumbUp, Modifier.weight(1f)) {
                    viewModel.setCategory("Cadangan")
                    navController.navigate("form")
                }
                MenuButton("Penghargaan", Icons.Default.CheckCircle, Modifier.weight(1f)) {
                    viewModel.setCategory("Penghargaan")
                    navController.navigate("form")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Sejarah", Icons.Default.List, Modifier.weight(1f)) {
                    navController.navigate("history")
                }
                MenuButton("Soalan Lazim", Icons.Default.Star, Modifier.weight(1f)) {
                    navController.navigate("faq")
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MenuButton("Berita", Icons.Default.Notifications, Modifier.weight(1f)) {
                    navController.navigate("news")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Status aduan/ maklum balas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatusItem("Aduan",       countAduan)
                    DividerLine()
                    StatusItem("Pertanyaan",  countPertanyaan)
                    DividerLine()
                    StatusItem("Cadangan",    countCadangan)
                    DividerLine()
                    StatusItem("Penghargaan", countPenghargaan)
                    DividerLine()
                    StatusItem("Jumlah",      countTotal)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .clickable { expanded = !expanded }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Perlukan Bantuan?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Hubungi 03-6767 4918", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatusItem(label: String, count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = count.toString(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DividerLine() {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 0.5.dp
    )
}
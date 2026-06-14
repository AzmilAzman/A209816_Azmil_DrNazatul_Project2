package com.example.a209816_azmil_nazatul_project02

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FAQScreen(navController: NavController) {
    val faqs = listOf(
        _root_ide_package_.com.example.a209816_azmil_nazatul_project02.FAQItem(
            "Bagaimana membuat aduan?",
            "Pilih 'Aduan' di menu utama."
        ),
        _root_ide_package_.com.example.a209816_azmil_nazatul_project02.FAQItem(
            "Adakah identiti saya dirahsiakan?",
            "Ya, maklumat anda adalah sulit."
        ),
        _root_ide_package_.com.example.a209816_azmil_nazatul_project02.FAQItem(
            "Berapa lama maklum balas?",
            "Dalam tempoh 3 hari bekerja."
        )
    )

    Scaffold(
        topBar = {
            _root_ide_package_.com.example.a209816_azmil_nazatul_project02.PeoplesVoiceTopBar(
                "Soalan Lazim",
                navController,
                showBack = true
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            items(faqs) { faq ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(faq.question, fontWeight = FontWeight.Bold)
                    Text(faq.answer, style = MaterialTheme.typography.bodyMedium)
                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}
package com.example.a209816_azmil_nazatul_project02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.a209816_azmil_nazatul_project02.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val viewModel: FeedbackViewModel = viewModel()
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login")   { LoginScreen(navController) }
                        composable("menu")    { MenuScreen(navController, viewModel) }
                        composable("form")    { FormScreen(navController, viewModel) }
                        composable("result")  { ResultScreen(navController, viewModel) }
                        composable("profile") { ProfileScreen(navController, viewModel) }
                        composable("history") { HistoryScreen(navController, viewModel) }
                        composable("faq")     { FAQScreen(navController) }
                        composable("news")    { NewsScreen(navController) }
                        composable("historyDetail/{itemId}") { backStackEntry ->
                            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                            HistoryDetailScreen(navController, viewModel, itemId)
                        }
                    }
                }
            }
        }
    }
}
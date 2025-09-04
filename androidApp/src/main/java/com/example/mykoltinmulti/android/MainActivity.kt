package com.example.mykoltinmulti.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.mykoltinmulti.android.screens.home.HomeScreen
import com.example.mykoltinmulti.android.screens.login.LoginScreen
import com.example.mykoltinmulti.android.ui.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    var isLoggedIn by remember { mutableStateOf(false) }
    if (isLoggedIn) {
        HomeScreen()
    } else {
        LoginScreen(onLoginSuccess = { isLoggedIn = true })
    }
}

@Preview
@Composable
fun LoginPreview() {
    Theme {
        LoginScreen(onLoginSuccess = {})
    }
}
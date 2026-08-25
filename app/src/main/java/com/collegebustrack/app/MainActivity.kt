package com.collegebustrack.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.collegebustrack.app.navigation.CollegeBusApp
import com.collegebustrack.app.ui.theme.CollegeBusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CollegeBusTheme { CollegeBusApp() } }
    }
}

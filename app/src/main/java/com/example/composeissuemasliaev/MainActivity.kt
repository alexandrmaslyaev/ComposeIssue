package com.example.composeissuemasliaev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composeissuemasliaev.container.Container
import com.example.composeissuemasliaev.container.State
import com.example.composeissuemasliaev.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Container(
                    cardState = State(
                        frontState = State.FrontState(
                            "0000",
                            type = State.Type.Type1,
                        ),
                        backState = null,
                        flagOne = false,
                        flagTwo = false,
                    ),
                )
            }
        }
    }
}

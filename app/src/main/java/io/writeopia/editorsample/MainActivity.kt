package io.writeopia.editorsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.writeopia.editorsample.ui.theme.EditorSampleTheme
import io.writeopia.sdk.manager.WriteopiaManager
import io.writeopia.sdk.models.span.Span
import io.writeopia.ui.WriteopiaEditor
import io.writeopia.ui.drawer.factory.DefaultDrawersAndroid
import io.writeopia.ui.manager.WriteopiaStateManager
import io.writeopia.ui.model.DrawState
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EditorSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Editor(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Editor(modifier: Modifier = Modifier) {

    val stateManager = remember {
        WriteopiaStateManager.create(
            WriteopiaManager(),
            dispatcher = Dispatchers.IO,
        ).also {
            it.newDocument("documentId")
        }
    }

    val drawState by stateManager.toDraw.collectAsState(DrawState())

    Box {
        WriteopiaEditor(
            modifier = modifier.fillMaxSize(),
            storyState = drawState,
            drawers = DefaultDrawersAndroid.create(manager = stateManager)
        )

        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 50.dp)) {
            Text(
                "B",
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    stateManager.toggleSpan(Span.BOLD)
                }.padding(10.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "I",
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    stateManager.toggleSpan(Span.ITALIC)
                }.padding(10.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "U",
                fontSize = 20.sp,
                modifier = Modifier.clickable {
                    stateManager.toggleSpan(Span.UNDERLINE)
                }.padding(10.dp)
            )
        }
    }
}

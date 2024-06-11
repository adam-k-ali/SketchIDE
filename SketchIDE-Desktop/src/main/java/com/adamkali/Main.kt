package com.adamkali

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.adamkali.browser.projectBrowser
import com.adamkali.editor.StyleAdapter
import com.adamkali.lang.Lexer
import com.adamkali.preferences.EditorStyles
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun codeEditor() {
    val linesTextScroll = rememberScrollState()
    val scriptTextScroll = rememberScrollState()
    LaunchedEffect(linesTextScroll.value) {
        scriptTextScroll.scrollTo(linesTextScroll.value)
    }
    LaunchedEffect(scriptTextScroll.value) {
        linesTextScroll.scrollTo(scriptTextScroll.value)
    }

    Row {
        TextField(
            modifier = Modifier.fillMaxHeight().width(80.dp).verticalScroll(linesTextScroll).padding(0.dp),
            value = IntRange(1, 10).joinToString("\n"),
            readOnly = true,
            onValueChange = {}
        )

        Divider(color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.fillMaxHeight().padding(horizontal = 8.dp).width(1.dp))

        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(scriptTextScroll)
                .onKeyEvent { it.type == KeyEventType.KeyUp && it.key == Key.Enter },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = StyleAdapter.colorAdapter(EditorStyles.PLAINTEXT_BG_COLOR)
            ),
            value = textState.value,
            onValueChange = {
                val tokens = Lexer.lex(it.text)
                val annotatedString = StyleAdapter.convertToAnnotatedString(tokens)
                textState.value = it.copy(annotatedString, it.selection, it.composition)
            }
        )
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun workspace() {
    val splitterState = rememberSplitPaneState()
    HorizontalSplitPane(
        splitPaneState = splitterState
    ) {
        first(minSize = 200.dp) {
            projectBrowser()
        }
        second(minSize = 50.dp) {
            codeEditor()
        }
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun debugAndStates() {
    val splitterState = rememberSplitPaneState()
    HorizontalSplitPane(
        splitPaneState = splitterState
    ) {
        first(minSize = 20.dp) {
            Box(Modifier.background(Color.Green).fillMaxSize())
        }
        second(minSize = 50.dp) {
            Box(Modifier.background(Color.Magenta).fillMaxSize())
        }
    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
@Preview
fun app() {
    MaterialTheme {
        val splitterState = rememberSplitPaneState()
        VerticalSplitPane (
            splitPaneState = splitterState
        ) {
            first(minSize = 500.dp) {
                workspace()
            }
            second(minSize = 50.dp) {
                debugAndStates()
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        ProjectManager.projectData.refresh()
        app()
    }
}

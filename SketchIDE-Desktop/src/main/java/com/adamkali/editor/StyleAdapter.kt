package com.adamkali.editor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.adamkali.lang.tokens.TabToken
import com.adamkali.lang.tokens.Token
import com.adamkali.preferences.EditorStyles

/**
 * Converts style information between different formats
 */
object StyleAdapter {
    /**
     * Converts java.awt.Color to androidx.compose.ui.graphics.Color
     */
    fun colorAdapter (color: java.awt.Color) : Color {
        return Color(color.red, color.green, color.blue)
    }

    private fun getTokenStyle(token: Token): SpanStyle {
        // Convert java.awt.Color to androidx.compose.ui.graphics.Color
        val fgColor = colorAdapter(token.getForegroundColor())
        val bgColor = colorAdapter(token.getBackgroundColor())

        return SpanStyle(
            color = fgColor,
            background = bgColor,
            fontSize = EditorStyles.FONT_SIZE.sp
        )
    }

    fun convertToAnnotatedString(tokens: List<Token>): AnnotatedString {
        return buildAnnotatedString {
            for (token in tokens) {
                withStyle(style = getTokenStyle(token)) {
                    append(token.text)
                }
            }
        }
    }
}
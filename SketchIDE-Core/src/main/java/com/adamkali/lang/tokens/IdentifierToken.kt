package com.adamkali.lang.tokens

import com.adamkali.preferences.EditorStyles

class IdentifierToken(text: String) : Token(text, EditorStyles.PLAINTEXT_BG_COLOR, EditorStyles.PLAINTEXT_FG_COLOR) {
}
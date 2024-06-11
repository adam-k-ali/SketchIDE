package com.adamkali.lang.tokens.keyword

import com.adamkali.lang.tokens.Token
import com.adamkali.preferences.EditorStyles

abstract class KeywordToken(text: String) : Token(text, EditorStyles.KEYWORD_BG_COLOR, EditorStyles.KEYWORD_FG_COLOR) {
}
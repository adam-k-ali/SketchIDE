package com.adamkali.lang.tokens.operator

import com.adamkali.lang.tokens.Token
import com.adamkali.preferences.EditorStyles

abstract class OperatorToken(text: String) :
    Token(text, EditorStyles.PLAINTEXT_BG_COLOR, EditorStyles.PLAINTEXT_FG_COLOR)
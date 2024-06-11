package com.adamkali.lang.tokens.literal

import com.adamkali.preferences.EditorStyles

class StringToken(text: String) : LiteralToken(text, EditorStyles.STRING_BG_COLOR, EditorStyles.STRING_FG_COLOR)
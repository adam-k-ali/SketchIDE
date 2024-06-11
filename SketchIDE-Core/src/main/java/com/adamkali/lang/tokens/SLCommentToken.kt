package com.adamkali.lang.tokens

import com.adamkali.preferences.EditorStyles

class SLCommentToken(text: String) : Token(text, EditorStyles.COMMENT_BG_COLOR, EditorStyles.COMMENT_FG_COLOR) {
}
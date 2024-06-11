package com.adamkali.lang.tokens.literal

import com.adamkali.lang.tokens.Token
import java.awt.Color

open class LiteralToken(text: String, bgColor: Color, fgColor: Color) :
    Token(text, bgColor, fgColor)
package com.adamkali.lang

import com.adamkali.lang.tokens.*
import com.adamkali.lang.tokens.keyword.context.ImportKeywordToken
import com.adamkali.lang.tokens.keyword.context.PackageKeywordToken
import com.adamkali.lang.tokens.keyword.exception.*
import com.adamkali.lang.tokens.keyword.flow.*
import com.adamkali.lang.tokens.keyword.literal.FalseKeywordToken
import com.adamkali.lang.tokens.keyword.literal.NullKeywordToken
import com.adamkali.lang.tokens.keyword.literal.TrueKeywordToken
import com.adamkali.lang.tokens.keyword.modifier.*
import com.adamkali.lang.tokens.keyword.reference.SuperKeywordToken
import com.adamkali.lang.tokens.keyword.reference.ThisKeywordToken
import com.adamkali.lang.tokens.keyword.scope.PrivateKeywordToken
import com.adamkali.lang.tokens.keyword.scope.ProtectedKeywordToken
import com.adamkali.lang.tokens.keyword.type.*
import com.adamkali.lang.tokens.literal.DoubleToken
import com.adamkali.lang.tokens.literal.FloatToken
import com.adamkali.lang.tokens.literal.StringToken
import com.adamkali.lang.tokens.operator.AssignmentToken
import com.adamkali.lang.tokens.operator.DotToken
import com.adamkali.lang.tokens.operator.SemicolonToken
import com.adamkali.lang.tokens.operator.character.*
import com.adamkali.lang.tokens.operator.logic.bit.*
import com.adamkali.lang.tokens.operator.logic.bit.assignment.AndEqualsToken
import com.adamkali.lang.tokens.operator.logic.bit.assignment.OrEqualsToken
import com.adamkali.lang.tokens.operator.logic.bit.assignment.XorEqualsToken
import com.adamkali.lang.tokens.operator.math.*
import com.adamkali.lang.tokens.operator.math.assignment.DivideEqualsToken
import com.adamkali.lang.tokens.operator.math.assignment.ModEqualsToken
import com.adamkali.lang.tokens.operator.math.assignment.PlusEqualsToken
import com.adamkali.lang.tokens.operator.logic.comparison.*
import com.adamkali.lang.tokens.operator.math.assignment.MinusEqualsToken
import com.adamkali.lang.tokens.operator.math.assignment.StarEqualsToken
import com.adamkaliproject.lang.tokens.keyword.scope.PublicKeywordToken

object Lexer {
    val keywordTokens = hashMapOf(
        "boolean" to BooleanKeywordToken::class,
        "byte" to ByteKeywordToken::class,
        "char" to CharKeywordToken::class,
        "double" to DoubleKeywordToken::class,
        "float" to FloatKeywordToken::class,
        "int" to IntKeywordToken::class,
        "long" to LongKeywordToken::class,
        "short" to ShortKeywordToken::class,
        "void" to VoidKeywordToken::class,

        "public" to PublicKeywordToken::class,
        "private" to PrivateKeywordToken::class,
        "protected" to ProtectedKeywordToken::class,

        "const" to ConstKeywordToken::class,
        "static" to StaticKeywordToken::class,
        "final" to FinalKeywordToken::class,
        "abstract" to AbstractKeywordToken::class,
        "volatile" to VolatileKeywordToken::class,
        "synchronized" to SynchronizedKeywordToken::class,
        "transient" to TransientKeywordToken::class,
        "native" to NativeKeywordToken::class,
        "strictfp" to StrictFPKeywordToken::class,
        "class" to ClassKeywordToken::class,
        "interface" to InterfaceKeywordToken::class,
        "enum" to EnumKeywordToken::class,
        "extends" to ExtendsKeywordToken::class,
        "implements" to ImplementsKeywordToken::class,
        "new" to NewKeywordToken::class,

        "package" to PackageKeywordToken::class,
        "import" to ImportKeywordToken::class,

        "true" to TrueKeywordToken::class,
        "false" to FalseKeywordToken::class,
        "null" to NullKeywordToken::class,

        "assert" to AssertKeywordToken::class,
        "catch" to CatchKeywordToken::class,
        "finally" to FinallyKeywordToken::class,
        "throw" to ThrowKeywordToken::class,
        "throws" to ThrowsKeywordToken::class,
        "try" to TryKeywordToken::class,

        "break" to BreakKeywordToken::class,
        "case" to CaseKeywordToken::class,
        "continue" to ContinueKeywordToken::class,
        "default" to DefaultKeywordToken::class,
        "do" to DoKeywordToken::class,
        "else" to ElseKeywordToken::class,
        "for" to ForKeywordToken::class,
        "goto" to GoToKeywordToken::class,
        "if" to IfKeywordToken::class,
        "instanceof" to InstanceOfKeywordToken::class,
        "return" to ReturnKeywordToken::class,
        "switch" to SwitchKeywordToken::class,
        "while" to WhileKeywordToken::class,

        "super" to SuperKeywordToken::class,
        "this" to ThisKeywordToken::class,
    )

    val operatorTokens = hashMapOf(
        "(" to LParenToken::class,
        ")" to RParenToken::class,
        "{" to LBraceToken::class,
        "}" to RBraceToken::class,
        "[" to LBracketToken::class,
        "]" to RBracketToken::class,
        "," to SemicolonToken::class,
        "." to DotToken::class,
        ";" to SemicolonToken::class,
        "=" to AssignmentToken::class,
        "+" to AdditionToken::class,
        "-" to SubtractionToken::class,
        "*" to MultiplicationToken::class,
        "/" to DivisionToken::class,
        "%" to ModuloToken::class,
        "++" to IncrementToken::class,
        "--" to DecrementToken::class,
        "==" to EqualityToken::class,
        "!=" to NEqualityToken::class,
        ">" to GTToken::class,
        "<" to LTToken::class,
        ">=" to GTEqToken::class,
        "<=" to LTEqToken::class,
        "&&" to AndToken::class,
        "||" to OrToken::class,
        "!" to NotToken::class,
        "&" to BitAndToken::class,
        "|" to BitOrToken::class,
        "^" to BitXorToken::class,
        "~" to BitNotToken::class,
        "<<" to BitShiftLeftToken::class,
        ">>" to BitShiftRightToken::class,
        ">>>" to BitURightShiftToken::class,
        "+=" to PlusEqualsToken::class,
        "-=" to MinusEqualsToken::class,
        "*=" to StarEqualsToken::class,
        "/=" to DivideEqualsToken::class,
        "%=" to ModEqualsToken::class,
        "&=" to AndEqualsToken::class,
        "|=" to OrEqualsToken::class,
        "^=" to XorEqualsToken::class,
    )

    private fun isInteger(input: String): Boolean {
        return input.toIntOrNull() != null
    }

    private fun getAtomicWord(input: String, startIndex: Int): String {
        var endIndex = startIndex
        while (endIndex < input.length && input[endIndex].isLetterOrDigit()) {
            endIndex++
        }
        if (endIndex == startIndex) {
            return input[startIndex].toString()
        }
        return input.substring(startIndex, endIndex)

    }

    fun lex(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var atom: String
        var index = 0
        while (index < input.length) {
            val c = input[index]
            atom = c.toString()
            when (c) {
                '\n' -> {
                    tokens.add(NewLineToken())
                }

                '\t' -> {
                    tokens.add(TabToken())
                }

                ' ' -> {
                    tokens.add(WhitespaceToken())
                }

                '/' -> {
                    if (index + 1 < input.length && input[index + 1] == '/') {
                        index++
                        atom += input[index]

                        // Single line comment
                        while (index + 1 < input.length && input[index] != '\n') {
                            index++
                            atom += input[index]
                        }
                        tokens.add(SLCommentToken(atom))
                    } else {
                        tokens.add(DivisionToken())
                    }
                }

                '"' -> {
                    var validString = true
                    while (index + 1 < input.length && input[index + 1] != '"') {
                        index++
                        atom += input[index]
                    }
                    if (index + 1 < input.length) {
                        index++
                        atom += input[index]
                    } else {
                        validString = false
                    }
                    var stringToken : StringToken = StringToken(atom)
                    stringToken.valid = validString
                    tokens.add(stringToken)
                }

                else -> {
                    if (operatorTokens.containsKey(atom)) {
                        // Operators can be up to 3 characters long
                        if (index + 1 < input.length && operatorTokens.containsKey(atom + input[index + 1])) {
                            atom += input[index + 1]
                            index++
                        }
                        if (index + 1 < input.length && operatorTokens.containsKey(atom + input[index + 1])) {
                            atom += input[index + 1]
                            index++
                        }
                        tokens.add(operatorTokens[atom]!!.constructors.first().call() as Token)
                    } else {
                        atom = getAtomicWord(input, index)
                        index += atom.length - 1
                        if (keywordTokens.containsKey(atom)) {
                            tokens.add(keywordTokens[atom]!!.constructors.first().call() as Token)
                        } else {
                            // If the word is followed by a ., it may be a decimal number
                            if (index + 1 < input.length && input[index + 1] == '.' && atom[0].isDigit()) {
                                index++;
                                atom += "."
                                while (index + 1 < input.length && input[index + 1].isDigit()) {
                                    index++
                                    atom += input[index]
                                }

                                if (index + 1 < input.length && input[index + 1].lowercaseChar() == 'f') {
                                    index++
                                    atom += "F"
                                    tokens.add(FloatToken(atom))
                                } else {
                                    tokens.add(DoubleToken(atom))
                                }
                            } else {
                                tokens.add(IdentifierToken(atom))
                            }
                        }
                    }
                }
            }
            index++
        }
        return tokens
    }
}
package tabaquithemasteroftime.softwarerenderer.awtutils.internal

import tabaquithemasteroftime.softwarerenderer.awtutils.Key
import java.awt.event.KeyEvent

/**
 * Helper for implementing listeners.
 */
internal interface ListenersHelper {

    /**
     * Maps keyboard key code [key]
     * into the corresponding [Key].
     */
    fun mapKey(key: Int): Key

    companion object {

        /**
         * Create a [ListenersHelper].
         */
        fun of(): ListenersHelper {
            return ListenersHelperImpl.of()
        }
    }
}

private class ListenersHelperImpl(
    private val keyboardMapping: Map<Int, Key>
) : ListenersHelper {

    override fun mapKey(key: Int): Key {
        return keyboardMapping.getOrDefault(
            key = key,
            defaultValue = Key.UNKNOWN
        )
    }

    companion object {

        fun of(): ListenersHelperImpl {
            val keyboardMapping = mapOf(
                KeyEvent.VK_ENTER to Key.VK_ENTER,
                KeyEvent.VK_BACK_SPACE to Key.VK_BACK_SPACE,
                KeyEvent.VK_TAB to Key.VK_TAB,
                KeyEvent.VK_CANCEL to Key.VK_CANCEL,
                KeyEvent.VK_CLEAR to Key.VK_CLEAR,
                KeyEvent.VK_SHIFT to Key.VK_SHIFT,
                KeyEvent.VK_CONTROL to Key.VK_CONTROL,
                KeyEvent.VK_ALT to Key.VK_ALT,
                KeyEvent.VK_PAUSE to Key.VK_PAUSE,
                KeyEvent.VK_CAPS_LOCK to Key.VK_CAPS_LOCK,
                KeyEvent.VK_ESCAPE to Key.VK_ESCAPE,
                KeyEvent.VK_SPACE to Key.VK_SPACE,
                KeyEvent.VK_PAGE_UP to Key.VK_PAGE_UP,
                KeyEvent.VK_PAGE_DOWN to Key.VK_PAGE_DOWN,
                KeyEvent.VK_END to Key.VK_END,
                KeyEvent.VK_HOME to Key.VK_HOME,
                KeyEvent.VK_LEFT to Key.VK_LEFT,
                KeyEvent.VK_UP to Key.VK_UP,
                KeyEvent.VK_RIGHT to Key.VK_RIGHT,
                KeyEvent.VK_DOWN to Key.VK_DOWN,
                KeyEvent.VK_COMMA to Key.VK_COMMA,
                KeyEvent.VK_MINUS to Key.VK_MINUS,
                KeyEvent.VK_PERIOD to Key.VK_PERIOD,
                KeyEvent.VK_SLASH to Key.VK_SLASH,
                KeyEvent.VK_0 to Key.VK_0,
                KeyEvent.VK_1 to Key.VK_1,
                KeyEvent.VK_2 to Key.VK_2,
                KeyEvent.VK_3 to Key.VK_3,
                KeyEvent.VK_4 to Key.VK_4,
                KeyEvent.VK_5 to Key.VK_5,
                KeyEvent.VK_6 to Key.VK_6,
                KeyEvent.VK_7 to Key.VK_7,
                KeyEvent.VK_8 to Key.VK_8,
                KeyEvent.VK_9 to Key.VK_9,
                KeyEvent.VK_SEMICOLON to Key.VK_SEMICOLON,
                KeyEvent.VK_EQUALS to Key.VK_EQUALS,
                KeyEvent.VK_A to Key.VK_A,
                KeyEvent.VK_B to Key.VK_B,
                KeyEvent.VK_C to Key.VK_C,
                KeyEvent.VK_D to Key.VK_D,
                KeyEvent.VK_E to Key.VK_E,
                KeyEvent.VK_F to Key.VK_F,
                KeyEvent.VK_G to Key.VK_G,
                KeyEvent.VK_H to Key.VK_H,
                KeyEvent.VK_I to Key.VK_I,
                KeyEvent.VK_J to Key.VK_J,
                KeyEvent.VK_K to Key.VK_K,
                KeyEvent.VK_L to Key.VK_L,
                KeyEvent.VK_M to Key.VK_M,
                KeyEvent.VK_N to Key.VK_N,
                KeyEvent.VK_O to Key.VK_O,
                KeyEvent.VK_P to Key.VK_P,
                KeyEvent.VK_Q to Key.VK_Q,
                KeyEvent.VK_R to Key.VK_R,
                KeyEvent.VK_S to Key.VK_S,
                KeyEvent.VK_T to Key.VK_T,
                KeyEvent.VK_U to Key.VK_U,
                KeyEvent.VK_V to Key.VK_V,
                KeyEvent.VK_W to Key.VK_W,
                KeyEvent.VK_X to Key.VK_X,
                KeyEvent.VK_Y to Key.VK_Y,
                KeyEvent.VK_Z to Key.VK_Z,
                KeyEvent.VK_OPEN_BRACKET to Key.VK_OPEN_BRACKET,
                KeyEvent.VK_BACK_SLASH to Key.VK_BACK_SLASH,
                KeyEvent.VK_CLOSE_BRACKET to Key.VK_CLOSE_BRACKET,
                KeyEvent.VK_NUMPAD0 to Key.VK_NUMPAD0,
                KeyEvent.VK_NUMPAD1 to Key.VK_NUMPAD1,
                KeyEvent.VK_NUMPAD2 to Key.VK_NUMPAD2,
                KeyEvent.VK_NUMPAD3 to Key.VK_NUMPAD3,
                KeyEvent.VK_NUMPAD4 to Key.VK_NUMPAD4,
                KeyEvent.VK_NUMPAD5 to Key.VK_NUMPAD5,
                KeyEvent.VK_NUMPAD6 to Key.VK_NUMPAD6,
                KeyEvent.VK_NUMPAD7 to Key.VK_NUMPAD7,
                KeyEvent.VK_NUMPAD8 to Key.VK_NUMPAD8,
                KeyEvent.VK_NUMPAD9 to Key.VK_NUMPAD9,
                KeyEvent.VK_MULTIPLY to Key.VK_MULTIPLY,
                KeyEvent.VK_ADD to Key.VK_ADD,
                KeyEvent.VK_SUBTRACT to Key.VK_SUBTRACT,
                KeyEvent.VK_DECIMAL to Key.VK_DECIMAL,
                KeyEvent.VK_DIVIDE to Key.VK_DIVIDE,
                KeyEvent.VK_DELETE to Key.VK_DELETE,
                KeyEvent.VK_NUM_LOCK to Key.VK_NUM_LOCK,
                KeyEvent.VK_SCROLL_LOCK to Key.VK_SCROLL_LOCK,
                KeyEvent.VK_F1 to Key.VK_F1,
                KeyEvent.VK_F2 to Key.VK_F2,
                KeyEvent.VK_F3 to Key.VK_F3,
                KeyEvent.VK_F4 to Key.VK_F4,
                KeyEvent.VK_F5 to Key.VK_F5,
                KeyEvent.VK_F6 to Key.VK_F6,
                KeyEvent.VK_F7 to Key.VK_F7,
                KeyEvent.VK_F8 to Key.VK_F8,
                KeyEvent.VK_F9 to Key.VK_F9,
                KeyEvent.VK_F10 to Key.VK_F10,
                KeyEvent.VK_F11 to Key.VK_F11,
                KeyEvent.VK_F12 to Key.VK_F12,
                KeyEvent.VK_F13 to Key.VK_F13,
                KeyEvent.VK_F14 to Key.VK_F14,
                KeyEvent.VK_F15 to Key.VK_F15,
                KeyEvent.VK_F16 to Key.VK_F16,
                KeyEvent.VK_F17 to Key.VK_F17,
                KeyEvent.VK_F18 to Key.VK_F18,
                KeyEvent.VK_F19 to Key.VK_F19,
                KeyEvent.VK_F20 to Key.VK_F20,
                KeyEvent.VK_F21 to Key.VK_F21,
                KeyEvent.VK_F22 to Key.VK_F22,
                KeyEvent.VK_F23 to Key.VK_F23,
                KeyEvent.VK_F24 to Key.VK_F24,
                KeyEvent.VK_PRINTSCREEN to Key.VK_PRINTSCREEN,
                KeyEvent.VK_INSERT to Key.VK_INSERT,
                KeyEvent.VK_HELP to Key.VK_HELP,
                KeyEvent.VK_META to Key.VK_META,
                KeyEvent.VK_BACK_QUOTE to Key.VK_BACK_QUOTE,
                KeyEvent.VK_QUOTE to Key.VK_QUOTE,
                KeyEvent.VK_KP_UP to Key.VK_KP_UP,
                KeyEvent.VK_KP_DOWN to Key.VK_KP_DOWN,
                KeyEvent.VK_KP_LEFT to Key.VK_KP_LEFT,
                KeyEvent.VK_KP_RIGHT to Key.VK_KP_RIGHT,
                KeyEvent.VK_DEAD_GRAVE to Key.VK_DEAD_GRAVE,
                KeyEvent.VK_DEAD_ACUTE to Key.VK_DEAD_ACUTE,
                KeyEvent.VK_DEAD_CIRCUMFLEX to Key.VK_DEAD_CIRCUMFLEX,
                KeyEvent.VK_DEAD_TILDE to Key.VK_DEAD_TILDE,
                KeyEvent.VK_DEAD_MACRON to Key.VK_DEAD_MACRON,
                KeyEvent.VK_DEAD_BREVE to Key.VK_DEAD_BREVE,
                KeyEvent.VK_DEAD_ABOVEDOT to Key.VK_DEAD_ABOVEDOT,
                KeyEvent.VK_DEAD_DIAERESIS to Key.VK_DEAD_DIAERESIS,
                KeyEvent.VK_DEAD_ABOVERING to Key.VK_DEAD_ABOVERING,
                KeyEvent.VK_DEAD_DOUBLEACUTE to Key.VK_DEAD_DOUBLEACUTE,
                KeyEvent.VK_DEAD_CARON to Key.VK_DEAD_CARON,
                KeyEvent.VK_DEAD_CEDILLA to Key.VK_DEAD_CEDILLA,
                KeyEvent.VK_DEAD_OGONEK to Key.VK_DEAD_OGONEK,
                KeyEvent.VK_DEAD_IOTA to Key.VK_DEAD_IOTA,
                KeyEvent.VK_DEAD_VOICED_SOUND to Key.VK_DEAD_VOICED_SOUND,
                KeyEvent.VK_DEAD_SEMIVOICED_SOUND to Key.VK_DEAD_SEMIVOICED_SOUND,
                KeyEvent.VK_AMPERSAND to Key.VK_AMPERSAND,
                KeyEvent.VK_ASTERISK to Key.VK_ASTERISK,
                KeyEvent.VK_QUOTEDBL to Key.VK_QUOTEDBL,
                KeyEvent.VK_LESS to Key.VK_LESS,
                KeyEvent.VK_GREATER to Key.VK_GREATER,
                KeyEvent.VK_BRACELEFT to Key.VK_BRACELEFT,
                KeyEvent.VK_BRACERIGHT to Key.VK_BRACERIGHT,
                KeyEvent.VK_AT to Key.VK_AT,
                KeyEvent.VK_COLON to Key.VK_COLON,
                KeyEvent.VK_CIRCUMFLEX to Key.VK_CIRCUMFLEX,
                KeyEvent.VK_DOLLAR to Key.VK_DOLLAR,
                KeyEvent.VK_EURO_SIGN to Key.VK_EURO_SIGN,
                KeyEvent.VK_EXCLAMATION_MARK to Key.VK_EXCLAMATION_MARK,
                KeyEvent.VK_INVERTED_EXCLAMATION_MARK to Key.VK_INVERTED_EXCLAMATION_MARK,
                KeyEvent.VK_LEFT_PARENTHESIS to Key.VK_LEFT_PARENTHESIS,
                KeyEvent.VK_NUMBER_SIGN to Key.VK_NUMBER_SIGN,
                KeyEvent.VK_PLUS to Key.VK_PLUS,
                KeyEvent.VK_RIGHT_PARENTHESIS to Key.VK_RIGHT_PARENTHESIS,
                KeyEvent.VK_UNDERSCORE to Key.VK_UNDERSCORE,
                KeyEvent.VK_WINDOWS to Key.VK_WINDOWS,
                KeyEvent.VK_CONTEXT_MENU to Key.VK_CONTEXT_MENU,
                KeyEvent.VK_FINAL to Key.VK_FINAL,
                KeyEvent.VK_CONVERT to Key.VK_CONVERT,
                KeyEvent.VK_NONCONVERT to Key.VK_NONCONVERT,
                KeyEvent.VK_ACCEPT to Key.VK_ACCEPT,
                KeyEvent.VK_MODECHANGE to Key.VK_MODECHANGE,
                KeyEvent.VK_KANA to Key.VK_KANA,
                KeyEvent.VK_KANJI to Key.VK_KANJI,
                KeyEvent.VK_ALPHANUMERIC to Key.VK_ALPHANUMERIC,
                KeyEvent.VK_KATAKANA to Key.VK_KATAKANA,
                KeyEvent.VK_HIRAGANA to Key.VK_HIRAGANA,
                KeyEvent.VK_FULL_WIDTH to Key.VK_FULL_WIDTH,
                KeyEvent.VK_HALF_WIDTH to Key.VK_HALF_WIDTH,
                KeyEvent.VK_ROMAN_CHARACTERS to Key.VK_ROMAN_CHARACTERS,
                KeyEvent.VK_ALL_CANDIDATES to Key.VK_ALL_CANDIDATES,
                KeyEvent.VK_PREVIOUS_CANDIDATE to Key.VK_PREVIOUS_CANDIDATE,
                KeyEvent.VK_CODE_INPUT to Key.VK_CODE_INPUT,
                KeyEvent.VK_JAPANESE_KATAKANA to Key.VK_JAPANESE_KATAKANA,
                KeyEvent.VK_JAPANESE_HIRAGANA to Key.VK_JAPANESE_HIRAGANA,
                KeyEvent.VK_JAPANESE_ROMAN to Key.VK_JAPANESE_ROMAN,
                KeyEvent.VK_KANA_LOCK to Key.VK_KANA_LOCK,
                KeyEvent.VK_INPUT_METHOD_ON_OFF to Key.VK_INPUT_METHOD_ON_OFF,
                KeyEvent.VK_CUT to Key.VK_CUT,
                KeyEvent.VK_COPY to Key.VK_COPY,
                KeyEvent.VK_PASTE to Key.VK_PASTE,
                KeyEvent.VK_UNDO to Key.VK_UNDO,
                KeyEvent.VK_AGAIN to Key.VK_AGAIN,
                KeyEvent.VK_FIND to Key.VK_FIND,
                KeyEvent.VK_PROPS to Key.VK_PROPS,
                KeyEvent.VK_STOP to Key.VK_STOP,
                KeyEvent.VK_COMPOSE to Key.VK_COMPOSE,
                KeyEvent.VK_ALT_GRAPH to Key.VK_ALT_GRAPH,
                KeyEvent.VK_BEGIN to Key.VK_BEGIN,
                KeyEvent.VK_UNDEFINED to Key.VK_UNDEFINED
            )
            return ListenersHelperImpl(keyboardMapping = keyboardMapping)
        }
    }
}
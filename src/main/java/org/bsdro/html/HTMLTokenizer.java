package org.bsdro.html;

import java.util.ArrayList;
import java.util.List;

public class HTMLTokenizer {
    public static List<HTMLToken> parse(String htmlString) {
        List<HTMLToken> tokens = new ArrayList<>();
        TokenizerState state = TokenizerState.INITIAL;
        HTMLToken token = new HTMLToken(TokenType.TAG);

        for (char c : htmlString.toCharArray()) {
            switch (state) {
                case INITIAL:
                    state = TokenizerState.TAG;
                    break;
                case TEXT:
                    if (c == '<') {
                        if(!token.isEmpty()) {
                            tokens.add(token);
                        }
                        state = TokenizerState.TAG;
                        token = new HTMLToken(TokenType.TAG);
                    }
                    else {
                        token.addCharacter(c);
                    }
                    break;
                case TAG:
                    if (c == '>') {
                        tokens.add(token);
                        state = TokenizerState.TEXT;
                        token = new HTMLToken(TokenType.TEXT);
                    } else if (c == '"') {
                        state = TokenizerState.DOUBLE_QUOTED_STRING;
                        token.addCharacter(c);
                    } else if (c == '\'') {
                        state = TokenizerState.SINGLE_QUOTED_STRING;
                        token.addCharacter(c);
                    } else {
                        token.addCharacter(Character.toLowerCase(c));
                    }
                    break;
                case DOUBLE_QUOTED_STRING:
                    if (c == '"') {
                        state = TokenizerState.TAG;
                    }
                    token.addCharacter(c);
                    break;
                case SINGLE_QUOTED_STRING:
                    if (c == '\'') {
                        state = TokenizerState.TAG;
                    }
                    token.addCharacter(c);
                    break;
            }
        }


        return tokens;
    }

    private enum TokenizerState { INITIAL, TEXT, TAG, SINGLE_QUOTED_STRING, DOUBLE_QUOTED_STRING}
}

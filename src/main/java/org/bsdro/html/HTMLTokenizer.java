package org.bsdro.html;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HTMLTokenizer {
    private static final String LINK_PREFIX = "a href=";

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

    public static String[] filterLinks(List<HTMLToken> tokens, String hostname) {

        return tokens.stream()
                .filter(token -> token.getType() == TokenType.TAG)
                .filter(token -> token.getContentAsString().startsWith(LINK_PREFIX))
                .map(token -> token.getContentAsString().substring(8, token.getContentAsString().length() - 1))
                .filter(url -> url.startsWith("http://") || url.startsWith("/"))
                .map(url -> {
                    try {
                        URI uri = new URI(url);
                        String host = uri.getHost();
                        String path = uri.getPath();

                        return host != null ? host + path : hostname + path;
                    } catch (URISyntaxException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    private enum TokenizerState { INITIAL, TEXT, TAG, SINGLE_QUOTED_STRING, DOUBLE_QUOTED_STRING}
}

package org.bsdro.http;

public class HTMLToken {
    private final TokenType type;
    private final StringBuilder content;

    public HTMLToken(TokenType type) {
        this.type = type;
        content = new StringBuilder();
    }

    public TokenType getType() {
        return type;
    }

    public String getContentAsString() {
        return content.toString();
    }

    public void addCharacter(char c) {
        content.append(c);
    }

    public boolean isEmpty() {
        return content.toString().trim().isEmpty();
    }

    @Override
    public String toString() {
        return  type + " " + content.toString();
    }
}

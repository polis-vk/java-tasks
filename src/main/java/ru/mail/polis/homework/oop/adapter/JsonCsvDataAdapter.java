package ru.mail.polis.homework.oop.adapter;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {

    public JsonCsvDataAdapter(String text) {
        super(adaptJsonToCsv(text));
    }

    private static String adaptJsonToCsv(String json) {
        StringBuilder adaptedCsvBuilder = new StringBuilder();
        boolean objectBegan = false;
        TokenType tokenType = null;
        char[] charArray = json.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '{') {
                if (objectBegan) {
                    throw new IllegalArgumentException("Unexpected token");
                }
                objectBegan = true;
                continue;
            }

            if (c == '}') {
                if (tokenType != TokenType.VALUE
                        || (!objectBegan && i != charArray.length - 1)) {
                    throw new IllegalArgumentException("Unexpected token");
                }
                objectBegan = false;
                continue;
            }

            if (tokenType == null) {
                if (c == '"') {
                    tokenType = TokenType.KEY;
                    continue;
                }
                if (c == ' ' || c == '\n') {
                    continue;
                }
                throw new IllegalArgumentException("Unexpected token");
            }

            if (c == '\n') {
                throw new IllegalArgumentException("Unexpected token");
            }

            if (tokenType == TokenType.KEY) {
                if (c == '"') {
                    adaptedCsvBuilder.append(',');
                    tokenType = TokenType.AFTER_KEY;
                    continue;
                }
                adaptedCsvBuilder.append(c);
            }

            if (tokenType == TokenType.AFTER_KEY && c == ':') {
                tokenType = TokenType.BEFORE_VALUE;
                continue;
            }

            if (tokenType == TokenType.BEFORE_VALUE) {
                if (c <= '9' && c >= '0') {
                    adaptedCsvBuilder.append(c);
                    tokenType = TokenType.VALUE;
                    continue;
                }
            }

            if (c != ' ' && (tokenType == TokenType.AFTER_KEY
                    || tokenType == TokenType.BEFORE_VALUE)) {
                throw new IllegalArgumentException("Unexpected token");
            }

            if (tokenType == TokenType.VALUE) {
                if (c == ',') {
                    tokenType = null;
                    adaptedCsvBuilder.append('\n');
                    continue;
                }
                if (c > '9' || c < '0') {
                    throw new IllegalArgumentException("Unexpected token");
                }
                adaptedCsvBuilder.append(c);
            }
        }

        if (objectBegan) {
            throw new IllegalArgumentException();
        }

        return adaptedCsvBuilder.toString();
    }

    private enum TokenType {
        KEY, AFTER_KEY, BEFORE_VALUE, VALUE
    }

}

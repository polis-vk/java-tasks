package ru.mail.polis.homework.oop.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * После имплементации раскоментировать тест в ru.mail.polis.homework.oop.adapter.ProductPriceParsServiceTest
 * для проверки правильной имплементации
 */
public class JsonCsvDataAdapter extends CsvData {
    public JsonCsvDataAdapter(String text) {
        super(convertJsonToCsv(text));
    }

    private enum Context {
        DEFAULT, OBJECT, KEY, SEPARATOR, VALUE, COMMA
    }

    private static boolean checkWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private static String getErrorMsg(char[] expected, char got) {
        StringBuilder sB = new StringBuilder("Expected - [");
        sB.append('\'').append(expected[0]).append('\'');

        for (int i = 1; i < expected.length; i++) {
            sB.append(", '").append(expected[i]).append('\'');
        }

        return sB.append("], got - '").append(got).append("'.").toString();
    }

    private static String convertJsonToCsv(String text) {
        List<String> jsonTokens = new ArrayList<>();
        Context context = Context.DEFAULT;

        StringBuilder curToken = new StringBuilder();

        boolean isObjectOpen = true;
        boolean isReadingValue = false;
        boolean isValueString = false;
        for (int i = 0; i < text.length(); i++) {
            char curC = text.charAt(i);
            char nxtC = text.charAt(Math.min(i + 1, text.length() - 1));

            switch (context) {
                case DEFAULT:
                    if (curC == '{') {
                        context = Context.OBJECT;
                    } else if (!checkWhiteSpace(curC)) {
                        throw new IllegalArgumentException(getErrorMsg(new char[]{'{'}, curC));
                    }
                    break;
                case OBJECT:
                    if (curC == '"') {
                        context = Context.KEY;
                    } else if (curC == '}' && jsonTokens.size() == 0) {
                        isObjectOpen = false;
                    } else if (!checkWhiteSpace(curC)) {
                        throw new IllegalArgumentException(getErrorMsg(new char[]{'"'}, curC));
                    }
                    break;
                case KEY:
                    if (curC == '\\' && nxtC == '"') {
                        curToken.append('"');
                        i++;
                    } else if (curC == '"') {
                        jsonTokens.add(curToken.toString());
                        curToken = new StringBuilder();
                        context = Context.SEPARATOR;
                    } else {
                        curToken.append(curC);
                    }
                    break;
                case SEPARATOR:
                    if (curC == ':') {
                        context = Context.VALUE;
                        isReadingValue = false;
                        isValueString = false;
                    } else if (!checkWhiteSpace(curC)) {
                        throw new IllegalArgumentException(getErrorMsg(new char[]{':'}, curC));
                    }
                    break;
                case VALUE:
                    if (!isReadingValue && !checkWhiteSpace(curC)) {
                        isReadingValue = true;
                        if (curC == '"') {
                            isValueString = true;
                        } else {
                            curToken.append(curC);
                        }
                    } else if (isValueString) {
                        if (curC == '\\' && nxtC == '"') {
                            curToken.append('"');
                            i++;
                        } else if (curC == '"') {
                            jsonTokens.add(curToken.toString());
                            curToken = new StringBuilder();
                            context = Context.COMMA;
                        } else {
                            curToken.append(curC);
                        }
                    } else if (isReadingValue) {
                        if (checkWhiteSpace(curC) || curC == ',' || curC == '}') {
                            jsonTokens.add(curToken.toString());
                            curToken = new StringBuilder();
                            context = Context.COMMA;
                            i--;
                        } else {
                            curToken.append(curC);
                        }
                    }
                    break;
                case COMMA:
                    if (curC == '}') {
                        isObjectOpen = false;
                    } else if (curC == ',') {
                        context = Context.OBJECT;
                    } else if (!checkWhiteSpace(curC)) {
                        throw new IllegalArgumentException(getErrorMsg(new char[]{',', '}'}, curC));
                    }
                    break;
            }

            if (!isObjectOpen) {
                break;
            }
        }

        if (isObjectOpen) {
            throw new IllegalArgumentException("JSON is not complete");
        }

        StringBuilder strCsv = new StringBuilder();
        for (int i = 0; i < jsonTokens.size(); i += 2) {
            strCsv.append(jsonTokens.get(i)).append(',').append(jsonTokens.get(i + 1)).append('\n');
        }

        return strCsv.toString();
    }
}

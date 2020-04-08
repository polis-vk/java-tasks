package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

//Проверяю начинается ли предложение с большой буквой и оканчивается ли точкой.
public class CorrectCaseFilter implements TextAnalyzer {
    public static final FilterType type = FilterType.CUSTOM;

    @Override
    public FilterType analyze(String text) {
        boolean isAfterPoint = true;
        final String endLiterals = ".!?";
        for (char literal : text.toCharArray()) {
            if (literal == ' ') {
                continue;
            } else if (isAfterPoint && !isUppercase(literal)) {
                return type;
            } else if (endLiterals.contains(Character.toString(literal))) {
                isAfterPoint = true;
                continue;
            }
            isAfterPoint = false;
        }
        return defaultType;
    }

    @Override
    public FilterType getType() {
        return type;
    }

    private boolean isUppercase(char literal) {
        final char fistLitInAlphabet = 'A';
        final char lastLitInAlphabet = 'Z';
        return literal >= fistLitInAlphabet && literal <= lastLitInAlphabet;
    }


}

package ru.mail.polis.homework.analyzer;

public class SpaceAnalyzer implements TextAnalyzer {
    private final String[] wrongSpaces = {"  ", " ,", " .", " !", " ?"};

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public FilterType getFilterType(String str) {
        for (String space : wrongSpaces) {
            if (str.contains(space)) {
                return FilterType.WRONG_SPACE;
            }
        }
        return FilterType.GOOD;
    }
}

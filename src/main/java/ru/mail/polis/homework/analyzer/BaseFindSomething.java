package ru.mail.polis.homework.analyzer;

public abstract class BaseFindSomething implements TextAnalyzer {
    private final String[] SOMETHING;

    public BaseFindSomething() {
        this.SOMETHING = new String[]{"=(", ":(", ":|"};
    }

    public BaseFindSomething(String[] something) {
        this.SOMETHING = something;
    }

    @Override
    public FilterType analyze(String text) {
        for (String word : SOMETHING) {
            if (text.contains(word)) {
                return type();
            }
        }
        return FilterType.GOOD;
    }

    public abstract FilterType type();
}

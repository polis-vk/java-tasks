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
    public boolean analyze(String text) {
        for (String word : SOMETHING) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public abstract FilterType getType();
}

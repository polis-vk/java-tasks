package ru.mail.polis.homework.analyzer;

// Наследуемся от SpamAnalyzer, т.к. NegativeTextAnalyzer тоже своего рода SpamAnalyzer,
// и отличается у них лишь массив "спама" и тип анализатора
public class NegativeTextAnalyzer extends SpamAnalyzer {
    private final static String[] NEGATIVE_EMOJI = {"=(", ":(", ":|"};

    // Вызываем конструктор базового класса, т.к. он дублирует его поведение
    public NegativeTextAnalyzer() {
        super(NEGATIVE_EMOJI);
    }

    @Override
    public FilterType getAnalyzerType() {
        return FilterType.NEGATIVE_TEXT;
    }
}

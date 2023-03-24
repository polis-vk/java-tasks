package ru.mail.polis.homework.analyzer;


public class NegativeTextAnalyzer implements TextAnalyzer{
    private final String[] negativeWords = new String[]{"=(", ":(", ":|"};
    private final FilterType type = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean analyze(String text){
        for (String word : negativeWords) {
            if (text.contains(word)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public FilterType getType(){
        return type;
    }
}

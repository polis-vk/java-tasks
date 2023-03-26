package ru.mail.polis.homework.analyzer;


public class NegativeTextAnalyzer implements TextAnalyzer{
    private final String[] negativeWords = new String[]{"=(", ":(", ":|"};
    private final FilterType type = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean checkTextIsCorrect(String text){
        return TextAnalyzer.checkInclusion(text, negativeWords);
    }
    @Override
    public FilterType getType(){
        return type;
    }
}

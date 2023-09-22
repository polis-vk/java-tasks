package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer<T> implements TextAnalyzer {
    private T something;
    CustomAnalyzer(T something)
    {
        this.something = something;
    }

    @Override
    public FilterType getFilter() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean textAnalyzer(String text) {
        int space = 0;
        for(int i = 0; i < text.length(); ++i)
        {
            if(Character.isSpaceChar(text.charAt(i))) ++space;
            else
            {
                if(space > 1) return true;
                space = 0;
            }
        }
        return false;
    }
}

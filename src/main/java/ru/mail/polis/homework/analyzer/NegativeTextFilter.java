package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends Filter {
    private final String[] smiles = new String[]{"=(", ":(", ":|"};

    NegativeTextFilter(FilterType t) {
        super(t);
    }

    @Override
    public FilterType filterText(String text) {
        for (String smile :
                smiles) {
            if (text.contains(smile)) {
                return type;
            }
        }
        return FilterType.GOOD;
    }
}

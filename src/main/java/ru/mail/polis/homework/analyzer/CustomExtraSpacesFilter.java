package ru.mail.polis.homework.analyzer;

public class CustomExtraSpacesFilter implements TextAnalyzer {
    public FilterType type() {
        return FilterType.CUSTOM_EXTRA_SPACES;
    }

    public boolean isValid(String text) {
        if (text == null || text.isEmpty()) {
            return true;
        }

        String formattedText = text.trim().replaceAll("\\s+", " ");

        return text.length() == formattedText.length();
    }
}

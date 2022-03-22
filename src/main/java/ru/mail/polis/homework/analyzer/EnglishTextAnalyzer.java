package ru.mail.polis.homework.analyzer;

//Возвращает ENGLISH_TEXT, если все буквы text английские. Допустимые символы - пробелы, запятая и точка
public class EnglishTextAnalyzer implements TextAnalyzer {

    private boolean isEnglishLetter(char letter) {
        return letter >= 65 && letter <= 90 || letter >= 97 && letter <= 122;
    }

    private boolean isValidCharacter(char character) {
        return character == 44 || character == 46 || character == 32;
    }

    @Override
    public boolean analyze(String text) {
        if (text.isEmpty()) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!(isEnglishLetter(text.charAt(i)) || isValidCharacter(text.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getType() {
        return FilterType.ENGLISH_TEXT;
    }
}
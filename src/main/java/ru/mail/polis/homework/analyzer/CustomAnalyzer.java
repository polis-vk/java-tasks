package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {

    @Override
    public FilterType analyze(String txt) {
        txt = txt.replaceAll(" |[0-9]|\\p{Punct}", ""); //отрезаем все кроме букв
        if (txt.length() <= 1) {
            return FilterType.GOOD;
        }
        for (int k = 1; k < txt.length(); k++) {                           //проходим по строке
            if (checkSize(txt.charAt(k), txt.charAt(k - 1))) {             //если символы одного регистра
                return FilterType.GOOD;                                    //это уже не ЗаБоРчИк
            }
        }
        return FilterType.CUSTOM;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }


    private boolean checkSize(char a, char b) {
        if (Character.isUpperCase(a)) {
            if (Character.isLowerCase(b)) {
                return false;
            } else {
                return true;
            }
        } else {
            if (Character.isUpperCase(b)) {
                return false;
            } else {
                return true;
            }
        }
    }
}

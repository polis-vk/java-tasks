package ru.mail.polis.homework.analyzer;

public class CapslockOnAnalyzer implements TextAnalyzer {
//Если все буквы заглавные, то фильтр проходит

    public FilterType analyze(String text) {
        if (text == null|| text == "") {
            return FilterType.GOOD;
        }
       String newText = text.toUpperCase();
        if (newText == text){
            return FilterType.CAPS;
        }
        return FilterType.GOOD;
    }
}

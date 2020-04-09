package ru.mail.polis.homework.analyzer;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Кастомный фильтр, который проверяет, написан ли текст на кириллице
 * Выдает GOOD, если в тексте нет латинских букв
 * Если есть хотя бы одна буква на латинице, выдает NOT_CYRILLIC
 * Если текст только из цифр или полностью на русском, но содержит цифры - тоже GOOD
 */

public class IsInCyrillic implements TextAnalyzer {

    private static Pattern pattern = Pattern.compile(
            "[" +                   //начало списка допустимых символов
                    "а-яА-ЯёЁ" +    //буквы русского алфавита
                    "\\d" +         //цифры
                    "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                    "\\p{Punct}" +  //знаки пунктуации
                    "]" +           //конец списка допустимых символов
                    "*");           //допускается наличие указанных символов в любом количестве

    @Override
    public FilterType analyze(String arg) {
        Matcher matcher = pattern.matcher(arg);
        if (matcher.matches()) {
            return FilterType.GOOD;
        }
        return FilterType.NOT_CYRILLIC;
    }

    @Override
    public FilterType getFilterAnswer() {
        return FilterType.NOT_CYRILLIC;
    }
}

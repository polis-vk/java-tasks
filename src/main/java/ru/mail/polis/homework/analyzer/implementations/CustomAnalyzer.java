package ru.mail.polis.homework.analyzer.implementations;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

/**
 * Хитрые шпионы шифруют свои сообщения шифром цезаря
 * Однако удалось перехватить их код(сдвиг алфавита)
 * Код == 10 означает, что надо сдвинуть код буквы
 * зашифрованного сообщения на 10 чтобы дешифровать его
 */
public final class CustomAnalyzer extends KeywordAnalyzer implements TextAnalyzer {

    private final int password;
    private final FilterType filterType = FilterType.SPY_MESSAGE;

    public CustomAnalyzer(int password) {
        this.password = password;
        this.keywords = new String[] {"BOMB", "Plant A", "Plant B", "Caesar", "cipher"};
    }

    @Override
    public FilterType process(String text) {
        String dectypted = decrypt(text, password);
        return super.process(dectypted);
    }

    public static String decrypt(String message, int password) {
        StringBuilder res = new StringBuilder();
        for (char ch : message.toCharArray()) {
            res.append((char) (ch + password));
        }
        return res.toString();
    }

    @Override
    public FilterType getLabel() {
        return filterType;
    }
}

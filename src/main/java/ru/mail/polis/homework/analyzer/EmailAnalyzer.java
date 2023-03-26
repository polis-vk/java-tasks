package ru.mail.polis.homework.analyzer;

public class EmailAnalyzer implements TextAnalyzer {
    private final static char AT = '@';
    private final static int MAX_LENGTH = 50;
    private final static String[] DOMAINS = {"yandex.ru", "gmail.com", "mail.ru", "icloud.com", "outlook.com"};
    private final static String FORBIDDEN_CHAR_FOR_LOCAL_NAME = "<>()\\[]\",;:/* ";

    @Override
    public boolean checkCorrection(String email) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        if (email.length() > MAX_LENGTH) {
            return false;
        }
        int indexAt = email.indexOf(AT);
        if (indexAt < 1 || indexAt >= email.length() - 1) {
            return false;
        }
        char[] localName = new char[indexAt];
        email.getChars(0, indexAt, localName, 0);
        if (containsForbiddenChar(localName) || containsDomain(localName)) {
            return false;
        }
        char[] mailDomain = new char[email.length() - indexAt - 1];
        email.getChars(indexAt + 1, email.length(), mailDomain, 0);
        return containsDomain(mailDomain);
    }

    private boolean containsForbiddenChar(char[] text) {
        for (char symbol : text) {
            if (FORBIDDEN_CHAR_FOR_LOCAL_NAME.contains(String.valueOf(symbol))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsDomain(char[] text) {
        for (String domain : DOMAINS) {
            if (String.valueOf(text).equals(domain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.INCORRECT_EMAIL;
    }
}


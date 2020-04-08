package ru.mail.polis.homework.analyzer;

public class LinksFilter implements TextAnalyzer {
    /**
     * находит в тексте ссылки
     */
    private static final FilterType FILTER_TYPE = FilterType.CUSTOM;
    private static final String[] PROTOCOLS = {"https://", "http://"};
    private static final int MIN_LENGTH_OF_LINK = 5; // после протокола
    private final String[] DOMAINS;

    public LinksFilter(String[] domains) {
        this.DOMAINS = domains.clone();
    }

    @Override
    public int getFilterPriority() {
        return FILTER_TYPE.getPriority();
    }

    @Override
    public FilterType analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (searchForLinks(str)) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }

    private boolean searchForLinks(String str) {
        for (String protocol : PROTOCOLS) {
            if (str.contains(protocol)) {
                // если протокол найден, начинается поиск домена
                int indexAfterProtocol = str.indexOf(protocol) + protocol.length();
                // если строка меньше, чем ссылка, пропуск итерации
                if (str.length() - indexAfterProtocol < MIN_LENGTH_OF_LINK) {
                    continue;
                }

                // поиск домена осуществляется до первого пробела или до конца строки
                int endIndexOfPotentialLink;
                if (str.indexOf(" ", indexAfterProtocol) != -1) {
                    endIndexOfPotentialLink = str.indexOf(" ", indexAfterProtocol);
                    // если между протоколом и пробелом ссылка не помещается - пропуск итерации
                    if (endIndexOfPotentialLink - indexAfterProtocol + 1 < MIN_LENGTH_OF_LINK) {
                        continue;
                    }
                } else {
                    endIndexOfPotentialLink = str.length();
                }

                String potentialLinks = str.substring(indexAfterProtocol, endIndexOfPotentialLink);
                for (String domain : DOMAINS) {
                    if (potentialLinks.contains(domain)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}

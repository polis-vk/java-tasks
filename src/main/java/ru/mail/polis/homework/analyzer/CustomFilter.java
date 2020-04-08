package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    /**
     * находит в тексте ссылки
     */
    private static int priority;
    private static FilterType filterType = FilterType.CUSTOM;
    private static final String[] protocols = {"https://", "http://"};
    private final String[] domains;
    private static final int MIN_LENGTH_OF_LINK = 5; // после протокола

    public CustomFilter(String[] domains) {
        this.domains = domains;
    }

    @Override
    public void setFilterPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getFilterPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public FilterType Analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (searchForLinks(str)) {
            return filterType;
        }
        return FilterType.GOOD;
    }

    private boolean searchForLinks(String str) {
        for (String protocol : protocols) {
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
                for (String domain : domains) {
                    if (potentialLinks.contains(domain)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}

package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

/**
 * Типы фильтров (2 балла)
 */
enum FilterType {
    GOOD(-1),
    NEGATIVE_TEXT(3),
    CUSTOM(4),
    TOO_LONG(2),
    SPAM(1);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public static void SortPriority(TextAnalyzer[] filters) {
        Arrays.sort(filters, (filter1, filter2) -> {
            if (filter1.filterType().getPriority() < filter2.filterType().getPriority()) {
                return -1;
            }
            return filter1.filterType().getPriority() == filter2.filterType().getPriority() ? 0 : 1;
        });
    }

    int getPriority() {
        return priority;
    }
}

package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(0),
    TOO_LONG(1),
    NEGATIVE_TEXT(2),
    REFERENCE(3),
    GOOD(Integer.MAX_VALUE);
    
    private final int priority;
    
    FilterType(int priority){
        this.priority = priority;
    }
    
    public int getPriority() {
        return priority;
    }
}

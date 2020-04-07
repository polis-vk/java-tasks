package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM,
    TOO_LONG,
    NEGATIVE_TEXT,
    REFERENCE,
    GOOD;
    
    private int priority;
    
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int getPriority() {
        return priority;
    }
}

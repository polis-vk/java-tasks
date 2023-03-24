package ru.mail.polis.homework.analyzer;

import javax.swing.*;

public class SpamAnalyzer extends ForbiddenTextAnalyzer {
    public SpamAnalyzer(String[] spam) {
        forbiddenText = spam;
        filterType = FilterType.SPAM;
    }
}

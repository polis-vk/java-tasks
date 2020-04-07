package ru.mail.polis.homework.analyzer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextFilterManagerTest {

    @Test
    public void analyzeEmptyFilters() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[0]);
        assertEquals("GOOD", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("������� ��� �� ���").toString());
        assertEquals("GOOD", manager.analyze("����������� ������������������ ���������������������").toString());
    }

    @Test
    public void analyzeOnlyLongFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(19)});
        assertEquals("GOOD", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("������� ��� �� ���  ").toString());
        assertEquals("TOO_LONG", manager.analyze("����������� ������������������ ���������������������").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(0)});
        assertEquals("TOO_LONG", manager.analyze("������, � ����").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("������� ��� �� ���  ").toString());
        assertEquals("TOO_LONG", manager.analyze("����������� ������������������ ���������������������").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createTooLongAnalyzer(19),
                TextAnalyzer.createTooLongAnalyzer(8)});
        assertEquals("TOO_LONG", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("������� ��� �� ���  ").toString());
        assertEquals("TOO_LONG", manager.analyze("����������� ������������������ ���������������������").toString());

    }

    @Test
    public void analyzeOnlySpamFilter() {
        TextFilterManager manager = new TextFilterManager(
                new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{"�", "�"})});
        assertEquals("SPAM", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("������� ��� �� ���  :(").toString());
        assertEquals("SPAM", manager.analyze("������� ��� �� ��� ���������� ").toString());
        assertEquals("SPAM", manager.analyze("����������� ������������������ ���������������������").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createSpamAnalyzer(new String[]{"�"}),
                TextAnalyzer.createSpamAnalyzer(new String[]{"�"})});
        assertEquals("SPAM", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("������� ��� �� ���  :(").toString());
        assertEquals("SPAM", manager.analyze("������� ��� �� ��� ���������� ").toString());
        assertEquals("SPAM", manager.analyze("����������� ������������������ ���������������������").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{})});
        assertEquals("GOOD", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("������� ��� �� ���  :(").toString());
        assertEquals("GOOD", manager.analyze("������� ��� �� ��� ���������� ").toString());
        assertEquals("GOOD", manager.analyze("����������� ������������������ ���������������������").toString());
    }

    @Test
    public void analyzeOnlyNegativeFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createNegativeTextAnalyzer()});
        assertEquals("NEGATIVE_TEXT", manager.analyze("������, � ���� :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("������� ��� �� ��� :-( ").toString());
        assertEquals("NEGATIVE_TEXT", manager.analyze("������� ��� �� ��� ���������� :|").toString());
        assertEquals("GOOD", manager.analyze("����������� ������������������ ���������������������").toString());
    }

    @Test
    public void analyzeAllFiltersGood() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"������", "���", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20)});
        assertEquals("GOOD", manager.analyze("������, � ���� :-(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("���������������:-(").toString());
        assertEquals("GOOD", manager.analyze("��� ���������� ;|").toString());
    }

    @Test
    public void analyzeAllFiltersOne() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"������", "���", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20)});
        assertEquals("NEGATIVE_TEXT", manager.analyze("������, � ���� :(").toString());
        assertEquals("TOO_LONG", manager.analyze("������� ��� �� ��� :-(").toString());
        assertEquals("SPAM", manager.analyze("��� ���������� ;|").toString());
    }

    @Test
    public void analyzeAllFiltersMany() {
        manyFilters(false);
    }


    @Test
    public void analyzeAllFiltersManyWithPriority() {
        manyFilters(true);
    }

    private void manyFilters(boolean withPriority) {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"������", "���", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20)});
        if (withPriority) {
            assertEquals("SPAM", manager.analyze("������, � ���� ��� ��� cvv").toString());
            assertEquals("TOO_LONG", manager.analyze("������� ��� �� ��� :(").toString());
            assertEquals("SPAM", manager.analyze("��� ����������           :|").toString());
        } else {
            assertTrue(Arrays.asList("SPAM", "TOO_LONG").contains(
                    manager.analyze("������, � ���� ��� ��� cvv").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG").contains(
                    manager.analyze("������� ��� �� ��� :(").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG", "SPAM").contains(
                    manager.analyze("��� ����������           =(").toString()));
        }
    }


}
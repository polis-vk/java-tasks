package ru.mail.polis.homework.analyzer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextFilterManagerTest {

    @Test
    public void analyzeEmptyFilters() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[0]);

        assertEquals("GOOD", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
    }

    @Test
    public void analyzeOnlyLongFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(19)});
        assertEquals("GOOD", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("Скажите код из смс  ").toString());
        assertEquals("TOO_LONG", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(0)});
        assertEquals("TOO_LONG", manager.analyze("Привет, я Петя").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("Скажите код из смс  ").toString());
        assertEquals("TOO_LONG", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createTooLongAnalyzer(19),
                TextAnalyzer.createTooLongAnalyzer(8)});
        assertEquals("TOO_LONG", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("Скажите код из смс  ").toString());
        assertEquals("TOO_LONG", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());

    }

    @Test
    public void analyzeOnlySpamFilter() {
        TextFilterManager manager = new TextFilterManager(
                new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{"я", "й"})});
        assertEquals("SPAM", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс  :(").toString());
        assertEquals("SPAM", manager.analyze("Скажите код из смс пожалуйста ").toString());
        assertEquals("SPAM", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createSpamAnalyzer(new String[]{"я"}),
                TextAnalyzer.createSpamAnalyzer(new String[]{"й"})});
        assertEquals("SPAM", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс  :(").toString());
        assertEquals("SPAM", manager.analyze("Скажите код из смс пожалуйста ").toString());
        assertEquals("SPAM", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{})});
        assertEquals("GOOD", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс  :(").toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс пожалуйста ").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
    }

    @Test
    public void analyzeOnlyNegativeFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createNegativeTextAnalyzer()});
        assertEquals("NEGATIVE_TEXT", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс :-( ").toString());
        assertEquals("NEGATIVE_TEXT", manager.analyze("Скажите код из смс пожалуйста :|").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
    }

    @Test
    public void analyzeOnlyCustomFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createCustomAnalyzer(false)});
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс :-( ").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
        assertEquals("GOOD", manager.analyze("Привет, а я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("Привет а я Петя :(").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createCustomAnalyzer(true)});
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс :-( ").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
        assertEquals("GOOD", manager.analyze("Привет, а я Петя :(").toString());
        assertEquals("CUSTOM", manager.analyze("Привет а я Петя :(").toString());
        assertEquals("CUSTOM", manager.analyze("Привет когда деньги вернёшь?").toString());
    }

    @Test
    public void analyzeAllFiltersGood() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"пинкод", "смс", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20),
                TextAnalyzer.createCustomAnalyzer(true)});

        assertEquals("GOOD", manager.analyze("Привет, я Петя :-(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("СкажитеКодИзСмс:-(").toString());
        assertEquals("GOOD", manager.analyze("сМс пожалуйста ;|").toString());
        assertEquals("GOOD", manager.analyze("Привет, а я Петя").toString());
    }

    @Test
    public void analyzeAllFiltersOne() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"пинкод", "смс", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(29),
                TextAnalyzer.createCustomAnalyzer(true)});

        assertEquals("NEGATIVE_TEXT", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("TOO_LONG", manager.analyze("Скажите Код Из Смс :-(. Очень надо.").toString());
        assertEquals("SPAM", manager.analyze("смс пожалуйста ;|").toString());
        assertEquals("CUSTOM", manager.analyze("Привет когда деньги вернёшь?").toString());

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
                TextAnalyzer.createSpamAnalyzer(new String[]{"пинкод", "смс", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20),
                TextAnalyzer.createCustomAnalyzer(true)});
        if (withPriority) {
            assertEquals("SPAM", manager.analyze("Привет, я Петя вот мой cvv").toString());
            assertEquals("TOO_LONG", manager.analyze("Скажите Код Из Смс :(").toString());
            assertEquals("SPAM", manager.analyze("смс пожалуйста           :|").toString());
            assertEquals("SPAM", manager.analyze("смс пожалуйста, когда придёшь домой :|").toString());
            assertEquals("CUSTOM", manager.analyze("Привет а я Петя|").toString());
        } else {
            assertTrue(Arrays.asList("SPAM", "TOO_LONG").contains(
                    manager.analyze("Привет, я Петя вот мой cvv").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG").contains(
                    manager.analyze("Скажите Код Из Смс :(").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG", "SPAM").contains(
                    manager.analyze("смс пожалуйста           =(").toString()));
            assertTrue(Arrays.asList("SPAM", "CUSTOM", "NEGATIVE_TEXT").contains(
                    manager.analyze("смс пожалуйста, когда придёшь домой :|").toString()));
        }
    }


}
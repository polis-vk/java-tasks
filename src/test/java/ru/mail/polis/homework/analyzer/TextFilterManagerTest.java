package ru.mail.polis.homework.analyzer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextFilterManagerTest {

    @Test
    public void analyzeEmptyFilters() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[0]);
        assertEquals("GOOD", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Giv me code from sms  ").toString());
        assertEquals("GOOD", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());
    }

    @Test
    public void analyzeOnlyLongFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(19)});
        assertEquals("GOOD", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("Giv me code from sms  ").toString());
        assertEquals("TOO_LONG", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(0)});
        assertEquals("TOO_LONG", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("Giv me code from sms  ").toString());
        assertEquals("TOO_LONG", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createTooLongAnalyzer(19),
                TextAnalyzer.createTooLongAnalyzer(8)});
        assertEquals("TOO_LONG", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("TOO_LONG", manager.analyze("Giv me code from sms  ").toString());
        assertEquals("TOO_LONG", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());

    }

    @Test
    public void analyzeOnlySpamFilter() {
        TextFilterManager manager = new TextFilterManager(
                new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{"a", "l"})});
        assertEquals("SPAM", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Giv me code from sms  ").toString());
        assertEquals("SPAM", manager.analyze("Giv me code from sms please ").toString());
        assertEquals("SPAM", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createSpamAnalyzer(new String[]{"a"}),
                TextAnalyzer.createSpamAnalyzer(new String[]{"l"})});
        assertEquals("SPAM", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Giv me code from sms    :(").toString());
        assertEquals("SPAM", manager.analyze("Giv me code from sms please ").toString());
        assertEquals("SPAM", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());

        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{})});
        assertEquals("GOOD", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Giv me code from sms  :(").toString());
        assertEquals("GOOD", manager.analyze("Giv me code from sms please ").toString());
        assertEquals("GOOD", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());
    }

    @Test
    public void analyzeOnlyCorrectCaseFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createCustomAnalyzer()});
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("I am").toString());
        assertEquals("GOOD", manager.analyze("I. I").toString());
        assertEquals("CUSTOM", manager.analyze("il").toString());
        assertEquals("CUSTOM", manager.analyze("Il!il").toString());
    }

    @Test
    public void analyzeOnlyNegativeFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createNegativeTextAnalyzer()});
        assertEquals("NEGATIVE_TEXT", manager.analyze("I am Petr :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Giv me code from sms  :-( ").toString());
        assertEquals("NEGATIVE_TEXT", manager.analyze("Giv me code from sms please :|").toString());
        assertEquals("GOOD", manager.analyze("Verryyyyyyyy lonnnnnngggggggggggg stringggggggggggggggg").toString());
    }

    @Test
    public void analyzeAllFiltersGood() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"code", "sms", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(25)});
        assertEquals("GOOD", manager.analyze("I am Petr ;(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("SayCodeFromSms:-(").toString());
        assertEquals("GOOD", manager.analyze("SМS please ;|.").toString());
        assertEquals("GOOD", manager.analyze("Sms! Please.").toString());
    }

    @Test
    public void analyzeAllFiltersOne() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"code", "sms", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20),
                TextAnalyzer.createCustomAnalyzer()});
        assertEquals("NEGATIVE_TEXT", manager.analyze("I am Petr :(").toString());
        assertEquals("TOO_LONG", manager.analyze("Say Code from Sms :-(").toString());
        assertEquals("SPAM", manager.analyze("sms please ;|").toString());
        assertEquals("CUSTOM", manager.analyze("SMS .please").toString());
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
                TextAnalyzer.createSpamAnalyzer(new String[]{"code", "sms", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20),
                TextAnalyzer.createCustomAnalyzer()});
        if (withPriority) {
            assertEquals("SPAM", manager.analyze("Hello, I am petr and my its cvv").toString());
            assertEquals("TOO_LONG", manager.analyze("Say Code. in smS          :(").toString());
            assertEquals("SPAM", manager.analyze("sms please           :|").toString());
            assertEquals("NEGATIVE_TEXT", manager.analyze("Sms.please :(").toString());
        } else {
            assertTrue(Arrays.asList("SPAM", "TOO_LONG").contains(
                    manager.analyze("Hello, I am petr and my its cvv ").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG").contains(
                    manager.analyze("Say Code. in smS         :(").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG", "SPAM").contains(
                    manager.analyze("Sms.please sms              =(").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG", "SPAM", "CUSTOM").contains(
                    manager.analyze("hello code :(           ").toString()));
        }
    }


}
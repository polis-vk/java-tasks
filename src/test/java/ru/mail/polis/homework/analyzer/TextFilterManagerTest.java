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
        assertEquals("GOOD", manager.analyze("http://web5-site.com/directory").toString());
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
        
        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createTooLongAnalyzer(35)});
        assertEquals("GOOD", manager.analyze("http://web5-site.com/directory").toString());
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
        assertEquals("GOOD", manager.analyze("http://web5-site.com/directory").toString());
        
        manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createSpamAnalyzer(new String[]{"я"}),
                TextAnalyzer.createSpamAnalyzer(new String[]{"й"})});
        assertEquals("SPAM", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс  :(").toString());
        assertEquals("SPAM", manager.analyze("Скажите код из смс пожалуйста ").toString());
        assertEquals("SPAM", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
        assertEquals("GOOD", manager.analyze("http://web5-site.com/directory").toString());
        
        manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createSpamAnalyzer(new String[]{})});
        assertEquals("GOOD", manager.analyze("Привет, я Петя :(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс  :(").toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс пожалуйста ").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
        assertEquals("GOOD", manager.analyze("http://web5-site.com/directory").toString());
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
        assertEquals("GOOD", manager.analyze("http://web5-site.com/directory").toString());
    }
    
    @Test
    public void analyzeOnlyReferenceFilter() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{TextAnalyzer.createReferenceAnalyzer()});
        assertEquals("REFERENCE", manager.analyze("http://web5-site.com/directory").toString());
        assertEquals("REFERENCE", manager.analyze("sakjsdh kjsn https://fa-st.web9site.com/directory/file.filename dsds").toString());
        assertEquals("REFERENCE", manager.analyze("https://website.com/?querystring").toString());
        assertEquals("REFERENCE", manager.analyze("sdasda http://www.website.com/?key=val sasdsa").toString());
        assertEquals("REFERENCE", manager.analyze("asdsd http://www.website.com/?key=val#anchor  ryads ").toString());
        
        assertEquals("GOOD", manager.analyze("sdsdsa http://website.c-om/directory sas").toString());
        assertEquals("GOOD", manager.analyze("sdsdsa www.w;ebsite.?com/ sas").toString());
        assertEquals("GOOD", manager.analyze("sdsdsa https://website sas").toString());
        assertEquals("GOOD", manager.analyze("sdsdsa http://? sas").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("Скажите код из смс пожалуйста :|").toString());
        assertEquals("GOOD", manager.analyze("Ооооооочень длиннннннаааааяяяя стрроооооооккккаааааа").toString());
    }
    
    
    @Test
    public void analyzeAllFiltersGood() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"пинкод", "смс", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(20),
                TextAnalyzer.createReferenceAnalyzer()});
        assertEquals("GOOD", manager.analyze("Привет, я Петя :-(").toString());
        assertEquals("GOOD", manager.analyze("").toString());
        assertEquals("GOOD", manager.analyze(null).toString());
        assertEquals("GOOD", manager.analyze("СкажитеКодИзСмс:-(").toString());
        assertEquals("GOOD", manager.analyze("сМс пожалуйста ;|").toString());
        assertEquals("GOOD", manager.analyze("https://website").toString());
    }
    
    @Test
    public void analyzeAllFiltersOne() {
        TextFilterManager manager = new TextFilterManager(new TextAnalyzer[]{
                TextAnalyzer.createNegativeTextAnalyzer(),
                TextAnalyzer.createSpamAnalyzer(new String[]{"пинкод", "смс", "cvv"}),
                TextAnalyzer.createTooLongAnalyzer(25),
                TextAnalyzer.createReferenceAnalyzer()});
        assertEquals("NEGATIVE_TEXT", manager.analyze("https://web Смс :(").toString());
        assertEquals("TOO_LONG", manager.analyze("https://websiteeeeeee Смс :-(").toString());
        assertEquals("SPAM", manager.analyze("смс https://web ;|").toString());
        assertEquals("REFERENCE", manager.analyze("Cмс https://vk.com/ ;|").toString());
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
                TextAnalyzer.createReferenceAnalyzer()});
        if (withPriority) {
            FilterType.SPAM.setPriority(0);
            FilterType.TOO_LONG.setPriority(1);
            FilterType.NEGATIVE_TEXT.setPriority(2);
            FilterType.REFERENCE.setPriority(3);
            FilterType.GOOD.setPriority(4);
            assertEquals("SPAM", manager.analyze("Привет, я Петя вот мой https://www.google.com/ cvv :(").toString());
            assertEquals("TOO_LONG", manager.analyze("Скажите Код Из Смс :( https://www.google.com/").toString());
            assertEquals("NEGATIVE_TEXT", manager.analyze("https://vk.com/ :|").toString());
            assertEquals("REFERENCE", manager.analyze("https://vk.com/ :-)").toString());
        } else {
            assertTrue(Arrays.asList("SPAM", "TOO_LONG").contains(
                    manager.analyze("Привет, я Петя вот мой cvv").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG").contains(
                    manager.analyze("Скажите Код Из Смс :(").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG", "SPAM").contains(
                    manager.analyze("смс пожалуйста           =(").toString()));
            assertTrue(Arrays.asList("NEGATIVE_TEXT", "TOO_LONG", "SPAM", "REFERENCE").contains(
                    manager.analyze("смс пожалуйста    https://www.google.com/       =(").toString()));
            assertTrue(Arrays.asList("SPAM", "REFERENCE").contains(
                    manager.analyze("смс https://vk.com").toString()));
        }
    }
    
    
}
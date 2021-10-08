package ru.mail.polis.homework.processor;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TextProcessorManagerTest {

    @Test
    public void validateWrongProcessingSequences() {
        assertFalse(isValid(
                TextProcessor.trimProcessor(10), // POST_PROCESSING
                TextProcessor.squashWhiteSpacesProcessor() // PRE_PROCESSING
        ));
        assertFalse(isValid(
                TextProcessor.replaceFirstProcessor("A", "B"), // PROCESSING
                TextProcessor.squashWhiteSpacesProcessor() // PRE_PROCESSING
        ));
        assertFalse(isValid(
                TextProcessor.trimProcessor(10), // POST_PROCESSING
                TextProcessor.replaceFirstProcessor("A", "B") // PROCESSING
        ));
        assertFalse(isValid(
                TextProcessor.squashWhiteSpacesProcessor(), // PRE_PROCESSING
                TextProcessor.trimProcessor(10), // POST_PROCESSING
                TextProcessor.replaceFirstProcessor("A", "B") // PROCESSING
        ));
    }

    @Test
    public void validateGoodProcessingSequences() {
        assertTrue(isValid());
        assertTrue(isValid(
                TextProcessor.squashWhiteSpacesProcessor() // PRE_PROCESSING
        ));
        assertTrue(isValid(
                TextProcessor.replaceFirstProcessor("A", "B") // PROCESSING
        ));
        assertTrue(isValid(
                TextProcessor.trimProcessor(10) // POST_PROCESSING
        ));
        assertTrue(isValid(
                TextProcessor.squashWhiteSpacesProcessor(), // PRE_PROCESSING
                TextProcessor.trimProcessor(10) // POST_PROCESSING
        ));
        assertTrue(isValid(
                TextProcessor.squashWhiteSpacesProcessor(), // PRE_PROCESSING
                TextProcessor.replaceFirstProcessor("A", "B") // PROCESSING
        ));
        assertTrue(isValid(
                TextProcessor.replaceFirstProcessor("A", "B"), // PROCESSING
                TextProcessor.trimProcessor(10) // POST_PROCESSING
        ));
        assertTrue(isValid(
                TextProcessor.squashWhiteSpacesProcessor(), // PRE_PROCESSING
                TextProcessor.replaceFirstProcessor("A", "B"), // PROCESSING
                TextProcessor.trimProcessor(10) // POST_PROCESSING
        ));
    }

    @Test
    public void processNullText() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.squashWhiteSpacesProcessor(),
                TextProcessor.upperCaseProcessor()
        });
        assertNull(manager.processText(null));
    }

    @Test
    public void processEmptySequenceNullText() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
        });
        assertNull(manager.processText(null));
    }

    @Test
    public void processEmptyText() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.squashWhiteSpacesProcessor(),
                TextProcessor.upperCaseProcessor()
        });
        assertEquals("", manager.processText(""));
    }

    @Test
    public void processWrongSequence() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.upperCaseProcessor(),
                TextProcessor.squashWhiteSpacesProcessor()
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals("Hello, World!", manager.processText("Hello, World!"));
        assertEquals("       \t\t \n      \t\t\n\n", manager.processText("       \t\t \n      \t\t\n\n"));
    }

    @Test
    public void processSquashOnly() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.squashWhiteSpacesProcessor()
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals(" ", manager.processText("       \t\t \n      \t\t\n\n"));
        assertEquals("A B C D E", manager.processText("A  B\tC    \t\tD\nE"));
        assertEquals(" A B C D E", manager.processText("\t  A  B\tC    \t\tD\nE"));
        assertEquals(" A B C D E ", manager.processText("\t  A  B\tC    \t\tD\nE\n"));
    }

    @Test
    public void processReplaceFirstProcessorOnly() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.replaceFirstProcessor("ABA", "ACA")
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals("ACA", manager.processText("ABA"));
        assertEquals("ACA", manager.processText("ACA"));
        assertEquals("ACA ABA ABA", manager.processText("ABA ABA ABA"));
        assertEquals("ACA ACA ABA", manager.processText("ACA ABA ABA"));
        assertEquals("ACABABA", manager.processText("ABABABA"));

        manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.replaceFirstProcessor("ABA", "")
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals("", manager.processText("ABA"));
        assertEquals("ACA", manager.processText("ACA"));
        assertEquals(" ABA ABA", manager.processText("ABA ABA ABA"));
        assertEquals("ACA  ABA", manager.processText("ACA ABA ABA"));
        assertEquals("BABA", manager.processText("ABABABA"));
    }

    @Test
    public void processTrimProcessorOnly() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.trimProcessor(0)
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals("", manager.processText("ABA"));
        assertEquals("", manager.processText("ABA ABA ABA"));
        assertEquals("", manager.processText("ACA ABA ABA"));
        assertEquals("", manager.processText("ABABABA"));

        manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.trimProcessor(3)
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals("ABA", manager.processText("ABA"));
        assertEquals("ABA", manager.processText("ABA ABA ABA"));
        assertEquals("ACA", manager.processText("ACA ABA ABA"));
        assertEquals("ABA", manager.processText("ABABABA"));
    }

    @Test
    public void processComplexProcessor() {
        TextProcessorManager manager = TextProcessorManager.construct(new TextProcessor[] {
                TextProcessor.squashWhiteSpacesProcessor(),
                TextProcessor.replaceFirstProcessor("A B", "B A"),
                TextProcessor.replaceFirstProcessor("X", "Y"),
                TextProcessor.replaceFirstProcessor("CAB", "BAC"),
                TextProcessor.upperCaseProcessor(),
                TextProcessor.trimProcessor(10),
        });
        assertNull(manager.processText(null));
        assertEquals("", manager.processText(""));
        assertEquals("ABA", manager.processText("ABA"));
        assertEquals("BAC", manager.processText("CAB"));
        assertEquals(" B A C D E", manager.processText("\t  A  B\tC    \t\tD\ne\n"));
        assertEquals(" BAC A YYZ", manager.processText("\t  CAA  B\t   \t  \nXyz    \t\tD\ne\n"));
    }

    private static boolean isValid(TextProcessor ... processors) {
        return TextProcessorManager.isValidSequence(processors);
    }
}

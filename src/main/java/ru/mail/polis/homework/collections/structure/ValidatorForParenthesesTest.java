package ru.mail.polis.homework.collections.structure;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorForParenthesesTest {

    @Test
    public void testValidationWithCorrectString() {
        assertTrue(ValidatorForParentheses.validate("{}[]()<>"));
        assertTrue(ValidatorForParentheses.validate("{{()<>}}[]()<>"));
        assertTrue(ValidatorForParentheses.validate("(-b + (x)^2)/(2+4)"));
    }

    @Test
    public void testValidationWithIncorrectString() {
        assertFalse(ValidatorForParentheses.validate("Понедельники меня угнетают (("));
        assertFalse(ValidatorForParentheses.validate("([)]"));
        assertFalse(ValidatorForParentheses.validate("{{()<>}[]()<>"));
        assertFalse(ValidatorForParentheses.validate(">>>"));
        assertFalse(ValidatorForParentheses.validate(""));
        assertFalse(ValidatorForParentheses.validate("Понедельники меня угнетают"));
        assertFalse(ValidatorForParentheses.validate(null));
    }

}
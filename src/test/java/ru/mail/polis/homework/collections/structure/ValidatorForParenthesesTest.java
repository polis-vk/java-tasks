package ru.mail.polis.homework.collections.structure;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorForParenthesesTest {

    public void testValidationWithCorrectString() {
        assertTrue(ValidatorForParentheses.validate("{}[]()<>"));
        assertTrue(ValidatorForParentheses.validate("{{()<>}}[]()<>"));
        assertTrue(ValidatorForParentheses.validate("(-b + (x)^2)/(2+4)"));
    }

    public void testValidationWithIncorrectString() {
        assertFalse(ValidatorForParentheses.validate("Понедельники меня угнетают (("));
        assertFalse(ValidatorForParentheses.validate("([)]"));
        assertFalse(ValidatorForParentheses.validate("{{()<>}[]()<>"));
        assertFalse(ValidatorForParentheses.validate(""));
        assertFalse(ValidatorForParentheses.validate(null));
    }

}
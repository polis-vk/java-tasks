package ru.mail.polis.homework.collections.structure;

import static org.junit.Assert.assertTrue;

public class ValidatorForParenthesesTest {

    public void testValidationWithCorrectString() {
        assertTrue(ValidatorForParentheses.validate("{}[]()<>"));
        assertTrue(ValidatorForParentheses.validate("{{()<>}}[]()<>"));
        assertTrue(ValidatorForParentheses.validate("(-b + (x)^2)/(2+4)"));
    }

    public void testValidationWithIncorrectString() {
        assertTrue(ValidatorForParentheses.validate("Понедельники меня угнетают (("));
        assertTrue(ValidatorForParentheses.validate("([)]"));
        assertTrue(ValidatorForParentheses.validate("{{()<>}[]()<>"));
        assertTrue(ValidatorForParentheses.validate(""));
        assertTrue(ValidatorForParentheses.validate(null));
    }

}
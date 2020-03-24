package ru.mail.polis.homework.objects;

public class StringTasks {
    
    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String numericString = getNumericString(str);
        if (numericString == null || numericString.isEmpty()) {
            return null;
        }
        if (numericString.contains("e") || numericString.contains(".")) {
            return getDouble(numericString);
        }
        
        long number = getLong(numericString);
        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            return number;
        }
        return (int) number;
    }
    
    private enum SymbolType {
        DIGIT,
        E,
        DOT,
        MINUS,
        DEFAULT
    }
    
    private static String getNumericString(String str) {
        boolean dotIsHere = false;
        boolean eIsHere = false;
        SymbolType prevSymbolType = SymbolType.DEFAULT;
        StringBuilder numericStringBuilder = new StringBuilder();
        
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            
            if (symbol == '.') {
                if (dotIsHere || eIsHere || prevSymbolType != SymbolType.DIGIT) {
                    return null;
                }
                dotIsHere = true;
                prevSymbolType = SymbolType.DOT;
                numericStringBuilder.append(symbol);
                
            } else if (symbol == 'e') {
                if (eIsHere || prevSymbolType != SymbolType.DIGIT) {
                    return null;
                }
                eIsHere = true;
                prevSymbolType = SymbolType.E;
                numericStringBuilder.append(symbol);
                
            } else if (symbol == '-') {
                if (prevSymbolType != SymbolType.DEFAULT && prevSymbolType != SymbolType.E) {
                    return null;
                }
                prevSymbolType = SymbolType.MINUS;
                numericStringBuilder.append(symbol);
                
            } else if (Character.isDigit(symbol)) {
                prevSymbolType = SymbolType.DIGIT;
                numericStringBuilder.append(symbol);
            }
        }
        
        if (prevSymbolType != SymbolType.DIGIT) {
            return null;
        }
        
        return numericStringBuilder.toString();
    }
    
    private static long getLong(String str) {
        long number = 0;
        int length = str.length();
        
        for (int i = length - 1; i > 0; i--) {
            number += Character.digit(str.charAt(i), 10) * Math.pow(10, length - i - 1);
        }
        if (str.charAt(0) == '-') {
            number = -number;
        } else {
            number += Character.digit(str.charAt(0), 10) * Math.pow(10, length - 1);
        }
        return number;
    }
    
    private static double getDouble(String str) {
        if (str.contains("e")) {
            String[] partsOfNumber = str.split("e");
            double number = partsOfNumber[0].contains(".") ? numberWithDotParse(partsOfNumber[0]) : getLong(partsOfNumber[0]);
            return number * Math.pow(10, getLong(partsOfNumber[1]));
        }
        return numberWithDotParse(str);
    }
    
    private static double numberWithDotParse(String numberWithDot) {
        String[] partsOfNumber = numberWithDot.split("\\.");
        return getLong(partsOfNumber[0]) + getLong(partsOfNumber[1]) * Math.pow(10, -partsOfNumber[1].length());
    }
}

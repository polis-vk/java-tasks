package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.equals("")) {
            return null;
        }

        String digitString = "";
        int lenghtOfStr = str.length();
        char currentChar;
        boolean wasMinus = false;
        boolean wasE = false;
        boolean wasDot = false;
        boolean wasDigit = false;

        for (int i = 0; i < lenghtOfStr; i++) {
            currentChar = str.charAt(i);

            if ((currentChar == 'e' && wasE) || (currentChar == '.' && wasDot)){
                return null;
            }
            if (currentChar == 'e' && !wasDigit) {
                return null;
            }
            if (currentChar == '-' && (wasMinus || wasDigit)) {
                return null;
            }
            if (currentChar == '.' && (wasE || !wasDigit)) {
                return null;
            }


            if (currentChar == '-') {
                digitString = digitString + "-";
                wasMinus = true;
            }
            if (currentChar == '.') {
                digitString = digitString + ".";
                wasDot = true;
            }
            if (currentChar == 'e') {
                digitString = digitString + "e";
                wasE = true;
                wasMinus = false; // после числа Эйлера может быть еще один минус
                wasDigit = false;
            }
            if (Character.isDigit(currentChar)) {
                digitString = digitString + currentChar;
                wasDigit = true;
            }
        }

        char lastSymbol = digitString.charAt(digitString.length() - 1);
        if (lastSymbol == '-' || lastSymbol == 'e' || lastSymbol == '.') {
            return null;
        }

        int lengthOfDigitStr = digitString.length();
        char currentSymbol;
        int zeroChar = (int) '0';

        if (wasDot || wasE) {
            double digit = 0.0;
            wasE = false;
            wasMinus = false;
            wasDot = false;
            int powerOfE = 0;
            int positionDot = 0;

            for(int i = 0; i < lengthOfDigitStr; i++) {
                currentSymbol = digitString.charAt(i);

                if (currentSymbol == 'e') {
                    wasE = true;
                }
                if (wasE && currentSymbol == '-') {
                    wasMinus = true;
                }
                if (currentSymbol == '.') {
                    wasDot = true;
                    positionDot = i;
                }

                if (Character.isDigit(currentSymbol) && wasE) {
                    powerOfE = powerOfE * 10 + ((int) currentSymbol - zeroChar);
                } else if (Character.isDigit(currentSymbol) && wasDot && !wasE) {
                    digit += ((double) currentSymbol - zeroChar) * Math.pow(10, -(i - positionDot));
                } else if (Character.isDigit(currentSymbol)) {
                    digit = digit * 10 + ((int) currentSymbol - zeroChar);
                }

                if (wasE && wasMinus) {
                    powerOfE *= -1;
                }

            }

            if (digitString.charAt(0) == '-') {
                digit *= -1;
            }

            return digit * Math.pow(10, powerOfE);
        } else {
            long digit = 0;

            for (int i = 0; i < lengthOfDigitStr; i++) {
                currentSymbol = digitString.charAt(i);

                if (Character.isDigit(currentSymbol)){
                    digit = digit * 10 + ((int) currentSymbol - zeroChar);
                }
            }

            if (digitString.charAt(0) == '-') {
                digit *= -1;
            }

            if (digit >= Integer.MIN_VALUE && digit <= Integer.MAX_VALUE) {
                return (int) digit;
            }

            return digit;
        }
    }
}

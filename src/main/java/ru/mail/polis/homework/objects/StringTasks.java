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
        if (str == null || str.isEmpty()) {
            return null;
        }

        StringBuilder numberStrOneFiltered = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '-' || str.charAt(i) == '.' || str.charAt(i) == 'e' || Character.isDigit(str.charAt(i))) {
                numberStrOneFiltered.append(str.charAt(i));
            }
        }
        boolean firstMeetDot = true;
        boolean firstMeetE = true;

        StringBuilder numberStrTwoFiltered = new StringBuilder();
        for (int i = 0; i < numberStrOneFiltered.length(); i++) {
            if (numberStrOneFiltered.charAt(i) == '-') {
                if (i == 0 || numberStrOneFiltered.charAt(i - 1) == 'e') {
                    numberStrTwoFiltered.append(numberStrOneFiltered.charAt(i));
                } else {
                    return null;
                }
            }
            if (numberStrOneFiltered.charAt(i) == '.') {
                if (firstMeetDot) {
                    numberStrTwoFiltered.append(numberStrOneFiltered.charAt(i));
                    firstMeetDot = false;
                } else {
                    return null;
                }
            }
            if (numberStrOneFiltered.charAt(i) == 'e' && (i + 1) == numberStrOneFiltered.length()) {
                return null;
            }
//            if (numberStrOneFiltered.charAt(i) == 'e' && !firstMeetE) { firstMeetDot = false; }
            if (numberStrOneFiltered.charAt(i) == 'e' && (Character.isDigit(numberStrOneFiltered.charAt(i + 1)) || numberStrOneFiltered.charAt(i + 1) == '-')) {
                if (firstMeetE) {
                    numberStrTwoFiltered.append(numberStrOneFiltered.charAt(i));
                    firstMeetE = false;
                } else {
                    return null;
                }
            }
            if (Character.isDigit(numberStrOneFiltered.charAt(i))) {
                numberStrTwoFiltered.append(numberStrOneFiltered.charAt(i));
            }
        }
        // проверим, были ли '.' или 'e'
        firstMeetDot = !firstMeetDot;
        firstMeetE = !firstMeetE;
        long numForLong = 0; // не придумал как починить это
        double numForDouble = numForLong;
        boolean isDigitAfterDot = false;
        boolean isDigitAfterE = false;
        boolean needInverseNum = false;
        boolean needInversePow = false;
        boolean isItDouble = false;
        int countOfNumAfterDot = 0;
        double numAfterDot = 0;
        int countOfNumAfterE = 0;
        if (firstMeetDot || firstMeetE) {
            isItDouble = true;
            firstMeetDot = false;
            firstMeetE = false;
            if (numberStrTwoFiltered.charAt(0) == 'e') {
                countOfNumAfterE = 1;
            }
            if (numberStrTwoFiltered.charAt(0) == '-') {
                needInverseNum = true;
            }
            for (int i = 0; i < numberStrTwoFiltered.length(); i++) {
                if (numberStrTwoFiltered.charAt(i) == 'e' || firstMeetE) {
                    if (i != numberStrTwoFiltered.length() - 1 && numberStrTwoFiltered.charAt(i + 1) == '-') { //первое условие нужно?
                        needInversePow = true;
                    } else if (firstMeetE && numberStrTwoFiltered.charAt(i) != '-') {
                        countOfNumAfterE = Character.digit((numberStrTwoFiltered.charAt(i)), 10) + 10 * countOfNumAfterE;
                    }
                    isDigitAfterE = true;
                    firstMeetE = true;
                }
                if ((numberStrTwoFiltered.charAt(i) == '.' || firstMeetDot) && !firstMeetE) {
                    if (firstMeetDot) {
                        numAfterDot = Character.digit((numberStrTwoFiltered.charAt(i)), 10) + 10 * numAfterDot;
                        countOfNumAfterDot++;
                    }
                    isDigitAfterDot = true;
                    firstMeetDot = true;
                }
                if (Character.isDigit(numberStrTwoFiltered.charAt(i)) && !isDigitAfterDot && !isDigitAfterE) {
                    numForDouble = Character.digit((numberStrTwoFiltered.charAt(i)), 10) + 10 * numForDouble;
                }
            }
            if (needInverseNum) {
                numForDouble *= -1;
            }
            if (needInversePow) {
                countOfNumAfterE *= -1;
            }
            numForDouble = (numForDouble + numAfterDot * Math.pow(10, -countOfNumAfterDot)) * Math.pow(10, countOfNumAfterE);
        } else {
            int step = 0;
            if (numberStrTwoFiltered.charAt(0) == '-') {
                needInverseNum = true;
                step = 1;
            }
            for (int i = step; i < numberStrTwoFiltered.length(); i++) {
                numForLong = Character.digit((numberStrTwoFiltered.charAt(i)), 10) + 10 * numForLong;
            }
            if (needInverseNum) {
                numForLong *= -1;
            }
        }
        if (isItDouble) {
            return numForDouble;
        } else if (numForLong <= Integer.MAX_VALUE && numForLong >= Integer.MIN_VALUE) {
            return (int) numForLong;
        } else {
            return numForLong;
        }
    }
}

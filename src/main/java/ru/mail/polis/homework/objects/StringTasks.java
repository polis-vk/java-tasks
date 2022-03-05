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
        if (str == null || str.equals("")) {
            return null;
        }
        StringBuilder num = new StringBuilder();
        boolean chekPoint = false;
        boolean chekE = false;
        boolean chekMinus = false;
        boolean chekNum = false;
        for (int i = 0; i < str.length(); i++) {
            char buffChar = str.charAt(i);
            boolean chekLast = str.charAt(str.length() - 1) == buffChar;
            switch (buffChar) {
                case '.':
                    if (((chekPoint || !chekE) && !chekNum) || chekLast) {
                        return null;
                    }
                    num.append(buffChar);
                    chekPoint = true;
                    break;
                case 'e':
                    if (chekE || chekLast) {
                        return null;
                    }
                    num.append(buffChar);
                    chekE = true;
                    chekMinus = false;
                    chekNum = false;
                    break;
                case '-':
                    if (chekE) {
                        if (chekMinus || chekLast) {
                            return null;
                        }
                        num.append(buffChar);
                        chekMinus = true;
                        break;
                    }
                    if (chekMinus || chekNum) {
                        return null;
                    }
                    num.append(buffChar);
                    chekMinus = true;
                    break;
                default:
                    if (Character.isDigit(buffChar)) {
                        if (!chekE) {
                            chekNum = true;
                        }
                        num.append(buffChar);
                    }
                    break;
            }
        }
        int resultInt = 0;
        long resultLong = 0L;
        double resultDoubleAndFloat = 0.;
        String resultNumber = num.toString();
        chekMinus = resultNumber.contains("-");
        if (!resultNumber.contains(".") && !resultNumber.contains("e")) {
            resultNumber = resultNumber.replaceFirst("-", "");
            resultLong = castToLong(resultNumber);
            if (chekMinus) {
                resultLong *= -1;
            }
            if (resultLong >= Integer.MIN_VALUE && resultLong <= Integer.MAX_VALUE) {
                resultInt += resultLong;
                return resultInt;
            }
            return resultLong;
        }
        String[] splitStr = resultNumber.split("e");
        int degree = 0;
        if (resultNumber.split("e").length > 1) {
            degree = (int) castToLong(splitStr[1]);
            boolean chekMinusDegree = splitStr[1].contains("-");
            if (chekMinusDegree) {
                degree *= -1;
            }
        }
        String part1 = splitStr[0].split("\\.")[0];
        for (int i = 0; i < part1.length(); i++) {
            resultDoubleAndFloat += charToInt(part1.charAt(i)) * Math.pow(10, part1.length() - i - 1);
        }
        if (splitStr[0].split("\\.").length > 1) {
            String part2 = splitStr[0].split("\\.")[1];
            for (int i = 0; i < part2.length(); i++) {
                resultDoubleAndFloat += charToInt(part2.charAt(i)) / Math.pow(10, i + 1);
            }
        }
        resultDoubleAndFloat *= Math.pow(10, degree);
        if (splitStr[0].contains("-")) {
            resultDoubleAndFloat *= -1.;
        }
        return resultDoubleAndFloat;
    }

    public static long castToLong(String num) {
        long resultNumber = 0;
        for (int i = 0; i < num.length(); i++) {
            resultNumber += charToInt(num.charAt(i)) * Math.pow(10, num.length() - i - 1);
        }
        return resultNumber;
    }

    public static int charToInt(char num) {
        int numInt = 0;
        switch (num) {
            case '1':
                numInt = 1;
                break;
            case '2':
                numInt = 2;
                break;
            case '3':
                numInt = 3;
                break;
            case '4':
                numInt = 4;
                break;
            case '5':
                numInt = 5;
                break;
            case '6':
                numInt = 6;
                break;
            case '7':
                numInt = 7;
                break;
            case '8':
                numInt = 8;
                break;
            case '9':
                numInt = 9;
                break;
        }
        return numInt;
    }
}

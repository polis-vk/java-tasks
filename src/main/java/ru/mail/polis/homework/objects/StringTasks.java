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

        if (str == null || str.length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        int dotCount = 0;
        int minusCount = 0;
        int eCount = 0;

        for (char ch : str.toCharArray()) {
            if (ch == '.' || ch == '-' || ch == 'e' || Character.isDigit(ch)) {
                switch (ch) {
                    case '.':
                        dotCount++;
                        break;
                    case '-':
                        minusCount++;
                        break;
                    case 'e':
                        eCount++;
                        break;
                    default:
                        break;
                }

                sb.append(ch);
            }
        }

        String numString = sb.toString();

        if (dotCount > 1 || eCount > 1 || minusCount > 2 || numString.startsWith(".")
                || numString.startsWith("e") || numString.contains("--")
                || numString.contains("-e") || numString.endsWith("e")
                || numString.endsWith(".") || numString.contains(".e")
                || numString.contains("e.") || numString.endsWith("-")) {
            return null;
        }

        if (dotCount + eCount > 0) {
            return parseDouble(numString);
        }

        long longNumber = parseLong(numString);

        if (longNumber > Integer.MAX_VALUE || longNumber < Integer.MIN_VALUE) {
            return longNumber;
        }

        return (int) longNumber;
    }

    private static long parseLong(String numString) {
        return (long) parsePart(numString, 0);
    }

    private static double parseDouble(String numString) {
        String[] parts = numString.split("[.e]");
        double result = parsePart(parts[0], 0.0);

        if (numString.contains(".")) {
            int num;

            for (int i = 0; i < parts[1].length(); i++) {
                num = parseChar(parts[1].charAt(i));

                if (num >= 0) {
                    result += num * Math.pow(10, -i - 1);
                }
            }
        }

        if (numString.contains("e")) {
            result *= Math.pow(10, parsePart(parts[parts.length - 1], 0.0));
        }

        return result;
    }

    public static double parsePart(String part, double result) {
        boolean minusFlag = false;
        int num;

        for (int i = 0; i < part.length(); i++) {
            num = parseChar(part.charAt(i));

            if (num >= 0) {
                result += num * Math.pow(10, part.length() - i - 1);
            } else {
                minusFlag = !minusFlag;
            }
        }

        if (minusFlag) {
            result *= -1;
        }
        return result;
    }

    private static int parseChar(char ch) {
        switch (ch) {
            case '-':
                return -1;
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                throw new RuntimeException();
        }
    }
}

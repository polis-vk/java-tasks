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
        StringBuilder resultStringBuilder = new StringBuilder();
        int dotCount = 0;
        int minusCount = 0;
        int eCount = 0;
        for (char temp : str.toCharArray()) {
            if (Character.isDigit(temp)) {
                resultStringBuilder.append(temp);
            } else if (temp == '.') {
                dotCount++;
                if (dotCount > 1) {
                    return null;
                }
                resultStringBuilder.append(temp);
            } else if (temp == 'e') {
                eCount++;
                if (eCount > 1) {
                    return null;
                }
                resultStringBuilder.append(temp);
            } else if (temp == '-') {
                minusCount++;
                if (minusCount > 2) {
                    return null;
                }
                resultStringBuilder.append(temp);
            }
        }
        String resultString = resultStringBuilder.toString();
        char[] resultStringChars = resultString.toCharArray();
        if (minusCount == 2) {
            if (!(resultString.startsWith("-")
                    && (resultString.lastIndexOf('-') - resultString.indexOf('e') == 1))) {
                return null;
            }
        }
        if (minusCount == 1) {
            if (!((resultString.indexOf('-') - resultString.indexOf('e') == 1)
                    || resultString.startsWith("-"))) {
                return null;
            }
        }
        if (eCount == 1) {
            if (resultString.startsWith("e") || resultString.endsWith("e")) {
                return null;
            }
            if(!(Character.isDigit(resultStringChars[resultString.indexOf('e') - 1])
                        && (Character.isDigit(resultStringChars[resultString.indexOf('e') + 1])
                            || resultStringChars[resultString.indexOf('e') + 1] == '-'))) {
                    return null;
            }
        }
        if (dotCount == 1) {
            if (resultString.startsWith(".") || resultString.endsWith(".")) {
                return null;
            }
            if (!(Character.isDigit(resultStringChars[resultString.indexOf('.') - 1])
                    && Character.isDigit(resultStringChars[resultString.indexOf('.') + 1]))) {
                return null;
            }
        }
        if (eCount == 1) {
            String mantissaString = resultString.split("e")[0];
            char[] mantissaStringChars = mantissaString.toCharArray();
            double mantissa = 0;
            String exponentString = resultString.split("e")[1];
            char[] exponentStringChars = exponentString.toCharArray();
            long exponent = 0;
            if (mantissaString.contains(".")) {
                if (mantissaString.startsWith("-")) {
                    for (int i = 1; i < mantissaString.indexOf('.'); ++i) {
                            mantissa -= (mantissaStringChars[i] - 48)
                                    * Math.pow(10, mantissaString.indexOf('.') - i - 1);
                    }
                    for (int i = mantissaString.indexOf('.') + 1; i < mantissaStringChars.length; ++i) {
                        mantissa -= (mantissaStringChars[i] - 48)
                                * Math.pow(10, mantissaString.indexOf('.') - i);
                    }
                } else {
                    for (int i = 0; i < mantissaString.indexOf('.'); ++i) {
                        mantissa += (mantissaStringChars[i] - 48)
                                * Math.pow(10, mantissaString.indexOf('.') - i - 1);
                    }
                    for (int i = mantissaString.indexOf('.') + 1; i < mantissaStringChars.length; ++i) {
                        mantissa += (mantissaStringChars[i] - 48)
                                * Math.pow(10, mantissaString.indexOf('.') - i);
                    }
                }
            } else {
                if (mantissaString.startsWith("-")) {
                    for (int i = 1; i < mantissaStringChars.length; ++i) {
                        mantissa -= (mantissaStringChars[i] - 48)
                                * Math.pow(10, mantissaStringChars.length - i - 1);
                    }
                } else {
                    for (int i = 0; i < mantissaStringChars.length; ++i) {
                        mantissa += (mantissaStringChars[i] - 48)
                                * Math.pow(10, mantissaStringChars.length - i - 1);
                    }
                }
            }
            if (exponentString.startsWith("-")) {
                for (int i = 1; i < exponentStringChars.length; ++i) {
                    exponent -= (exponentStringChars[i] - 48)
                            * Math.pow(10, exponentStringChars.length - i - 1);
                }
            } else {
                for (int i = 0; i < exponentStringChars.length; ++i) {
                    exponent += (exponentStringChars[i] - 48)
                            * Math.pow(10, exponentStringChars.length - i - 1);
                }
            }
            return mantissa * Math.pow(10, exponent);
        } else {
            if (resultString.contains(".")) {
                double result = 0;
                if (resultString.startsWith("-")) {
                    for (int i = 1; i < resultString.indexOf('.'); ++i) {
                        result -= (resultStringChars[i] - 48)
                                * Math.pow(10, resultString.indexOf('.') - i - 1);
                    }
                    for (int i = resultString.indexOf('.') + 1; i < resultStringChars.length; ++i) {
                        result -= (resultStringChars[i] - 48)
                                * Math.pow(10, resultString.indexOf('.') - i);
                    }
                } else {
                    for (int i = 0; i < resultString.indexOf('.'); ++i) {
                        result += (resultStringChars[i] - 48)
                                * Math.pow(10, resultString.indexOf('.') - i - 1);
                    }
                    for (int i = resultString.indexOf('.') + 1; i < resultStringChars.length; ++i) {
                        result += (resultStringChars[i] - 48)
                                * Math.pow(10, resultString.indexOf('.') - i);
                    }
                }
                return result;
            } else {
                long result = 0;
                if (resultString.startsWith("-")) {
                    for (int i = 1; i < resultStringChars.length; ++i) {
                        result -= (resultStringChars[i] - 48)
                                * Math.pow(10, resultStringChars.length - i - 1);
                    }
                } else {
                    for (int i = 0; i < resultStringChars.length; ++i) {
                        result += (resultStringChars[i] - 48)
                                * Math.pow(10, resultStringChars.length - i - 1);
                    }
                }
                if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
                    return (int)result;
                }
                return result;
            }
        }
    }
}

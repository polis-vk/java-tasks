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
        if (str == null || str.equals(""))
            return null;
        StringBuilder requiredSymbolsStr = new StringBuilder();
        char currentSymbol;
        for (int i = 0; i < str.length(); i++) {
            currentSymbol = str.charAt(i);
            if (Character.isDigit(currentSymbol)) {
                requiredSymbolsStr.append(currentSymbol);
            }
            if (currentSymbol == '.' || currentSymbol == '-' || currentSymbol == 'e')
                requiredSymbolsStr.append(currentSymbol);
        }

        // Checking position of e
        int eNumber = 0;
        boolean eWas = false;
        int quantityOfNumberAfterEAfterE = 0;
        int positionE = 0;
        for (int i = 0; i < requiredSymbolsStr.length(); i++) {
            currentSymbol = requiredSymbolsStr.charAt(i);
            if (currentSymbol == 'e') {
                eWas = true;
                positionE = i;
                eNumber++;
                continue;
            }
            if (eNumber > 1)
                break;
            if (eWas && currentSymbol != '-')
                quantityOfNumberAfterEAfterE++;
        }
        if (eNumber > 1 || requiredSymbolsStr.charAt(requiredSymbolsStr.length() - 1) == 'e')
            return null;
        if (!eWas)
            positionE = requiredSymbolsStr.length();
        // Checking position of minuses
        boolean numberWas = false;
        boolean isItNumber = true;
        int minusNumberBeforeE = 0;
        int minusNumberAfterE = 0;
        for (int i = 0; i < requiredSymbolsStr.length(); i++) {
            currentSymbol = requiredSymbolsStr.charAt(i);
            if (i < positionE) {
                if (Character.isDigit(currentSymbol))
                    numberWas = true;
                if (currentSymbol == '-' && numberWas) {
                    isItNumber = false;
                    break;
                }
                if (currentSymbol == '-')
                    minusNumberBeforeE++;
                if (minusNumberBeforeE >= 2) {
                    isItNumber = false;
                    break;
                }
            }
            if (i > positionE){
                if (currentSymbol == '-')
                    minusNumberAfterE++;
                if (currentSymbol == '-' && i != positionE + 1) {
                    isItNumber = false;
                    break;
                }
                if (minusNumberAfterE >= 2){
                    isItNumber = false;
                    break;
                }
            }
        }
        if (!isItNumber)
            return null;

        // Checking position of dots
        int digitNumberBeforeDot = 0;
        numberWas = false;
        int dotsNumber = 0;
        for (int i = 0; i < requiredSymbolsStr.length(); i++) {
            currentSymbol = requiredSymbolsStr.charAt(i);
            if (dotsNumber == 0 && Character.isDigit(currentSymbol)) {
                digitNumberBeforeDot++;
                numberWas = true;
            }
            if (currentSymbol == '.' && !numberWas) {
                isItNumber = false;
                break;
            }
            if (currentSymbol == '.')
                dotsNumber ++;
            if (dotsNumber >= 2){
                isItNumber = false;
                break;
            }
        }
        if (!isItNumber)
            return null;

        int numberAfterE = 0;
        int pow = quantityOfNumberAfterEAfterE - 1;
        boolean minusAfterEWas = false;
        if (eWas){
            for (int i = positionE + 1; i < requiredSymbolsStr.length(); i++) {
                currentSymbol = requiredSymbolsStr.charAt(i);
                if (currentSymbol == '-') {
                    minusAfterEWas = true;
                    continue;
                }
                numberAfterE += (currentSymbol - 48) * Math.pow(10, pow);
                pow --;
            }
        }
        if (minusAfterEWas)
            numberAfterE *=(-1);

        // Transform string to number
        long answer = 0;
        boolean dotWas = false;
        pow = digitNumberBeforeDot - 1;
        eWas = false;
        double fractionalAnswerPart = 0;
        int currentNumber;
        for (int i = 0; i < requiredSymbolsStr.length(); i++) {
            currentSymbol = requiredSymbolsStr.charAt(i);
            if (currentSymbol == '-')
                continue;
            if (currentSymbol == '.') {
                dotWas = true;
                pow = -1;
                continue;
            }
            if (currentSymbol == 'e') {
                eWas = true;
                pow = digitNumberBeforeDot - 1 - quantityOfNumberAfterEAfterE;
                continue;
            }

            currentNumber = currentSymbol - 48;
            if (eWas){
                break;
            }
            if (dotWas) {
                fractionalAnswerPart += currentNumber * Math.pow(10, pow);
                pow--;
            }
            if (!dotWas) {
                answer += currentNumber * Math.pow(10, pow);
                pow--;
            }
        }
        if (eWas){
            if (minusNumberBeforeE == 1)
                return ((double) answer + fractionalAnswerPart) * (-1) * Math.pow(10, numberAfterE);
            else
                return ((double) answer + fractionalAnswerPart)* Math.pow(10, numberAfterE);
        }
        if (minusNumberBeforeE == 1) {
            answer *= (-1);
            fractionalAnswerPart *= (-1);
        }
        if (Math.abs(fractionalAnswerPart) > 0.000001 || numberAfterE != 0)
            return ((double) answer + fractionalAnswerPart);
        if (answer <= Integer.MAX_VALUE && answer >= Integer.MIN_VALUE)
            return (int) answer;
        return answer;
    }
}

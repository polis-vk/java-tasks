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
        /* Я пробовал сделать это задание в более приемлемом виде, но это единственный получившийся рабочий вариант.
         *  Я не рассчитываю получить баллы за этот код, но хотел бы узнать более корректное и красивое решение данной задачи
         */
        if ((str == null) || (str.length() == 0)) {
            return null;
        }
        Character symbol = null;
        long num = 0;                   //Мантисса числа
        int order = 0;                  //Ордината числа
        int digitsAfterPoint = 0;       //Кол-во цифр после запятой (чтобы уменьшить порядок при выводе числа)
        boolean e = false;              //Условие, встречался ли в строке символ 'e'
        boolean isNumNegative = false;
        boolean isOrderNegative = false;
        boolean point = false;          //Условие, встречался ли в строке символ '.'
        for (int i = 0; i < str.length(); i++) {
            if (symbol.isDigit(str.charAt(i))) {
                if (e) {
                    order = order * 10 + (str.charAt(i) - '0');
                } else {
                    if (point) digitsAfterPoint++;
                    num = num * 10 + (str.charAt(i) - '0');
                }
            }
            switch (str.charAt(i)) {
                case '-':
                    if (e) {
                        if (!isOrderNegative && order == 0) {
                            isOrderNegative = true;
                        } else {
                            return null;
                        }
                    } else {
                        if (!isNumNegative && num == 0) {
                            isNumNegative = true;
                        } else {
                            return null;
                        }
                    }
                    break;
                case '.':
                    if (!(e || point)) {
                        point = true;
                    } else {
                        return null;
                    }
                    break;
                case 'e':
                    if (e) {
                        return null;
                    }
                    e = true;
                    break;
            }
        }
        if (isNumNegative) {
            num *= -1;
        }
        if (isOrderNegative) {
            order *= -1;
        }
        if (e || point) {

            if (order == 0) {
                return num / (Math.pow(10, digitsAfterPoint));
            }

            int pow = 1; //Я не использую Math.pow(), потому что результатом в одном из тестов будет 0.00120000001, а не 0.0012
            for (int i = 0; i < Math.abs(order - digitsAfterPoint); i++) {
                pow *= 10;
            }
            if (order - digitsAfterPoint >= 0) {    // С Math.pow() я бы мог просто умножить число на степень, но тогда возникнет погрещность и не пройдут тесты
                return (double) num * pow;          //return num*Math.pow(10,order-digitsAfterPoint)
            } else {
                return (double) num / pow;
            }

        } else {
            //При вывохде корректного числа, но типа данных Long тест не проходитЮ поэтому стоит проверка, переводящая его в int
            if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                return num;
            }
            return (int) num;
        }
    }
}

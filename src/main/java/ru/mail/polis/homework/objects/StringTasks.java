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
        if (str==null) {//Если строка равна null
            return null;
        }
        if ((str.length()==0)) {//Если строка пустая
            return null;
        }
        if ((str.indexOf('e')!=str.lastIndexOf('e'))||(str.indexOf('.')!=str.lastIndexOf('.'))) {//Если в строке 2 точки или 2 e
            return null;
        }
        StringBuilder answer = new StringBuilder("");
        for (int i=0; i<str.length(); i++) { //Формируем строку с допустимыми символами
            if (Character.isDigit(str.charAt(i))||str.charAt(i)=='-'||str.charAt(i)=='.'||str.charAt(i)=='e') {
                answer.append(str.charAt(i));
            }
        }
        for (int i=1; i<answer.length(); i++) {
            if ((answer.charAt(i-1)=='-')&&(answer.charAt(i)=='e')) {
                return null;
            }
        }
        if (answer.toString().lastIndexOf('-')==answer.toString().length()-1) {//Если последний символ -
            return null;
        }
        if ((answer.toString().indexOf('-')-answer.toString().lastIndexOf('-'))==-1) {//Если подряд идут 2 -
            return null;
        }
        if ((answer.toString().indexOf('e')!=-1)||(answer.toString().indexOf('.')!=-1)) {
            return Double.parseDouble(answer.toString());
        }
        if (answer.toString().indexOf('-')!=answer.toString().lastIndexOf('-')) {//Если в строке 2 минуса и число не double
            return null;
        }
        if ((Long.parseLong(answer.toString())>2147483647)||(Long.parseLong(answer.toString())<-2147483648)) {
            return Long.parseLong(answer.toString());
        }
        return Integer.parseInt(answer.toString());
    }
}

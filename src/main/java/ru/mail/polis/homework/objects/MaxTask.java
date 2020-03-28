package ru.mail.polis.homework.objects;

public class MaxTask {

    /**
     * Вам дан массив и количество элементов в возвращаемом массиве
     * Вернуть нужно массив из count максимальных элементов array, упорядоченный по убыванию.
     * Если длина массива меньше count, то вернуть null
     * Например ({1, 3, 10, 11, 22, 0}, 2) -> {22, 11}
     * ({1, 3, 22, 11, 22, 0}, 3) -> {22, 22, 11}
     * НЕЛЬЗЯ СОРТИРОВАТЬ массив array и его копиии
     */
    public static int[] getMaxArray(int[] array, int count) {

        /*
        Вы писали: "в худшем случае (обратный порядок) у вас n раз будет цикл от 1 до n. Итого n^2 сложность, тоже самое что самая простая сортировка"
        не соглашусь, тут k раз будет цикл от 1 до n, и один раз от 1 до count. Итого n * k + count сложность.
        (k равно количеству уникальных чисел в массиве result)
        где k <= n
        k уменьшается если:
        1) в массиве array есть повторяющиеся числа
        2) count < array.length
        Например:
        при array = new int[] {0,1,2,3,4,4}; count = 3;
        n * k + count = 6 * 2 + 3 = 15, а не 36, из за повтора и малого count
        Наихудший вариант - нет повторов и count = array.length;
        в этом случае сортировка даже эффективнее, но ее, согласно заданию, нельзя использовать...
        */

        if (array.length < count) {
            return null;
        }
        int[] result = new int[count];
        if (count == 0) {
            return result;
        }
        int newMaxValue;
        int oldMaxValue = Integer.MAX_VALUE;
        boolean loop = true;
        int index = 0;

        while (loop) {//повторяется k раз
            int counter = 0;
            newMaxValue = -Integer.MAX_VALUE;
            for (int i = 0; i < array.length; i++) {                        //повторяется n раз
                if (array[i] > newMaxValue && array[i] < oldMaxValue) {     //& промаргал, да
                    newMaxValue = array[i];
                    counter = 1;
                    continue;                                               //обрезал лишнии проверки
                }
                if (array[i] == newMaxValue) {
                    counter++;
                }
            }
            /*оч сложно без каких-то пояснений*/
            for (int j = 0; j < counter; j++) {     //повторяем пока не запишим все повторы, повторяется не более чем count раз
                result[index] = newMaxValue;        //записываем максимальное число в масив result
                index++;
                if (index >= result.length) {       //если масив result заполнен
                    loop = false;                   //прерываем главный цикл while
                    break;                          //прерываем текущий цикл for
                }
            }
            oldMaxValue = newMaxValue;              //запоминаем предыдущее максимальное число
        }
        return result;
    }
}

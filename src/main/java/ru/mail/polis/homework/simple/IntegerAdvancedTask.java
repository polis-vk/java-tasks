package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if(q==1)
            return a*n;
        else
        return (long) (a*(Math.pow(q,n)-1)/(q-1));
    }

    /**
     * Гусеница ползает по столу квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до поля с травой?
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 5, 5, 20, 11) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if(up>=grassY || right>=grassX || grassX<=0 ||grassY<=0)
            return 1;
        else if(up>down||right>left){
            int coordX=0;
            int coordY=0;
            int count=0;
           while (coordX<=grassX && coordY<=grassY){
               count++;
               coordX+=right;
               coordY+=up;
               if(coordX>=grassX || coordY>=grassY)
                   return count;
               coordX-=left;
               coordY-=down;
           }
           return count;
        }else
              return Integer.MAX_VALUE;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        for (int i = 0; i < order-1; i++) {
            n/=16;
        }

            int rez=Math.abs(n%16);
            if(rez>9)
                return (char) ('A'+(rez-10));
            else
                return (char)(rez+'0');
        }



    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte i=0,j=0;
        int min=16;
        while (a>0){
            i++;
            if(a%16<min) {
                min = (int) (a % 16);
                j=i;
            }
        a/=16;
        }
        return j;
    }

}

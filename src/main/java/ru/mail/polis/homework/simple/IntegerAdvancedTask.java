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
    	//сравниваю квадрат отклонения с 10^-10, если меньше то q ~ 1
    	if((1 - q) * (1 - q) <= 1e-10)
    		return a * n;
    	return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
    }

    /**
     * Гусеница ползает по столу квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до поля с травой?
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 5, 5, 20, 1) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
    	if(up >= grassY || right >= grassX) {
        	return 1; // доползет за полдня, формально за первый день
        }

		return Math.min(calcTime(right - left, grassX - right), calcTime(up - down, grassY - up));
	}
	
	private static int calcTime(final int step, final int border) {
		if(step <= 0) {
			return Integer.MAX_VALUE; // не доползет
		}
		return (border / step) + (border % step > 0 ? 2 : 1);
	}

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
    	// а, ой...
    	// String s = Integer.toHexString(n);
        // return s.toUpperCase().charAt(s.length() - order);
    	int num = (n >> 4 * (order - 1)) % 16;
    	// Ну раз нельзя String и методы Integer, то
    	if(num < 10) {
    		return (char) ('0' + num);
    	}
    	return (char) ('A' + num - 10);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
    	int size = (int) Math.round(Math.log(a) / Math.log(16) + 0.5);
    	int min = 16;
    	int k = 0;
    	for(int i = 0; i < size; ++i) {
    		int num = (int) ((a >> 4 * i) % 16);
    		if(num < min) {
    			k = i + 1;
    			min = num;
    		}
    	}
        return (byte)k;
    }
    

}

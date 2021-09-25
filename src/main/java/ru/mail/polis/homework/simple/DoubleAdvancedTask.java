package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
    	double[] x = new double[] {0,0,0};
    	// Тригонометрическая формула Виета, v1, v2 и v3 - коэффициенты x ^ 3 + v1 * x ^ 2 + v2 * x + v3 = 0
    	final double 
    		v1 = (double)b / a, // частный случай что a = 0 не учитываем, т.к. условие задачи
    		v2 = (double)c / a, // явно предполагает три корня, а у квадратного уравнения их 2,
    		v3 = (double)d / a, // по основной теореме алгербы, и третий корень определить нельзя
    		Q = (double)(v1*v1 - 3*v2)/9,
    		R = (double)(2*v1*v1*v1 - 9*v1*v2 + 27*v3)/54,
    		a1 = v1/3; // в формуле часто фигурирует v1/3

    	System.out.println(Q);
    	System.out.println(R);
    	if(Q*Q*Q > R*R) {
    		final double angle = Math.acos(R/Math.pow(Q, 1.5)) / 3;
    		final double sqrt = Math.sqrt(Q);
    		x[0] = -2 * sqrt * Math.cos(angle) - a1;
    		x[1] = -2 * sqrt * Math.cos(angle + 2.0 * Math.PI/3.0) - a1;
    		x[2] = -2 * sqrt * Math.cos(angle - 2.0 * Math.PI/3.0) - a1;
    		
    		//3 элемента - еще не повод сортировать массив
    		if (x[0] < x[1]) { 
    			double tmp = x[0]; x[0] = x[1]; x[1] = tmp;
    		}
    		if (x[0] < x[2]) { 
    			double tmp = x[0]; x[0] = x[2]; x[2] = tmp;
    		}
    		if (x[1] < x[2]) { 
    			double tmp = x[1]; x[1] = x[2]; x[2] = tmp;
    		}

    	}
    	else {// по условию корни вещественные
    		double cbrt = Math.cbrt(R);
    		x[0] = -2 * cbrt - a1;
    		x[1] = cbrt - a1;
    		if(x[1] > x[0]) {
    			x[2] = x[0];
    			x[0] = x[1];
    		}
    	}
    		

        return x[0] + ", " + x[1] + ", " + x[2];
    }


    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
    	// y = a1 * x + b1 и y = a2 * x + b1; 
    	if(a1 != a2) {// расстояние не равно 0 только если угловые коэффициенты равны
    		return 0;
    	}
    	else {
    		return (float) (Math.abs(b1-b2)/Math.sqrt(1 + a1*a2));
    	}
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
    	// исходя из уравнения плоскости по трем точкам, приводим коэфициенты для упрощения расчетов
    	x2 -= x1;
    	x3 -= x1;
    	x4 -= x1;
    	y2 -= y1;
    	y3 -= y1;
    	y4 -= y1;
    	z2 -= z1;
    	z3 -= z1;
    	// подставляем приведенные x4 и y4 в уравнение, выражаем приведенное z4 и прибавляем z1 т.к. нужно неприведенное значение
        return (float)(z3*(x2*y4 - x4*y2) + z2*(x4*y3-x3*y4))/(x2*y3 - x3*y2) + z1;
    }
}

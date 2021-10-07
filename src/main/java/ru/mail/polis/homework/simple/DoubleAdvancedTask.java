package ru.mail.polis.homework.simple;
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
    private static double f(double x,double a, double b, double c, double d){
        return a*x*x*x+b*x*x+c*x+d;
    }
    private static double diskr(double a,double b,double c){
        return Math.sqrt(b*b-4*a*c);
    }
    public static String equation(int a, int b, int c, int d) {//тест не проходит из-за точности
        //так как предполагается, что все 3 корня есть, проверка на равенство нуля коэф. а не нужна.
        double x1;
        double x2;
        double x3;//посчитаем производную - ее корни это экстремумы(т. к. по усл. все корни вещественные)
        double xDer1=(-2.*b+diskr(3.*a,2.*b,c))/(6.*a);
        double xDer2=(-2.*b-diskr(3.*a,2.*b,c))/(6*a);
        if(Math.abs(xDer1-xDer2)<1e-8)//экстремумы совпадают => это корень
        {
            x1=x2=x3=xDer1;
        }else{
        //на промежутке [xDer2, xDer1] есть ровно один корень, ищем его методом половинного деления
        double med=(xDer1+xDer2)/2.;
        while (Math.abs(f(med,a,b,c,d))>Double.MIN_VALUE){
            med=(xDer1+xDer2)/2.;
            if(Math.abs(f((med+xDer1)/2.,a,b,c,d))<Math.abs(f((med+xDer2)/2.,a,b,c,d))){
                xDer2=med;

            }else
                xDer1=med;
        }
        x1=med;
        //Так как мы знаем один корень x_0, мы можем разделить уравнение на (x-x_0) и получить квадратное ур-ние
       //Получим уравнение a*x^2+(b+x_0)*x+(c+x_0^2+b*x_0)=0
            double c2 = c + x1 * x1 + b * x1;
            x2=(-(b+x1)+diskr(a,(b+x1), c2))/(2*a);
        x3=(-(b+x1)-diskr(a,(b+x1), c2))/(2*a);

        }
        final double max = Math.max(Math.max(x1, x2), x3);
        final double min = Math.min(Math.min(x1, x2), x3);
        return  max + ", " +(x1+x2+x3 - max - min)  + ", " + min;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        //y=ax+b => ax+by+c=0
        if(a1!=a2)
            return 0;
        return (float) ((Math.abs(b2-b1))/(Math.sqrt(a1*a1+1)));
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
        return  z1+
              ( (double)(y1-y2)*(z3-z1)*(x4-x1)+(x1-x3)*(y4-y1)*(z2-z1)+
                (x4-x1)*(y3-y1)*(z2-z1)+(y4-y1)*(z3-z1)*(x2-x1))/((x2-x1)*(y3-y1)-(y2-y1)*(x3-x1));

    }
}

package ru.mail.polis.homework.exception;

import java.io.IOException;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */

public class Robot {
    private int x,y, robotID;
    Robot(int start_x, int start_y,int robotID)
    {
        this.x = start_x;
        this.y = start_y;
        this.robotID = robotID;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setX(int new_x)
    {
        this.x = new_x;
    }
    public void setY(int new_y)
    {
        this.y = new_y;
    }
   public int getID()
   {
       return robotID;
   }
}

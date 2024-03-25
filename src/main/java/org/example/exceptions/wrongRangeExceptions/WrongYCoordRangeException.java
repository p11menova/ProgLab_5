package org.example.exceptions.wrongRangeExceptions;

import org.example.models.Coordinates;
/**
 * Исключение неверного диапазона у-координаты. Выбрасывается при попытке задать у-координате значение, больше максимально допустимого.
 */
public class WrongYCoordRangeException extends Exception{
    public WrongYCoordRangeException(){
        super("значение поля y не должно превышать " + Coordinates.yMax);
    }
}

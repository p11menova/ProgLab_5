package org.example.exceptions;

import org.example.models.TicketType;
import org.example.utility.Console;
/**
 * Исключение отсутствия типа билета. Выбрасывается при попытке обращения к несуществующему типу билета.
 */
public class NoSuchTicketTypeException extends Exception{
    public NoSuchTicketTypeException(){
        super("нет такого типа билета(\nвозможные варианты:");

    }
}

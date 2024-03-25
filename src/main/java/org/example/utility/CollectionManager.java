package org.example.utility;

import org.example.models.Person;
import org.example.models.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс управления коллекцией
 */
public class CollectionManager {
    /**
     * Хранимая коллекция
     */
    private static final Hashtable<Integer, Ticket> TicketsCollection = new Hashtable<>();
    /**
     * Дата инициализации коллекции
     */
    private static LocalDateTime InitilizationDate = LocalDateTime.now();

    /**
     * Возвращает элемент коллекции с заданным айди
     * @param id id элемента коллекции
     * @return элемент коллекции с заданным айди
     */
    public Ticket getById(int id){
        return TicketsCollection.get(id);
    }

    /**
     * Добавляет элемент в коллекцию
     * @param ticket элемент добавляемый в коллекцию
     */
    public void addToCollection(Ticket ticket){
        TicketsCollection.put(ticket.get_id(), ticket);

    }

    /**
     * Заполняет коллекцию данными из файла
     * @param fileParsingManager менеджер работы с файлом
     */
    public void loadCollectionFromFile(FileManager fileParsingManager){
        fileParsingManager.addDataToCollection(this);
        InitilizationDate = LocalDateTime.now();
    }

    /**
     * Возвращает размер коллекции
     * @return размер коллекции
     */
    public static int getCollectionLength(){
        return TicketsCollection.size();
    }

    /**
     * Возвращает дату инициализации коллекции
     * @return дата инициализации коллекции в формате yy-MM-dd hh:mm:ss
     */
    public static String getCreationDate(){
        return DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss").format(InitilizationDate);
    }

    /**
     * Возвращает информацию о коллекции: хранимый тип, кол-во элементов, дата инициализации
     * @return информация о коллекции
     */
    public static String getInfo() {
        return "информация о коллекции:\n-хранимый тип:" + Ticket.class.toString() +
                "\n-количество элементов:"+CollectionManager.getCollectionLength()
                +"\n-дата инициализации:"+CollectionManager.getCreationDate();
    }

    /**
     * Проверяет, занят ли заданный айди
     * @param i id для проверки
     * @return true, если данный id занят, иначе else
     */
    public boolean isIdTaken(int i){
        return !(TicketsCollection.get(i) == null);
    }

    /**
     * Удаляет элемент коллекции по данному id
     * @param id id элемент с которым должен быть удален
     */
    public void removeWithId(int id){
        TicketsCollection.remove(id);
    }

    /**
     * Очистить коллекцию
     */
    public void clear(){
        TicketsCollection.clear();
    }

    /**
     * Возвращает коллекцию
     * @return хранимая коллекция TicketsCollection
     */
    public Hashtable<Integer, Ticket> getTicketsCollection() {
        return TicketsCollection;
    }

    @Override
    public String toString() {
        if (getCollectionLength() == 0) return "коллекция еще пуста(";
        StringBuilder info = new StringBuilder();
        info.append("TicketsCollection: [ \n");
        Hashtable<Integer, Ticket> ht = TicketsCollection;
        ht.forEach((key,value) -> info.append(key).append(" -> ").append(ht.get(key).toString()).append("\n\n"));
        ;
        return info.toString().trim()+"\n]";
    }
}

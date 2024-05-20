package org.server.utility.managers;

import org.example.models.DBModels.TicketWithMetadata;
import org.example.models.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

public class CollectionManager {
    // Для синхронизации доступа к коллекции использовать синхронизацию чтения и записи с помощью synchronized
    // !! HASHTABLE IS ALREADY SYNCHRONIZED,  SO ONLY ONE THREAD CAN ACCESS OR MODIFY IT AT A TIME !!
    /**
     * Хранимая коллекция
     */
    private static final Hashtable<Integer, TicketWithMetadata> TicketsCollection = new Hashtable<>();
    /**
     * Дата инициализации коллекции
     */
    private static LocalDateTime InitilizationDate = LocalDateTime.now();

    /**
     * Возвращает элемент коллекции с заданным айди
     *
     * @param id id элемента коллекции
     * @return элемент коллекции с заданным айди
     */
    public TicketWithMetadata getById(int id) {
        return TicketsCollection.get(id);
    }

    /**
     * Добавляет элемент в коллекцию
     *
     * @param ticket элемент добавляемый в коллекцию
     */
    public  boolean addToCollection(TicketWithMetadata ticket) {
        System.out.println(ticket.getTicket().get_id() +" "+isIdTaken(ticket.getTicket().get_id()));
        if (!isIdTaken(ticket.getTicket().get_id())) {
            TicketsCollection.put(ticket.getTicket().get_id(), ticket);
            return true;
        }
        return false;
    }
    public void updateElem(TicketWithMetadata ticket){
        getTicketsCollection().put(ticket.getTicket().get_id(), ticket);

    }

    /**
     * Заполняет коллекцию данными из файла
     *
     * @param fileParsingManager менеджер работы с файлом
     */
//    public void loadCollectionFromFile(FileManager fileParsingManager) {
//        fileParsingManager.addDataToCollection(this);
//        InitilizationDate = LocalDateTime.now();
//    }

    /**
     * Возвращает размер коллекции
     *
     * @return размер коллекции
     */
    public static int getCollectionLength() {
        return TicketsCollection.size();
    }

    /**
     * Возвращает дату инициализации коллекции
     *
     * @return дата инициализации коллекции в формате yy-MM-dd hh:mm:ss
     */
    public static String getCreationDate() {
        return DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss").format(InitilizationDate);
    }

    /**
     * Возвращает информацию о коллекции: хранимый тип, кол-во элементов, дата инициализации
     *
     * @return информация о коллекции
     */
    public static String getInfo() {
        return "информация о коллекции:\n-хранимый тип:" + Ticket.class.toString() +
                "\n-количество элементов:" + CollectionManager.getCollectionLength()
                + "\n-дата инициализации:" + CollectionManager.getCreationDate();
    }

    /**
     * Проверяет, занят ли заданный айди
     *
     * @param i id для проверки
     * @return true, если данный id занят, иначе else
     */
    public boolean isIdTaken(int i) {
        return !(TicketsCollection.get(i) == null);
    }

    /**
     * Удаляет элемент коллекции по данному id
     *
     * @param id id элемент с которым должен быть удален
     */
    public void removeWithId(int id) {
        TicketsCollection.remove(id);
    }

    /**
     * Очистить коллекцию
     */
    public void clear() {
        TicketsCollection.clear();
    }

    /**
     * Возвращает коллекцию
     *
     * @return хранимая коллекция TicketsCollection
     */
    public synchronized Hashtable<Integer, TicketWithMetadata> getTicketsCollection() {
        return TicketsCollection;
    }

    @Override
    public String toString() {
        if (getCollectionLength() == 0) return "коллекция еще пуста(";
        StringBuilder info = new StringBuilder();
        info.append("TicketsCollection: [ \n");
        Hashtable<Integer, TicketWithMetadata> ht = getTicketsCollection();
        ht.forEach((key, value) -> info.append(key).append(" -> ").append(ht.get(key).toString()).append("\n\n"));
        ;
        return info.toString().trim() + "\n]";
    }
}

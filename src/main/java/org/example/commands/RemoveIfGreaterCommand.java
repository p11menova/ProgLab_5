package org.example.commands;

import org.example.exceptions.NoSuchElementException;
import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.models.Ticket;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

import java.util.Hashtable;
import java.util.Map;

/**
 * Команда удаления экземпляров коллекции, превышающих заданный.
 */
public class RemoveIfGreaterCommand extends Command{
    private CollectionManager collectionManager;
    public RemoveIfGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater {element}", " удалить все элементы из коллекции, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean go(String arg) {
        try{
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int i = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(i)) throw new NoSuchElementException();
            Hashtable<Integer, Ticket> ht = this.collectionManager.getTicketsCollection();
            Ticket cur = ht.get(i);
            for (Map.Entry<Integer, Ticket> entry: ht.entrySet()){
                if (cur.compareTo(entry.getValue()) > 0){
                    this.collectionManager.removeWithId(entry.getKey());
                    Console.println("элемент с айди "+ entry.getKey() +" удален");}
            }

            return true;
        }
        catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage());
        }
        catch (NumberFormatException e){
            Console.print_error("айди элемента должен быть целым числом.");
        } catch (NoSuchElementException e) {
            Console.print_error("элемента с указанным айди не существует. использование: " +this.getCommandName());
        }
        return false;
    }
}

package org.example.commands;

import org.example.exceptions.CollectionIdIsTakenException;
import org.example.exceptions.WrongAmountOfArgumentsException;
import org.example.utility.CollectionManager;
import org.example.utility.Console;

/**
 * Команда удаления элемента коллекции по ключу.
 */
public class RemoveByKeyCommand extends Command{
    private CollectionManager collectionManager;
    public RemoveByKeyCommand(CollectionManager collectionManager) {
        super("remove {key}", "удалить элемент коллекции по ключу");
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean go(String arg) {
        try{
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            Integer i = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(i)) throw new CollectionIdIsTakenException();
            this.collectionManager.removeWithId(i);
            Console.println("элемент номер " + i + " удален из коллекции.");
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            Console.print_error(e.getMessage());
        }catch (NumberFormatException e){
            Console.print_error("айди элемента должен быть целым числом(");
        } catch (CollectionIdIsTakenException e) {
            Console.print_error("там и так нечего удалять(");
        }
        return false;
    }
}

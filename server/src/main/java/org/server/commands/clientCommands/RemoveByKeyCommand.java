package org.server.commands.clientCommands;

import org.server.exceptions.CollectionIdIsTakenException;
import org.server.exceptions.WrongAmountOfArgumentsException;
import org.example.interaction.Response;
import org.example.interaction.ResponseStatus;
import org.server.utility.CollectionManager;

/**
 * Команда удаления элемента коллекции по ключу.
 */
public class RemoveByKeyCommand extends ChangingCollectionCommand{
    public RemoveByKeyCommand(CollectionManager collectionManager) {
        super("remove {key}", "удалить элемент коллекции по ключу", collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg) {
        try{
            if (arg.isEmpty()) throw new WrongAmountOfArgumentsException();
            int i = Integer.parseInt(arg.trim());
            if (!this.collectionManager.isIdTaken(i)) throw new CollectionIdIsTakenException();
            this.collectionManager.removeWithId(i);
            return new Response(ResponseStatus.OK, "элемент номер " + i + " удален из коллекции.");
        } catch (WrongAmountOfArgumentsException e) {
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }catch (NumberFormatException e){
            return new Response(ResponseStatus.ERROR, "айди элемента должен быть целым числом(");
        } catch (CollectionIdIsTakenException e) {
            return new Response(ResponseStatus.ERROR, "там и так нечего удалять(");
        }
    }
}

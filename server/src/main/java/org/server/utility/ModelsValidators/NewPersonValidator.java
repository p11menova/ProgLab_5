package org.server.utility.ModelsValidators;

import org.example.models.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NewPersonValidator {
    /**
     * Новый экземпляр Person
     */
    private final Person person;

    /**
     * Конструктор
     */
    public NewPersonValidator(){
        this.person = new Person();
    }

    /**
     * Валидация роста нового экземпляра Person
     * @param height рост
     * @return true, если введенный рост корректен, иначе false
     */
    public boolean validateHeight(String height){
        try {
            double i = Double.parseDouble(height);
            if (i <= 0) {
                //console.print_error("поле height должно быть >0");
                return false;}

            this.person.setHeight(Double.valueOf(height));
            return true;
        }
        catch (NumberFormatException e){
            //console.print_error("поле height должно иметь тип double((");
        }
        return false;
    }
    /**
     * Валидация даты рождения нового экземпляра Person
     * @param bh дата рождения
     * @return true, если введенная дата рождения корректна, иначе false
     */
    public boolean validateBirthday(String bh){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
            LocalDate birthday = LocalDate.parse(bh, formatter);
            this.person.setBirthday(birthday.atStartOfDay());
            return true;
        } catch (DateTimeParseException e){
            //console.print_error("неверный формат введенных данных (");
        }
        return false;
    }

    /**
     * Запрашивает новый экземпляр класса Person
     */


}

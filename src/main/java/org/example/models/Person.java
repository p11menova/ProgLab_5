package org.example.models;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс Человека
 */
public class Person {
    @JsonProperty("birthday")
    @JsonSerialize(using=LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public java.time.LocalDateTime birthday; //Поле может быть null
    @JsonProperty("height")
    public Double height; //Поле не может быть null, Значение поля должно быть больше 0
    public void setHeight(Double height){
        this.height = height;
    }
    public void setBirthday(LocalDateTime birthday){
        this.birthday = birthday;
    }
    public Double getHeight(){
        return this.height;
    }
    public LocalDateTime getBirthday(){return this.birthday;}
    @Override
    public String toString() {
        return "\tPerson {\n" +
                "\t\tbirthday=" + DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(birthday)  +
                ", \n\t\theight=" + height +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(Person.class)) return false;
        return Objects.equals(this.height, ((Person) obj).getHeight()) && Objects.equals(this.birthday, ((Person) obj).getBirthday());

    }
}

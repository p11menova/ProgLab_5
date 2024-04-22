package org.example.utility;

import org.example.commands.ExecuteScriptCommand;
import org.example.exceptions.NoSuchElementException;

import java.util.Objects;
import java.util.Scanner;

/**
 * Класс Консоли - управление пользовательским вводом/вводом из файла
 */
public class Console {
    /**
     * Текущий сканнер
     */
    public static Scanner in; // текущий сканнер
    /**
     * Сканнер из скрипта
     */
    public static Scanner script_in; // сканнер из скрипта

    public static String promt1 = "$ ";
    public static String promt2 = " ?>";

    public static boolean notPrinting = false;

    /**
     * Печатает объект в поток стандартного вывода с новой строки
     *
     * @param obj объект для печати
     */
    public static void println(Object obj) {
        if (!notPrinting) System.out.println(obj);
    }

    /**
     * Печатает объект в поток стандартного вывода
     *
     * @param obj объект для печати
     */
    public static void printf(Object obj) {
        if (!notPrinting) System.out.printf((String) obj);
    }

    /**
     * Печатает ошибку в поток стандартного вывода с новой строки
     *
     * @param obj ошибка для печати
     */
    public static void print_error(Object obj) {
        if (!notPrinting) System.out.println("error: " + obj);
    }

    /**
     * Печатает объект в поток стандартного вывода с табуляцией с новой строки
     *
     * @param obj объект для печати
     */
    public static void println_with_t(Object obj) {
        if (!notPrinting) System.out.println("\t" + obj);
    }

    /**
     * Печатает объект в поток стандартного вывода с табуляцией
     *
     * @param obj объект для печати
     */
    public static void printf_with_t(Object obj) {
        if (!notPrinting) System.out.printf("\t" + obj);
    }

    /**
     * Обрабатывает пользовательский ввод
     */
    public static void readUserInput() {
        printf(Console.promt1);

        while (in.hasNextLine()) {
            String in_data = in.nextLine().trim() + " ";
            if (in_data.equals(" "))
                println("пустой ввод :(( для получения информации о возможных командах введите 'help'");
            else {
                String[] in_data_splited = in_data.split(" ", 2);
                String currentCommandName = in_data_splited[0];
                String currentCommandArgs = in_data_splited[1];
                if (CommandManager.go(currentCommandName, currentCommandArgs))
                    CommandManager.addToHistory(currentCommandName);
            }
            printf(Console.promt1);
        }
        Console.print_error("вы ввели символы завершения программы(");

    }

    /**
     * Устанавливает сканнер скрипта
     *
     * @param in сканнер скрипта
     */
    public static void setScriptScanner(Scanner in) {
        script_in = in;
    }

    /**
     * Устанавливает режим чтения из скрипта
     */
    public static void setFileMode() {
        in = script_in;
    }

    /**
     * Отменяет режим чтения из файла. Возврат к чтению стандартного потока ввода
     */
    public static void denyFileMode() {
        notPrinting = false;
        in = new Scanner(System.in);
    }

    /**
     * Обрабатывает чтение из скрипта
     *
     * @param in       сканнер скрипта
     * @param filename название исполняемого скрипта
     */
    public static void readScriptInput(Scanner in, String filename) {
        try {
            if (CommandManager.scriptNamesHistory.contains(filename))
                throw new RuntimeException("обнаружена рекурсия! завершение работы программы..".toUpperCase());
            CommandManager.scriptNamesHistory.push(filename);
        } catch (RuntimeException e) {
            Console.print_error(e.getMessage());
            System.exit(1);
        }
        while (in.hasNextLine()) {
            Console.setScriptScanner(in);
            Console.setFileMode();

            String in_data = Console.script_in.nextLine().trim() + " ";
            if (!in_data.equals(" ")) {
                String[] in_data_splited = in_data.split(" ", 2);
                String currentCommandName = in_data_splited[0];
                String currentCommandArgs = in_data_splited[1];


                if (CommandManager.go(currentCommandName, currentCommandArgs)) {
                    CommandManager.addToHistory(currentCommandName);
                    Console.denyFileMode();
                }

            }


        }
    }

}
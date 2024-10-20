package org.example;

import java.util.Scanner;

public class Main {
    private static StudentCommandHandler STUDENT_COMMAND_HANDLER = new StudentCommandHandler();

    public static void main(String[] args) {
        while (true) {
            printMessage();
            Command command = readCommand();
            if (command.getAction() == Action.EXIT) {
                return;
            } else if (command.getAction() == Action.ERROR) {
                continue;
            } else {
                STUDENT_COMMAND_HANDLER.processCommand(command);
            }
        }
    }

    private static Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        try {
            String code = scanner.nextLine();
            Integer actionsCode = Integer.valueOf(code);
            Action action = Action.fromCode(actionsCode);
            if (action.isRequireAdditionalData()) {
                String data = scanner.nextLine();
                if (data.matches(action.getRegex()))
                    return new Command(action, data);
                else {
                    System.out.println("Неверный ввод!");
                    return new Command(Action.ERROR);
                }
            } else
                return new Command(action);
        } catch (Exception exception) {
            System.out.println("Проблема обработки ввода " + exception.getMessage());
            return new Command(Action.ERROR);
        }

    }

    public static void printMessage() {
        System.out.println("-------------------------");
        System.out.println("0. Выход из приложения");
        System.out.println("1. Создание данных");
        System.out.println("2. Обновление данных");
        System.out.println("3. Удаление данных");
        System.out.println("4. Вывод статистики по курсам");
        System.out.println("5. Вывод статистики по городам");
        System.out.println("6. Поиск по фамилии");
        System.out.println("-------------------------");
    }
}
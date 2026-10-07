package ru.ksenia.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ksenia.NauJava.bussinessLogic.GuitarService;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

@Component
public class GuitarConsole {
    private final GuitarService guitarService;

    @Autowired
    public GuitarConsole(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        System.out.println("Менеджер гитарных пресетов");
        while (isRunning) {
            showCommandOptions();
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "create" -> createGuitar(scanner);
                case "showAll" -> showAllGuitars(guitarService.getAllGuitars());
                case "delete" -> deleteGuitar(guitarService.getAllGuitars(), scanner);
                case "update" -> updateGuitar(parts, guitarService.getAllGuitars(), scanner);
                case "help" -> help();
                case "exit" -> isRunning = false;
                default -> System.out.println("Введите команду из списка");
            }
        }
    }

    private void showCommandOptions() {
        System.out.println("Выберите действие:");
        System.out.println("help - справка");
        System.out.println("create - Добавить новую гитару");
        System.out.println("showAll - Показать список гитар");
        System.out.println("delete - Удалить гитару");
        System.out.println("update --field - Поменять поле <field> гитары");
        System.out.println("exit - Выход");
    }

    private void updateField(String text, Consumer<String> update, Scanner scanner) {
        System.out.println(text);
        String input = scanner.nextLine();
        try {
            update.accept(input);
            System.out.println("Поле успешно обновлено");
        } catch (NumberFormatException e) {
            System.out.println("Необходимо ввести число");
        }
    }

    private void showGuitarList(List<Guitar> guitars) {
        for (Guitar guitar : guitars) {
            System.out.println("ID: " + guitar.getId() + ". " + guitar.getBrand() + "-"
                    + guitar.getModel() + " " + guitar.getPickupType());
        }
    }

    private void createGuitar(Scanner scanner) {
        System.out.println("Введите бренд:");
        String brand = scanner.nextLine();
        System.out.println("Введите модель:");
        String model = scanner.nextLine();
        System.out.println("Введите количество струн:");
        int stringsCount = readInt(scanner);
        System.out.println("Введите тип звукоснимателей (Например, HSH):");
        String pickupType = scanner.nextLine();
        System.out.println("Введите описание:");
        String description = scanner.nextLine();
        guitarService.addNewGuitar(brand, model, stringsCount, pickupType, description);
        System.out.println("Гитара добавлена!");
    }

    private void showAllGuitars(List<Guitar> guitars) {
        if (guitars.isEmpty()) {
            System.out.println("Вы не добавили ни одной гитары");
            return;
        }
        System.out.println("Ваши гитары:");
        for (Guitar guitar : guitars) {
            System.out.println(guitar.getId() + ". " +
                    guitar.getBrand() + "-" + guitar.getModel()
                    + " " + guitar.getPickupType());
            System.out.println("   Количество струн: " + guitar.getStringsCount());
            System.out.println("   Описание: " + guitar.getDescription());
        }
    }

    private void deleteGuitar(List<Guitar> guitars, Scanner scanner) {
        if (guitars.isEmpty()) {
            System.out.println("Вы не добавили ни одной гитары, удалять нечего");
            return;
        }
        System.out.println("Введите ID гитары, которую нужно удалить");
        showGuitarList(guitars);
        long id = readLong(scanner);
        if (guitarService.getGuitarById(id) == null) {
            System.out.println("Гитары с таким ID нет");
            return;
        }
        guitarService.deleteGuitar(id);
        System.out.println("Гитара удалена!");
    }

    private void updateGuitar(String[] parts, List<Guitar> guitars, Scanner scanner) {
        if (guitars.isEmpty()) {
            System.out.println("Вы не добавили ни одной гитары, редактировать нечего");
            return;
        }
        if (parts.length < 2) {
            System.out.println("Укажите поле, например update --brand");
            return;
        }
        System.out.println("Введите ID гитары, которую нужно изменить");
        showGuitarList(guitars);
        long id = readLong(scanner);
        if (guitarService.getGuitarById(id) == null) {
            System.out.println("Гитары с таким ID нет");
            return;
        }
        updateFlags(parts, scanner, id);
    }

    private void updateFlags(String[] parts, Scanner scanner, Long id) {
        switch (parts[1]) {
            case "--brand" -> updateField("Введите бренд: ", br -> guitarService.updateBrand(br, id), scanner);
            case "--model" -> updateField("Введите модель: ", mod -> guitarService.updateModel(mod, id), scanner);
            case "--strings" -> updateField("Введите количество струн: ", c ->
                    guitarService.updateStringsCount(Integer.parseInt(c), id), scanner);
            case "--pickups" -> updateField("Введите тип звукоснимателей (например, HSH): ",
                    p -> guitarService.updatePickupType(p, id), scanner);
            case "--description" -> updateField("Введите описание: ", d ->
                    guitarService.updateDescription(d, id), scanner);
            default -> System.out.println("Неизвестное поле");
        }
    }

    private void help() {
        System.out.println("Доступные флаги для update:");
        System.out.println("--model - поменять модель");
        System.out.println("--brand - поменять бренд");
        System.out.println("--strings - поменять количество струн");
        System.out.println("--pickups - поменять тип звукоснимателей");
        System.out.println("--description - поменять описание");
    }

    private int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Необходимо ввести число");
            }
        }
    }

    private long readLong(Scanner scanner) {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Необходимо ввести число");
            }
        }
    }
}

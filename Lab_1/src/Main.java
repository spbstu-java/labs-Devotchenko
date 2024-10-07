import hero.Hero;
import moving.ByFoot;
import moving.Fly;
import moving.OnHorse;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hero hero = new Hero();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Выберите способ перемещения героя:");
        System.out.println("1 - Пешком");
        System.out.println("2 - На лошади");
        System.out.println("3 - Лететь");
        System.out.println("Введите 'exit' для выхода.");

         while (true) {
            System.out.print("Введите команду: ");
            command = scanner.nextLine();

             if (command.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы.");
                break;
            }

            switch (command) {
                case "1":
                    hero.setMoving(new ByFoot());
                    hero.move("на ногах");
                    break;
                case "2":
                    hero.setMoving(new OnHorse());
                    hero.move("на лошади");
                    break;
                case "3":
                    hero.setMoving(new Fly());
                    hero.move("летим");
                    break;
                default:
                    System.out.println("Неверная команда. Попробуйте снова.");
            }
        }

        scanner.close(); //test
    }
}


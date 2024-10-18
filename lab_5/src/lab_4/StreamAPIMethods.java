package lab_4;

import java.util.*;
import java.util.stream.*;

public class StreamAPIMethods {

    // Метод, возвращающий среднее значение списка целых чисел
    public static double getAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Список пуст"));
    }

    // Метод, приводящий все строки в списке в верхний регистр и добавляющий к ним префикс "_new_"
    public static List<String> transformStrings(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    // Метод, возвращающий список квадратов всех встречающихся только один раз элементов списка
    public static List<Integer> getUniqueSquares(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(e -> e.getKey() * e.getKey())
                .collect(Collectors.toList());
    }

    // Метод, принимающий на вход коллекцию и возвращающий ее последний элемент или кидающий исключение, если коллекция пуста
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));
    }

    // Метод, принимающий на вход массив целых чисел, возвращающий сумму чётных чисел или 0, если чётных чисел нет
    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    // Метод, преобразовывающий все строки в списке в Map, где первый символ – ключ, оставшиеся – значение
    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (existing, replacement) -> existing // при наличии дубликатов ключей
                ));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Выберите метод для выполнения:");
            System.out.println("1. Найти среднее значение списка целых чисел");
            System.out.println("2. Преобразовать строки в верхний регистр с префиксом \"_new_\"");
            System.out.println("3. Получить список квадратов уникальных элементов списка");
            System.out.println("4. Получить последний элемент коллекции");
            System.out.println("5. Найти сумму четных чисел в массиве");
            System.out.println("6. Преобразовать строки в Map (первый символ - ключ, остальные - значение)");
            System.out.println("0. Выход");

            int choice = Integer.parseInt(scanner.nextLine());

            switch(choice) {
                case 1:
                    System.out.println("Введите числа через пробел:");
                    String[] nums1 = scanner.nextLine().split("\\s+");
                    List<Integer> list1 = Arrays.stream(nums1)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    double average = getAverage(list1);
                    System.out.println("Среднее значение: " + average);
                    break;
                case 2:
                    System.out.println("Введите строки через запятую:");
                    String[] strs2 = scanner.nextLine().split(",");
                    List<String> list2 = Arrays.stream(strs2)
                            .map(String::trim)
                            .collect(Collectors.toList());
                    List<String> transformedStrings = transformStrings(list2);
                    System.out.println("Преобразованные строки: " + transformedStrings);
                    break;
                case 3:
                    System.out.println("Введите числа через пробел:");
                    String[] nums3 = scanner.nextLine().split("\\s+");
                    List<Integer> list3 = Arrays.stream(nums3)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    List<Integer> uniqueSquares = getUniqueSquares(list3);
                    System.out.println("Квадраты уникальных элементов: " + uniqueSquares);
                    break;
                case 4:
                    System.out.println("Введите элементы коллекции через запятую:");
                    String[] elems = scanner.nextLine().split(",");
                    List<String> collection = Arrays.stream(elems)
                            .map(String::trim)
                            .collect(Collectors.toList());
                    try {
                        String lastElement = getLastElement(collection);
                        System.out.println("Последний элемент: " + lastElement);
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Введите числа через пробел:");
                    String[] nums5 = scanner.nextLine().split("\\s+");
                    int[] array = Arrays.stream(nums5)
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    int sumEven = sumOfEvenNumbers(array);
                    System.out.println("Сумма четных чисел: " + sumEven);
                    break;
                case 6:
                    System.out.println("Введите строки через запятую:");
                    String[] strs6 = scanner.nextLine().split(",");
                    List<String> list6 = Arrays.stream(strs6)
                            .map(String::trim)
                            .collect(Collectors.toList());
                    Map<Character, String> map = stringsToMap(list6);
                    System.out.println("Результирующая Map: " + map);
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
            System.out.println();
        }
    }
}

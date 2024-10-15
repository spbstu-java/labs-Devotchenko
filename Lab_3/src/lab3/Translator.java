package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {

    private static final String DICTIONARY_FILE = "dictionary_EngtoRus.txt";
    private Map<String, String> dictionary = new HashMap<>();

    // Метод для чтения словаря из файла
    public void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    dictionary.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении словаря: " + e.getMessage());
        }
    }

    // Метод для перевода текста
    public String translate(String input) {
        String[] words = input.split(" ");
        StringBuilder translatedText = new StringBuilder();

        for (String word : words) {
            String lowerCaseWord = word.toLowerCase();
            if (dictionary.containsKey(lowerCaseWord)) {
                translatedText.append(dictionary.get(lowerCaseWord)).append(" ");
            } else {
                translatedText.append(word).append(" ");
            }
        }

        return translatedText.toString().trim();
    }

    public static void main(String[] args) {
        Translator translator = new Translator();  // Исправлено имя переменной
        translator.loadDictionary();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите текст для перевода:");
        String input = scanner.nextLine();

        String translatedText = translator.translate(input);
        System.out.println("Перевод: " + translatedText);
    }
}

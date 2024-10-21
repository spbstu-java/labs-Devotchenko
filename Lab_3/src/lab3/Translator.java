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
    private int maxPhraseLength = 1;

    // Метод для чтения словаря из файла
    public void loadDictionary() throws InvalidFileFormatException, FileReadException {
        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String key = parts[0].trim().toLowerCase();
                    dictionary.put(key, parts[1].trim());
                    // Обновление максимальной длины фразы
                    int wordCount = key.split(" ").length;
                    if (wordCount > maxPhraseLength) {
                        maxPhraseLength = wordCount;
                    }
                } else {
                    throw new InvalidFileFormatException("Неверный формат строки в словаре: " + line);
                }
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении файла словаря: " + e.getMessage());
        }
    }

    // Метод для перевода текста
    public String translate(String input) {
        String[] words = input.split(" ");
        StringBuilder translatedText = new StringBuilder();

        int i = 0;
        while (i < words.length) {
            boolean found = false;
            int maxMatchLength = Math.min(maxPhraseLength, words.length - i);
            for (int len = maxMatchLength; len > 0; len--) {
                StringBuilder phraseBuilder = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    if (j > 0) {
                        phraseBuilder.append(" ");
                    }
                    phraseBuilder.append(words[i + j].toLowerCase());
                }
                String phrase = phraseBuilder.toString();
                if (dictionary.containsKey(phrase)) {
                    translatedText.append(dictionary.get(phrase)).append(" ");
                    i += len;
                    found = true;
                    break;
                }
            }
            if (!found) {
                translatedText.append(words[i]).append(" ");
                i++;
            }
        }

        return translatedText.toString().trim();
    }

    public static void main(String[] args) {
        Translator translator = new Translator();
        try {
            translator.loadDictionary();
        } catch (InvalidFileFormatException e) {
            System.err.println("Ошибка формата файла словаря: " + e.getMessage());
            return;
        } catch (FileReadException e) {
            System.err.println("Ошибка чтения файла словаря: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите текст для перевода:");
        String input = scanner.nextLine();

        String translatedText = translator.translate(input);
        System.out.println("Перевод: " + translatedText);
    }

    // Класс исключения для неверного формата файла словаря
    public static class InvalidFileFormatException extends Exception {
        public InvalidFileFormatException(String message) {
            super(message);
        }
    }

    // Класс исключения для ошибок при чтении файла словаря
    public static class FileReadException extends Exception {
        public FileReadException(String message) {
            super(message);
        }
    }
}

package lab_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Translator {

    private Map<String, String> dictionary = new HashMap<>();
    private int maxPhraseLength = 1;

    // Метод для чтения словаря из файла
    public void loadDictionary(File dictionaryFile) throws InvalidFileFormatException, FileReadException {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile))) {
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

    // Исключения

    public static class InvalidFileFormatException extends Exception {
        public InvalidFileFormatException(String message) {
            super(message);
        }
    }

    public static class FileReadException extends Exception {
        public FileReadException(String message) {
            super(message);
        }
    }
}

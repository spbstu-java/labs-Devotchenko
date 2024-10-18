package Lab_5;

import lab_1.Hero;
import lab_1.Fly;
import lab_1.ByFoot;
import lab_1.OnHorse;

import lab_2.MethodInvoker;

import lab_3.Translator;

import lab_4.StreamAPIMethods;

import java.util.List;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MainGUI extends JFrame {
    private JTextArea outputArea;
    private JComboBox<String> taskSelector;
    private JPanel inputPanel;
    private File dictionaryFile; // Выбранный файл словаря

    public MainGUI() {
        setTitle("Lab Tasks GUI Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Components
        taskSelector = new JComboBox<>(new String[]{
                "Task 1: Translator",
                "Task 2: Hero Movement",
                "Task 3: Method Invocation",
                "Task 4: Stream API Operations"
        });
        JButton executeButton = new JButton("Execute");
        outputArea = new JTextArea(20, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        inputPanel = new JPanel(new GridLayout(3, 1));

        // Add components to frame
        add(taskSelector, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(executeButton, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.EAST);

        // Action listener for the execute button
        executeButton.addActionListener(new ExecuteButtonListener());

        // Action listener for task selection to update input panel
        taskSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInputPanel(taskSelector.getSelectedIndex());
            }
        });

        // Initialize the input panel for the first selected task
        updateInputPanel(taskSelector.getSelectedIndex());

        setVisible(true);
    }

    private void updateInputPanel(int selectedTask) {
        inputPanel.removeAll();
        switch (selectedTask) {
            case 0:
                // Task 1: Translator
                JButton chooseFileButton = new JButton("Выбрать файл словаря");
                chooseFileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        int result = fileChooser.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            dictionaryFile = fileChooser.getSelectedFile();
                            outputArea.setText("Выбран файл словаря: " + dictionaryFile.getAbsolutePath());
                        }
                    }
                });
                JTextField textInput = new JTextField("Введите текст для перевода");
                textInput.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (textInput.getText().equals("Введите текст для перевода")) {
                            textInput.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (textInput.getText().isEmpty()) {
                            textInput.setText("Введите текст для перевода");
                        }
                    }
                });
                inputPanel.add(chooseFileButton);
                inputPanel.add(textInput);
                break;
            case 1:
                // Task 2: Hero Movement
                JTextField heroMovementInput = new JTextField("1 - Пешком, 2 - На лошади, 3 - Лететь");
                heroMovementInput.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (heroMovementInput.getText().equals("1 - Пешком, 2 - На лошади, 3 - Лететь")) {
                            heroMovementInput.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (heroMovementInput.getText().isEmpty()) {
                            heroMovementInput.setText("1 - Пешком, 2 - На лошади, 3 - Лететь");
                        }
                    }
                });
                inputPanel.add(new JLabel("Выберите способ перемещения героя:"));
                inputPanel.add(heroMovementInput);
                break;
            case 2:
                // Task 3: Method Invocation
                inputPanel.add(new JLabel("Вызов методов с использованием рефлексии"));
                break;
            case 3:
                // Task 4: Stream API Operations
                JComboBox<String> streamMethodSelector = new JComboBox<>(new String[]{
                        "Среднее значение чисел (Введите числа через пробел)",
                        "Строки в верхнем регистре с префиксом '_new_' (Введите строки через запятую)",
                        "Квадраты уникальных элементов (Введите числа через пробел)",
                        "Последний элемент коллекции (Введите элементы через запятую)",
                        "Сумма четных чисел (Введите числа через пробел)",
                        "Преобразовать строки в Map (первый символ - ключ) (Введите строки через запятую)"
                });
                JTextField streamInput = new JTextField("Введите значения здесь");
                streamInput.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (streamInput.getText().equals("Введите значения здесь")) {
                            streamInput.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (streamInput.getText().isEmpty()) {
                            streamInput.setText("Введите значения здесь");
                        }
                    }
                });
                inputPanel.add(new JLabel("Выберите операцию:"));
                inputPanel.add(streamMethodSelector);
                inputPanel.add(streamInput);
                break;
        }
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private class ExecuteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedTask = taskSelector.getSelectedIndex();
            Component[] components = inputPanel.getComponents();

            try {
                switch (selectedTask) {
                    case 0:
                        // Translator Task
                        if (components.length > 1 && components[1] instanceof JTextField) {
                            JTextField textInput = (JTextField) components[1];
                            String textToTranslate = textInput.getText();
                            if (dictionaryFile != null) {
                                Translator translator = new Translator();
                                translator.loadDictionary(dictionaryFile);
                                String translatedText = translator.translate(textToTranslate);
                                outputArea.setText("Перевод: " + translatedText);
                            } else {
                                outputArea.setText("Пожалуйста, сначала выберите файл словаря.");
                            }
                        } else {
                            outputArea.setText("Ошибка: Поля ввода не инициализированы должным образом.");
                        }
                        break;
                    case 1:
                        // Hero Movement Task
                        if (components.length > 1 && components[1] instanceof JTextField) {
                            JTextField commandInput = (JTextField) components[1];
                            String command = commandInput.getText();
                            Hero hero = new Hero();
                            switch (command) {
                                case "1":
                                    hero.setMoving(new ByFoot());
                                    hero.move("пешком");
                                    outputArea.setText("Герой переместился пешком.");
                                    break;
                                case "2":
                                    hero.setMoving(new OnHorse());
                                    hero.move("на лошади");
                                    outputArea.setText("Герой переместился на лошади.");
                                    break;
                                case "3":
                                    hero.setMoving(new Fly());
                                    hero.move("лететь");
                                    outputArea.setText("Герой полетел.");
                                    break;
                                default:
                                    outputArea.setText("Неверная команда.");
                            }
                        } else {
                            outputArea.setText("Ошибка: Поля ввода не инициализированы должным образом.");
                        }
                        break;
                    case 2:
                        // Method Invocation Task
                        outputArea.setText("Вызов методов с использованием рефлексии...\n");
                        PrintStream consoleStream = System.out;
                        System.setOut(new PrintStream(new TextAreaOutputStream(outputArea)));
                        MethodInvoker.main(new String[]{});
                        System.setOut(consoleStream);
                        break;
                    case 3:
                        // Stream API Operations
                        if (components.length > 2 && components[1] instanceof JComboBox && components[2] instanceof JTextField) {
                            JComboBox<String> streamMethodSelector = (JComboBox<String>) components[1];
                            String selectedMethod = (String) streamMethodSelector.getSelectedItem();
                            JTextField listInput = (JTextField) components[2];
                            String inputData = listInput.getText();

                            switch (selectedMethod) {
                                case "Среднее значение чисел (Введите числа через пробел)":
                                    if (!inputData.isEmpty()) {
                                        List<Integer> numbers = Arrays.stream(inputData.split(" "))
                                                .map(Integer::parseInt)
                                                .collect(Collectors.toList());
                                        double average = StreamAPIMethods.getAverage(numbers);
                                        outputArea.setText("Среднее значение: " + average);
                                    }
                                    break;
                                case "Строки в верхнем регистре с префиксом '_new_' (Введите строки через запятую)":
                                    if (!inputData.isEmpty()) {
                                        List<String> strings = Arrays.asList(inputData.split(","));
                                        List<String> transformedStrings = StreamAPIMethods.transformStrings(strings);
                                        outputArea.setText("Преобразованные строки: " + transformedStrings);
                                    }
                                    break;
                                case "Квадраты уникальных элементов (Введите числа через пробел)":
                                    if (!inputData.isEmpty()) {
                                        List<Integer> numbers = Arrays.stream(inputData.split(" "))
                                                .map(Integer::parseInt)
                                                .collect(Collectors.toList());
                                        List<Integer> uniqueSquares = StreamAPIMethods.getUniqueSquares(numbers);
                                        outputArea.setText("Квадраты уникальных элементов: " + uniqueSquares);
                                    }
                                    break;
                                case "Последний элемент коллекции (Введите элементы через запятую)":
                                    if (!inputData.isEmpty()) {
                                        List<String> collection = Arrays.asList(inputData.split(","));
                                        try {
                                            String lastElement = StreamAPIMethods.getLastElement(collection);
                                            outputArea.setText("Последний элемент: " + lastElement);
                                        } catch (NoSuchElementException ex) {
                                            outputArea.setText("Ошибка: Коллекция пуста.");
                                        }
                                    }
                                    break;
                                case "Сумма четных чисел (Введите числа через пробел)":
                                    if (!inputData.isEmpty()) {
                                        int[] numbers = Arrays.stream(inputData.split(" "))
                                                .mapToInt(Integer::parseInt)
                                                .toArray();
                                        int sum = StreamAPIMethods.sumOfEvenNumbers(numbers);
                                        outputArea.setText("Сумма четных чисел: " + sum);
                                    }
                                    break;
                                case "Преобразовать строки в Map (первый символ - ключ) (Введите строки через запятую)":
                                    if (!inputData.isEmpty()) {
                                        List<String> strings = Arrays.asList(inputData.split(","));
                                        Map<Character, String> map = StreamAPIMethods.stringsToMap(strings);
                                        outputArea.setText("Результирующая Map: " + map);
                                    }
                                    break;
                                default:
                                    outputArea.setText("Выберите корректную операцию.");
                            }
                        } else {
                            outputArea.setText("Ошибка: Поля ввода не инициализированы должным образом.");
                        }
                        break;
                }
            } catch (Exception ex) {
                outputArea.setText("Ошибка: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}

class TextAreaOutputStream extends java.io.OutputStream {
    private JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws java.io.IOException {
        textArea.append(String.valueOf((char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    // Метод для выполнения арифметических операций
    public static String calc(String input) {
        try {
            // Разбиваем входную строку на три части: число, оператор, число
            String[] parts = input.trim().split("\\s+");
            if (parts.length != 3)
                throw new IllegalArgumentException("Invalid input format");

            // Извлекаем числа и оператор из массива частей
            String num1Str = parts[0];
            String operator = parts[1];
            String num2Str = parts[2];

            // Определяем, являются ли введенные числа римскими
            boolean isRoman = isRoman(num1Str) && isRoman(num2Str);

            // Преобразуем числа в числовые значения
            int num1 = parseNumber(num1Str);
            int num2 = parseNumber(num2Str);

            // Выполняем выбранную арифметическую операцию
            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    // Выполняем деление и возвращаем результат в зависимости от типа чисел
                    if (isRoman) {
                        // Делаем проверку на ноль, чтобы избежать деления на ноль
                        if (num2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        // Возвращаем результат деления в римском формате
                        result = num1 / num2;
                    } else {
                        // Возвращаем результат деления в арабском формате
                        result = num1 / num2;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }

            // Возвращение результата операции в зависимости от типа чисел
            if (isRoman) {
                // Преобразуем десятичное число в римское и возвращаем результат
                return decimalToRoman(result);
            } else {
                // Преобразуем результат в строку и возвращаем его
                return String.valueOf(result);
            }
        } catch (Exception e) {
            // Обработка исключений и возврат сообщения об ошибке
            return "throws Exception";
        }
    }

    // Метод для определения типа числа (арабское или римское)
    private static int parseNumber(String str) {
        if (isRoman(str))
            return romanToDecimal(str);
        else
            return Integer.parseInt(str);
    }

    // Метод для проверки, является ли число римским
    private static boolean isRoman(String str) {
        return str.matches("[IVXLCDM]+");
    }

    // Метод для преобразования римских чисел в десятичные значения
    private static int romanToDecimal(String roman) {
        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        int decimal = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanValues.get(roman.charAt(i));
            if (currentValue < prevValue)
                decimal -= currentValue;
            else
                decimal += currentValue;
            prevValue = currentValue;
        }
        return decimal;
    }

    // Метод для преобразования десятичного числа в римское
    private static String decimalToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Number out of range (1-3999)");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();

        int i = 0;
        while (num > 0) {
            while (num >= romanValues[i]) {
                result.append(romanSymbols[i]);
                num -= romanValues[i];
            }
            i++;
        }

        return result.toString();
    }

    // Метод для чтения ввода и вывода результатов
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input:");
        String input = scanner.nextLine();
        String output = calc(input);
        System.out.println("\nOutput:");
        System.out.println(output);
    }
}

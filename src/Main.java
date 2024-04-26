import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
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
            boolean isRomanNum1 = isRoman(num1Str);
            boolean isRomanNum2 = isRoman(num2Str);

            // Проверяем, являются ли оба числа одного типа (или оба арабские, или оба римские)
            if (isRomanNum1 != isRomanNum2)
                throw new IllegalArgumentException("Both numbers should be of the same type");

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
                    // Проверка на деление на ноль
                    if (num2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }

            // Возвращение результата операции в зависимости от типа чисел
            if (isRomanNum1) {
                // Проверка на допустимый диапазон римских чисел (1-3999)
                if (result < 1 || result > 3999)
                    throw new IllegalArgumentException("Result out of range (1-3999)");
                // Преобразуем десятичное число в римское и возвращаем результат
                return decimalToRoman(result);
            } else {
                // Проверка на допустимый диапазон арабских чисел (1-10)
                if (result < 1 || result > 10)
                    throw new IllegalArgumentException("Result out of range (1-10)");
                // Преобразуем результат в строку и возвращаем его
                return String.valueOf(result);
            }
        } catch (NumberFormatException e) {
            return "Invalid number format";
        } catch (ArithmeticException e) {
            return "Arithmetic exception: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Invalid input: " + e.getMessage();
        }
    }

    // Метод для определения типа числа (арабское или римское)
    private static int parseNumber(String str) {
        int num;
        try {
            if (isRoman(str)) {
                num = romanToDecimal(str);
                if (num < 1 || num > 10) {
                    throw new IllegalArgumentException("Number out of range (1-10)");
                }
            } else {
                num = Integer.parseInt(str);
                if (num < 1 || num > 10) {
                    throw new IllegalArgumentException("Number out of range (1-10)");
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        }
        return num;
    }



    // Метод для проверки, является ли число римским
    private static boolean isRoman(String str) {
        // Проверяем, начинается ли строка с одного из символов римских цифр
        if (!str.matches("[MDCLXVI].*")) {
            return false;
        }

        // Проверяем, что строка не содержит недопустимых комбинаций символов
        if (str.matches(".*[MD](?![C])|[MDC](?!M)|[MD]DD|LL|VV|IIII|I[VX]|X[XLC]|C[DM]|D[MV]")) {
            return false;
        }

        return true;
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

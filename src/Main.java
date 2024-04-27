import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    // Метод для выполнения арифметических операций и обработки исключений
    public static String calc(String input) {
        try {
            // Разбиение входной строки на три части: число, оператор, число
            String[] parts = input.trim().split("\\s+");
            if (parts.length != 3)
                throw new IllegalArgumentException("Invalid input format");

            // Извлечение чисел и оператора из массива частей
            String num1Str = parts[0];
            String operator = parts[1];
            String num2Str = parts[2];

            // Преобразование строковых чисел в числовые значения
            int num1 = parseNumber(num1Str);
            int num2 = parseNumber(num2Str);

            // Проверка допустимого диапазона чисел
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10)
                throw new IllegalArgumentException("Numbers should be in the range of 1 to 10");

            // Проверка, являются ли числа римскими или арабскими
            boolean isRomanNum1 = isRoman(num1Str);
            boolean isRomanNum2 = isRoman(num2Str);

            // Проверка, что оба числа одного типа (либо римские, либо арабские)
            if (isRomanNum1 != isRomanNum2)
                throw new IllegalArgumentException("Numbers should be of the same type (either Roman or Arabic)");

            // Выполнение выбранной арифметической операции
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
                    if (num2 == 0)
                        throw new ArithmeticException("Division by zero");
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }

            // Возврат результата в зависимости от типа чисел (римские или арабские)
            if (isRomanNum1) {
                if (result <= 0)
                    throw new IllegalArgumentException("Result cannot be zero or negative in Roman numerals");
                return decimalToRoman(result);
            } else {
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

    // Метод для преобразования строки в число с проверкой на допустимые значения
    private static int parseNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            if (num < 0 || num > 10)
                throw new IllegalArgumentException("Numbers should be in the range of 0 to 10");
            return num;
        } catch (NumberFormatException e) {
            // Если не удалось преобразовать в число, проверяем, является ли строка римским числом
            if (!isRoman(str))
                throw new IllegalArgumentException("Invalid number format");
            // Если строка - римское число, преобразуем его в десятичное значение
            int decimalValue = romanToDecimal(str);
            // Проверяем, что римское число соответствует правилам
            if (!isValidRoman(str))
                throw new IllegalArgumentException("Invalid number format: " + str);
            return decimalValue;
        }
    }

    // Метод для проверки, является ли строка римским числом
    private static boolean isRoman(String str) {
        return str.matches("^[IVXLCDM]+$");
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

    // Метод для проверки допустимости римских чисел
    private static boolean isValidRoman(String str) {
        return str.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    // Главный метод программы для чтения ввода и вывода результатов
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input:");
        String input = scanner.nextLine();
        String output = calc(input);
        System.out.println("\nOutput:");
        System.out.println(output);
    }
}

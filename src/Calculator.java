import java.util.Scanner;

public class Calculator {


    private static final String[] romanNumeral = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] romanValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String ROMAN_REGEX = "^C?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$";
    private static final String OPERATION_REGEX = "[+\\-*/]";

    public static void main(String[] args) {
        System.out.println("Введите выражение для счета:");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        String[] operands = expression.split(OPERATION_REGEX);
        if (operands.length != 2 || operands[0].isBlank()) {
            throw new IllegalArgumentException("Должно быть только два положительных аргумента!");
        }
        String operator = String.valueOf(expression.replace(operands[0], "").charAt(0));
        if (expression.length() - expression.replaceAll(OPERATION_REGEX, "").length() > 1) {
            throw new IllegalArgumentException("Операция может быть только одна!");
        }
        operands[0] = operands[0].trim();
        operands[1] = operands[1].trim();
        if (isRoman(operands[0]) != isRoman(operands[1])) {
            throw new IllegalArgumentException("Оба аргумента должны быть или арабскими числами, или римскими!");
        }

        int num1;
        int num2;
        if (!isRoman(operands[0])) {
            try {
                num1 = Integer.parseInt(operands[0]);
                num2 = Integer.parseInt(operands[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Арабские числа введены не корректно!");
            }
        } else {
            num1 = converterRomanToArab(operands[0]);
            num2 = converterRomanToArab(operands[1]);
        }
        if (!(num1 > 0 && num1 <= 10 && num2 > 0 && num2 <= 10)) {
            throw new IllegalArgumentException("Значения чисел должно быть в пределах от 1 до 10 включительно!");
        }
        int result = calc(num1, num2, operator);
        System.out.println("Результат: " + (isRoman(operands[0]) ? converterArabToRoman(result) : result));
    }

    private static String converterArabToRoman(int value) throws IllegalArgumentException{
        if (value < 1) {
            throw new IllegalArgumentException("Результат меньше 1. Он не может быть выражен римскими цифрами");
        }
        StringBuilder resultRomanString = new StringBuilder();
        for (int i = 0; i < romanNumeral.length; i++) {
            while (value >= romanValues[i]) {
                value -= romanValues[i];
                resultRomanString.append(romanNumeral[i]);
            }
        }
        return resultRomanString.toString();
    }

    private static int converterRomanToArab(String value) throws IllegalArgumentException {
        StringBuilder sbValue = new StringBuilder(value.trim());
        int resultArabInt = 0;
        for (int i = romanNumeral.length - 1; i >= 0; i--) {
            while (sbValue.toString().endsWith(romanNumeral[i])) {
                resultArabInt += romanValues[i];
                sbValue.delete(sbValue.length() - romanNumeral[i].length(), sbValue.length());
            }
        }
        return resultArabInt;
    }

    private static boolean isRoman(String string) {
        return string.matches(ROMAN_REGEX);
    }

    private static int calc(int a, int b, String operator) {
        if (operator.equals("+")) return a + b;
        if (operator.equals("-")) return a - b;
        if (operator.equals("*")) return a * b;
        return a / b;
    }
}
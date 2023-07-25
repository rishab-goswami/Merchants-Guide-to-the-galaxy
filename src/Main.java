import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.lang.String;
import java.lang.Character;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static String glob;
    static String prok;
    static String pish;
    static String tegj;
    static String silver;
    static String gold;
    static String  iron;

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        String [] output;

        output = processKeyboardData();
        processNotes(output);

    }

    private static String[] processKeyboardData() {
        System.out.println("Hello and welcome!");
        System.out.println("Please add your notes and then once you're finished type done");

        Scanner keyboard = new Scanner(System.in);
        String [] inputs = new String[100];
        int     counter = 0;
        boolean finishReading = false;

        do
        {
            inputs[counter] = keyboard.nextLine().toLowerCase().strip();
            if(inputs[counter].contains("done"))   finishReading = true;
            counter++;
        }
        while (!finishReading);
        String[] cleanedInputs;
        cleanedInputs = Arrays.stream(inputs).filter(Objects::nonNull).toArray(String[]::new);
        return cleanedInputs;
    }

    private static void processNotes(String[] keyBoardInputs) {

        String currentLine;
        String [] currentLineSplit;
        int splitLength;

        for (String keyBoardInput : keyBoardInputs) {

            currentLine = keyBoardInput;
            currentLineSplit = currentLine.split(" ");
            splitLength = currentLineSplit.length;

            if (currentLine.startsWith("how much is ") || currentLine.startsWith("how many credits is")) {
                sumUnits(currentLine);
            } else if ((splitLength == 3)) {
                if (isValidRomanNumeral(currentLineSplit[splitLength - 1])) {
                    processSingleVariableRomanNumeral(currentLine);
                }
            } else if (currentLine.startsWith("glob") || currentLine.startsWith("prok") || currentLine.startsWith("pish")
                    || currentLine.startsWith("tegj") || currentLine.startsWith("gold") ||currentLine.startsWith("silver")
                    || currentLine.startsWith("iron")) {
                calculateMissingValue(currentLine);
            } else if (currentLine.equals("done")){

            } else {
                System.out.println("I have no idea what you're talking about");
            }
        }
    }

    private static void calculateMissingValue(String currentLine){

        int result = 0;
        String[] splitString;
        int total;
        String[] units;
        String missingUnit = null;
        int missingUnitCount = 0;


        final Map<String, String> variableMap = new HashMap<>();
        variableMap.put("glob", glob);
        variableMap.put("prok", prok);
        variableMap.put("pish", pish);
        variableMap.put("tegj", tegj);
        variableMap.put("gold", gold);
        variableMap.put("silver", silver);
        variableMap.put("iron", iron);

       splitString = currentLine.split(" is ");
       units = splitString[0].split(" ");
       total = Integer.parseInt(splitString[1].split(" ")[0]) ;

        for (String unit : units) {
            String romanValue = variableMap.get(unit);
            if (romanValue != null) {
                int value = romanToInteger(romanValue);
                total = total - value;
            }else {
                missingUnit = unit;
                missingUnitCount++;

            }
        }

        if (missingUnitCount == 1){
            if(missingUnit.equals("glob")){
                glob = intToRoman(total);
            } else if(missingUnit.equals("prok")){
                prok = intToRoman(total);
            } else if(missingUnit.equals("pish")) {
                pish = intToRoman(total);
            } else if(missingUnit.equals("tegj")) {
                tegj = intToRoman(total);
            } else if(missingUnit.equals("silver")) {
                silver = intToRoman(total);
            } else if(missingUnit.equals("gold")) {
                gold = intToRoman(total);
            } else if(missingUnit.equals("iron")) {
                iron = intToRoman(total);
            }
        } else if (missingUnitCount > 1) {
            System.out.println("Too many missing variables, unable to compute");
        }


    }

    //assuming that we sum the units e.g. how much is glob pish tegj = how much is glob + pish + tegj
    private static void sumUnits(String currentLine) {

        int result = 0;
        int count = 0;
        String variableString;
        String[] units;

        final Map<String, String> variableMap = new HashMap<>();
        variableMap.put("glob", glob);
        variableMap.put("prok", prok);
        variableMap.put("pish", pish);
        variableMap.put("tegj", tegj);
        variableMap.put("gold", gold);
        variableMap.put("silver", silver);
        variableMap.put("iron", iron);

        if(currentLine.endsWith("?")){
            currentLine = currentLine.substring(0, currentLine.length()-1);
        }

        if (currentLine.startsWith("how much is")){
            variableString = currentLine.substring("how much is ".length(), currentLine.length());
            units = variableString.split(" ");
        } else {
            variableString = currentLine.substring("how many credits is ".length(), currentLine.length());
            units = variableString.split(" ");
        }

        for (String unit : units) {
            String romanValue = variableMap.get(unit);

            if (romanValue == null) {
                count++;
            }
        }

        if (count != units.length){

            for (String unit1 : units) {
                String romanValue1 = variableMap.get(unit1);
                if (romanValue1 != null) {
                    result += romanToInteger( romanValue1);
                }else {
                    // Variable not found in the mapping
                    System.out.println(unit1 + "'s value not set. Or mapping not found!");
                    return;
                }
            }
            System.out.println(variableString + " is " + result + " credits");
        } else {
            System.out.println("I have no idea what you're talking about");
        }
    }

    private static void processSingleVariableRomanNumeral(String input) {
        String [] temp = input.split(" ");

        if(input.contains("glob")){
            glob = temp[temp.length-1];
        } else if(input.contains("prok")){
            prok = temp[temp.length-1];
        } else if(input.contains("pish")) {
            pish = temp[temp.length-1];
        } else if(input.contains("tegj")) {
            tegj = temp[temp.length-1];
        } else if(input.contains("silver")) {
            silver = temp[temp.length-1];
        } else if(input.contains("gold")) {
            gold = temp[temp.length-1];
        } else if(input.contains("iron")) {
            iron = temp[temp.length-1];
        }

    }

    private static boolean isValidRomanNumeral(String input) {
        // A valid Roman numeral should only contain the following letters:
        // I, V, X, L, C, D, M
        if (!input.matches("[ivxlcdm]+")) {
            System.out.println(input + " is an incorrect roman numeral");
            return false;
        }

        // Check for repeated letters more than three times in a row
        if (input.matches(".*[ivxlcdm]{4}.*")) {
            System.out.println(input + " is an incorrect roman numeral");
            return false;
        }

        // Check for invalid combinations like "VV" or "DD"
        if (input.matches(".*[vld]{2,}.*")) {
            System.out.println(input + " is an incorrect roman numeral");
            return false;
        }

        // Check for invalid substractive combinations like "IL" or "IC"
        if (input.matches(".*I[lcdm].*") || input.matches(".*X[DM].*")) {
            System.out.println(input + " is an incorrect roman numeral");
            return false;
        }

        // Check for invalid subtractive combinations like "IIV" or "XXL"
        if (input.matches(".*[ivx]{2,}L.*") || input.matches(".*[IVX]{2,}D.*")) {
            System.out.println(input + " is an incorrect roman numeral");
            return false;
        }

        return true;
    }

    private static int romanToInteger(String input) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('i', 1);
        romanMap.put('v', 5);
        romanMap.put('x', 10);
        romanMap.put('l', 50);
        romanMap.put('c', 100);
        romanMap.put('d', 500);
        romanMap.put('m', 1000);
        int result = 0;
        int prevValue = 0;


        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            int value = romanMap.get(c);

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    private static String intToRoman(int input) {
        TreeMap<Integer, String> romanMap = new TreeMap<>();
        {
            romanMap.put(1, "i");
            romanMap.put(4, "iv");
            romanMap.put(5, "v");
            romanMap.put(9, "ix");
            romanMap.put(10, "x");
            romanMap.put(40, "xl");
            romanMap.put(50, "l");
            romanMap.put(90, "xc");
            romanMap.put(100, "c");
            romanMap.put(400, "cd");
            romanMap.put(500, "d");
            romanMap.put(900, "cm");
            romanMap.put(1000, "m");
        }

        int closestValue = romanMap.floorKey(input);
        if (input == closestValue) {
            return romanMap.get(input);
        }
        return romanMap.get(closestValue) + intToRoman(input - closestValue);
    }

}
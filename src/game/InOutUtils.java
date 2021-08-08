package game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import static java.util.stream.Collectors.joining;
import static game.ProcessUtils.UTF_8;

/**
 * Функции ввода/вывода.
 */
@SuppressWarnings("squid:S00108")
public final class InOutUtils {

    public static final int NO_MANS_TEAM_ID = -1;
    public static final int TEAM_0_ID = 0;
    public static final int TEAM_1_ID = 1;

    public static final String ROSTER_STRING_DELIMITER = " ";
    public static final int PLANET_FROM_INDEX = 0;
    public static final int PLANET_TO_INDEX = 1;
    public static final int COLONISTS_COUNT_INDEX = 2;
    public static final String NEW_LINE_CONTROL_CHARACTER = "\n";
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int MAX_STEPS_INDEX = 1;
    public static final int CURRENT_STEP_INDEX = 2;
    public static final int PLANET_COUNT_INDEX = 3;

    //Planets
    public static final int PLANET_ID_INDEX = 0;
    public static final int PLANET_OWNER_INDEX = 1;
    public static final int PLANET_POPULATION_INDEX = 2;
    public static final int PLANET_REPRODUCTION_INDEX = 3;

    //MovingGroup
    public static final int TEAM_ID_INDEX = 0;
    public static final int FROM_INDEX = 1;
    public static final int TO_INDEX = 2;
    public static final int COUNT_INDEX = 3;
    public static final int STEPS_LEFT_INDEX = 4;


    private InOutUtils() {
        //Utility
    }

    /**
     * Строка в целое число.
     * @param s строка
     * @return целое число
     */
    public static Integer stringToInteger(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Строка в список подстрок по разделителю.
     * @param string строка
     * @param delimiter разделитель
     * @return список подстрок
     */
    public static List<String> stringToListOfStrings(String string, String delimiter){
        return new ArrayList<>(Arrays.asList(string.split(delimiter)));
    }

    /**
     * Строка в список подстрок по разделителю-пробелу.
     * @param string строка
     * @return список подстрок
     */
    public static List<String> stringToListOfStrings(String string){
        return stringToListOfStrings(string, ROSTER_STRING_DELIMITER);
    }

    /**
     * Список строк в список целых чисел.
     * @param source список строк
     * @return список целых чисел
     * Возвращает пустой список, если хотя бы один из элементов списка строк не преобразуется к целому числу
     */
    public static List<Integer> listOfStringsToListOfIntegers(List<String> source){
        List<Integer> target = new ArrayList<>();
        for(String s : source){
            target.add(stringToInteger(s));
        }
        if(target.stream().filter(Objects::isNull).count() > 0){
            target = new ArrayList<>();
        }
        return target;
    }

    /**
     * Строка в список целых чисел (по разделителю-пробелу).
     * @param string строка
     * @return список целых чисел
     */
    public static List<Integer> stringToListOfIntegers(String string){
        return listOfStringsToListOfIntegers(stringToListOfStrings(string));
    }

    /**
     * Содержимое строки - число?
     * @param s строка
     * @return число?
     */
    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Преобразовать ввод - список строк в список списков целых чисел.
     * @param input ввод
     * @return список списков целых чисел
     */
    public static List<List<Integer>> parseInput(List<String> input){
        List<List<Integer>> result = new ArrayList<>();
        for(String s : input){
            result.add(listOfStringsToListOfIntegers(stringToListOfStrings(s)));
        }
        return result;
    }

    /**
     * Список целых чисел в строку по разделителю
     * @param list список
     * @param delimiter разделитель
     * @return строка
     */
    public static String listOfIntegerToString(List<Integer> list, String delimiter){
        return list.stream().map(Object::toString).collect(joining(delimiter));
    }

    /**
     * Список целых чисел в строку (по разделителю-пробелу).
     * @param list список
     * @return строка
     */
    public static String listOfIntegerToString(List<Integer> list){
        return listOfIntegerToString(list, ROSTER_STRING_DELIMITER);
    }

    /**
     * Прочитать текстовый файл в строку.
     * @param path путь к файлу
     * @return строка
     */
    public static String readTextFile(Path path){
        try {
            return Files.readAllLines(path, Charset.forName(UTF_8)).stream().collect(joining(ROSTER_STRING_DELIMITER));
        } catch (IOException e) {
        }
        return "";
    }

    /**
     * Записать строку в файл.
     * @param path путь к файлу
     * @param content содержимое
     */
    public static void writeToTextFile(Path path, String content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))){
            writer.write(content);
        } catch (IOException e) {
        }
    }

    /**
     * Прочитать последовательность целых чисел из InputStream.
     * @param source InputStream
     * @param charsetName кодировка
     * @return список целых чисел
     */
    public static List<Integer> readIntsFromInputStream(InputStream source, String charsetName){
        List<Integer> integers = new ArrayList<>();
        Scanner scanner = new Scanner(source, charsetName);
        while(scanner.hasNext()){
            if(scanner.hasNextInt()){
                integers.add(scanner.nextInt());
            } else {
                scanner.next();
            }
        }
        return integers;
    }

    /**
     * Прочитать строки из InputStream.
     * @param source InputStream
     * @param charsetName кодировка
     * @return список строк
     */
    public static List<String> readStringsFromInputStream(InputStream source, String charsetName){
        List<String> strings = new ArrayList<>();
        Scanner scanner = new Scanner(source, charsetName);
        while(scanner.hasNext()){
            if(scanner.hasNextLine()){
                strings.add(scanner.nextLine());
            } else {
                scanner.next();
            }
        }
        return strings;
    }

}

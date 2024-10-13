package org.example;

/*
        Напишите удаление всех повторяющихся элементов из списка [x]

        Напишите подсчет количества строк в списке, которые начинаются с определенной буквы [x]

        Используя оператор findFirst напишите поиск второго по величине элемента в списке целых чисел []
*/

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.println(deleteAllRepeat(List.of("1", "1", "61", "1", "31", "21", "14", "1", "2", "2")));
        System.out.println(countStringsWithLetterStarts(List.of("1", "1", "61", "1", "31", "21", "14", "1", "2", "2"), "1"));
        System.out.println(findSecondBiggerInt(List.of(1, 2, 3, 4, 5)));
    }

    public static List<String> deleteAllRepeat(List<String> list) {
        if (list != null)
            return list.stream()
                    .collect(Collectors.groupingBy(Function.identity()))
                    .entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .toList();
        return null;
    }

    public static int countStringsWithLetterStarts(List<String> list, String letter) {
        if (list != null && letter != null)
            return list.stream().filter(e -> e.startsWith(letter)).toList().size();
        return -1;
    }

    public static int findSecondBiggerInt(List<Integer> list) {
        if (list != null)
            return list.stream()
                    .sorted(Comparator.reverseOrder())
                    .skip(1)
                    .findFirst()
                    .get();
        return -1;
    }
}

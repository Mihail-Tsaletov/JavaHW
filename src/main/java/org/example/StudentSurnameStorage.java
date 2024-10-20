package org.example;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreated(Long id, String surname) {
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname) {
        surnamesTreeMap.get(surname).remove(id); // из множества id удаляем нужный
    }

    public void studentUpdate(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);
    }

    /**
     * Данный метод возвращает уникальные иденьтификаторы студентов,
     * чьи фамилии меньше либо равны переданной
     *
     * @return
     */
    public Set<Long> getStudentsBySurnamesLessOrEqualThen(String surname) {
        Set<Long> res = surnamesTreeMap.headMap(surname, true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream())
                .collect(Collectors.toSet());

        return res;
    }

    public Set<Long> getStudentsBySurname(String surname) {
        return surnamesTreeMap.get(surname);

    }

    public Set<Long> getStudentsBySurnamesBetween(String[] surnames) {
        String firstSurname = surnames[0];
        String secondSurname = surnames[1];

        return surnamesTreeMap.subMap(firstSurname, true, secondSurname, true)
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Set<Long> getAllSurnames(){
       return surnamesTreeMap
               .values()
               .stream()
               .flatMap(Collection::stream)
               .collect(Collectors.toSet());
    }
}

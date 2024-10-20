package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentStorage {
    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private Long currentId = 0L;

    /**
     * Создание данных о студенте
     *
     * @param student данные студента
     * @return сгенерированный уникальный идентификатор студента
     */

    public Long createStudent(Student student) {
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreated(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Обновление данных о студенте
     *
     * @param id      идентификатор студента
     * @param student данные студента
     * @return true если данные были обновлены, false если студент небыл найден
     */


    public boolean updateStudent(Long id, Student student) {
        if (!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            String newStudentSurname = student.getSurname();
            String oldStudentSurname = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdate(id, oldStudentSurname, newStudentSurname);
            studentStorageMap.put(id, student);
            return true;
        }
    }

    /**
     * Удаление данных о студенте
     *
     * @param id идентификатор студента
     * @return true если студент удален, false если студент небыл найден
     */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if (removed != null) {
            String surname = removed.getSurname();
            studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;
    }

    public boolean search(String data) {
        try {
            if (!data.equals("")) {
                String[] surnames = data.split(",");
                if (surnames.length <= 2) {

                    if (surnames.length == 1) {
                        Set<Long> students = studentSurnameStorage.getStudentsBySurname(surnames[0]);
                        for (Long studentsID : students) {
                            System.out.println(studentStorageMap.get(studentsID));
                        }
                        return true;
                    }

                    Set<Long> twoSurnameSearchId = studentSurnameStorage.getStudentsBySurnamesBetween(surnames);
                    for (Long studentsID : twoSurnameSearchId) {
                        System.out.println(studentStorageMap.get(studentsID));
                    }
                    return true;
                }
            } else {
                Set<Long> allSurnames = studentSurnameStorage.getAllSurnames();
                for (Long studentsID : allSurnames) {
                    System.out.println(studentStorageMap.get(studentsID));
                }
                return true;
            }

        } catch (Exception e){
            System.out.println("Студентов с такой фамилией нет " + e.getMessage());
            return false;
        }
        return false;
    }

    public Long getNextId() {
        currentId = currentId + 1;
        return currentId;
    }

    public void printAll() {
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> data) {
        data.entrySet().forEach(e -> {
            System.out.println(e.getKey() + " - " + e.getValue());
        });
    }

    public Map<String, Long> getCountByCourse() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        Student::getCourse, //student -> student.getCourse()
                        student -> 1L,
                        Long::sum
                ));
        return res;
    }

    public Map<String, Long> getCountByCity() {
        return studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        Student::getCity,
                        student -> 1L,
                        Long::sum
                ));
    }
}
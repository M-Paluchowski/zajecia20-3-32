package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollectorsExamples {

    private static List<Person> defaultPersons = List.of(new Person(1L, "Jan", "Kowalski", 56),
            new Person(2L, "Katarzyna", "Kowal", 17), new Person(3L, "Bartosz", "Nowak", 18),
            new Person(4L, "Piotr", "Kowalski", 16));

    @Test
    void max() {
        Optional<Person> max = defaultPersons.stream()
                .max(Comparator.comparingInt(Person::getAge));

        max.ifPresent(System.out::println);
    }

    @Test
    void min() {
        Optional<Person> min = defaultPersons.stream()
                .min(Comparator.comparingInt(Person::getAge));

        min.ifPresent(System.out::println);
    }

    @Test
    void reduceFirst() {
        Optional<Integer> ageSum = defaultPersons.stream()
                .map(Person::getAge)
                .reduce((first, second) -> first + second);

        ageSum.ifPresent(System.out::println);
    }

    @Test
    void reduceSecond() {
        Integer reduce = defaultPersons.stream()
                .map(Person::getAge)
                //10 -> 10 + wiek pierwszej osoby -> 26 + wiek drugiej osoby ->
                .reduce(10, (ageOne, ageTwo) -> ageOne + ageTwo);

        System.out.println(reduce);
    }

    @Test
    void reduceThird() {
        // (1 op 2) op 3 op (4 op 5 op 6)
        // (1 op 2) op (3 op 4 op 5) op 6
        // 1 op (2 op 3) op (4 op 5) op 6
        // (10_000 / 100 / 10) / (5 / 2 / 1)
        Stream.of(10_000, 100, 10, 5, 2, 1)
                .parallel()
                .reduce((first, second) -> first / second)
                .ifPresent(System.out::println);
    }

    @Test
    void toCollection() {
        LinkedList<Person> collect = defaultPersons.stream()
                .collect(Collectors.toCollection(() -> new LinkedList<>()));

        System.out.println(collect);
    }

    @Test
    void collect() {
        //To jest własna implementacja Collectors.toList()
        List<Person> collect = defaultPersons.stream()
                .collect(() -> new ArrayList<>(), (list, person) -> list.add(person), (mainList, secondaryList) -> mainList.addAll(secondaryList));

        System.out.println(collect);
    }

    @Test
    void toMapFirst() {
        Map<Long, String> collect = defaultPersons.stream()
                .collect(Collectors.toMap(person -> person.getId(), person -> person.getFirstName()));

        System.out.println(collect);
    }

    @Test
    void toMapSecond() {
        Map<String, Integer> collect = defaultPersons.stream()
                .collect(Collectors.toMap(person -> person.getLastName(), person -> person.getAge(), (first, second) -> second));

        System.out.println(collect);
    }

    @Test
    void groupingBy() {
        Map<String, List<Person>> collect = defaultPersons.stream()
                .collect(Collectors.groupingBy(person -> person.getLastName()));

        System.out.println(collect);
    }

    @Test
    void partitioningBy() {
        Map<Boolean, List<Person>> collect = defaultPersons.stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() >= 18));

        System.out.println(collect);
    }

    //joining
    //maxBy
    //minBy
    //reducing
    //collectingAndThen
    //counting
    //filtering
    //flatMapping
    //mapping
    //summarizingInt/Double/Long
    //averagingInt/Double/Long

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Person {
        private Long id;
        private String firstName;
        private String lastName;
        private int age;
    }
}

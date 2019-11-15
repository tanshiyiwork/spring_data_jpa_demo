package com.github.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestStream {

    @Test
    public void test1() {
        ArrayList<String> arrayList = (ArrayList<String>)Arrays.asList("How are you", "Hello", "Tom", "Hi", "Hell", "Jerry")
                .stream()
                .filter(s -> s.startsWith("H"))
                .map(s -> s.substring(2))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        if (arrayList.size()>0){
            arrayList.forEach(n -> System.out.println(n));
            arrayList.forEach((String s) -> System.out.println("*" + s + "*"));
        }
    }
}

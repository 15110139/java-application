package com.main;
import java.util.ArrayList;
import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tinylog.Logger;


@SpringBootApplication
public class App {
    public static void main(String[] args) {
        Consumer<Integer> consumter = (n) -> {Logger.info(n);};
        ArrayList<Integer> number = new ArrayList<Integer>();
        number.add(1);
        number.add(2);
        number.add(3);
        number.add(4);
        number.forEach(consumter);
    }
}


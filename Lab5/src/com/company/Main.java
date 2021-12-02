package com.company;

import com.company.game.Field;
import com.company.game.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Field field = new Field();
//        System.out.println(field);
//        Field.Hole start = field.holes[0];
//        for (int i = 0; i < 10; i++) {
//            System.out.println(start);
//            start = start.prev;
//        }
//        start = field.holes[0];
//        for (int i = 0; i < 11; i++) {
//            System.out.println(start);
//            start = start.next;
//        }
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.next();
        Game game = new Game(difficulty);
        game.start();
    }
}

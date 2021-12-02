package com.company.player;

import com.company.game.Field;

import java.util.Scanner;

public class Human implements Player {
    @Override
    public Field move(Field field) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input hole number");
        int hole = scanner.nextInt();
        return field.move(hole);
    }
}

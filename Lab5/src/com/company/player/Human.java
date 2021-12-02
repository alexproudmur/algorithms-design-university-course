package com.company.player;

import com.company.game.Field;

import java.util.Scanner;

public class Human implements Player {
    public int count = 0;

    @Override
    public Field move(Field field) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input hole number");
        int hole = scanner.nextInt();
        return field.move(hole, true);
    }

    @Override
    public void calcCount(Field field) {
        count += field.maxCount;
    }

    @Override
    public int getCount() {
        return count;
    }
}

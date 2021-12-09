package com.company;

import com.company.game.Game;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Insert difficulty: Low, Medium, High");
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.next();
        Game game = new Game(difficulty);
        game.start();
    }
}

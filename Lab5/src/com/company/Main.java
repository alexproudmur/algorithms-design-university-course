package com.company;

import com.company.game.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.next();
        Game game = new Game(difficulty);
        game.start();
    }
}

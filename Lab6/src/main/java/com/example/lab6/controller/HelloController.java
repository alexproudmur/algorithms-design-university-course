package com.example.lab6.controller;

import com.example.lab6.HelloApplication;
import com.example.lab6.game.GameState;
import com.example.lab6.game.Strategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Random;

public class HelloController {
    private final Random randomGen = new Random();
    @FXML
    private Label aiCount;
    @FXML
    private Label humanCount;
    @FXML
    private Label currSum;
    @FXML
    private Label random;
    @FXML
    private Label moves;

    @FXML
    public void move(ActionEvent event) {
        Button button = (Button) event.getSource();
        String strategy = button.getText();
        GameState gameState = new GameState(Integer.parseInt(aiCount.getText()),
                Integer.parseInt(humanCount.getText()),
                Integer.parseInt(currSum.getText()));
        gameState.changeSum(Strategy.valueOf(strategy), Integer.parseInt(random.getText()));
        gameState.eval(false);
        aiCount.setText(String.valueOf(gameState.aiCount));
        humanCount.setText(String.valueOf(gameState.humanCount));
        currSum.setText(String.valueOf(gameState.currSum));
        moves.setText(String.valueOf(Integer.parseInt(moves.getText()) + 1));
    }

    @FXML
    public void generate() {
        random.setText(String.valueOf(randomGen.nextInt(10) + 3));
    }

}
package com.example.lab6;

import com.example.lab6.algo.GameTree;
import com.example.lab6.algo.Minimax;
import com.example.lab6.controller.HelloController;
import com.example.lab6.game.GameState;
import com.example.lab6.game.Strategy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 810, 240);
        stage.setTitle("Lab6");
        stage.setScene(scene);
        stage.show();
        Label moves = (Label) scene.lookup("#moves");
        moves.textProperty().addListener((observableValue, s, t1) -> {
                    if (t1.equals("5")) {
                        for (int i = 0; i < 5; i++) {
                            generate(scene);
                            GameState gameState = getGameState(scene);
                            GameTree gameTree = new GameTree(gameState, 2);
                            Minimax minimax = new Minimax();
                            minimax.minimax(gameTree.root, true, 3);
                            Strategy strategy = minimax.getStrategy();
                            moveAi(scene, strategy);
                            moves.setText(String.valueOf(0));
                        }
                    }
        }
        );
    }

    public void moveAi(Scene scene, Strategy strategy) {

        Label aiCount = (Label) scene.lookup("#aiCount");
        Label humanCount = (Label) scene.lookup("#humanCount");
        Label currSum = (Label) scene.lookup("#currSum");
        Label random = (Label) scene.lookup("#random");

        GameState gameState = new GameState(Integer.parseInt(aiCount.getText()),
                Integer.parseInt(humanCount.getText()),
                Integer.parseInt(currSum.getText()));
        gameState.changeSum(strategy, Integer.parseInt(random.getText()));
        gameState.eval(true);

        aiCount.setText(String.valueOf(gameState.aiCount));
        humanCount.setText(String.valueOf(gameState.humanCount));
        currSum.setText(String.valueOf(gameState.currSum));
    }

    public GameState getGameState(Scene scene) {
        Label aiCount = (Label) scene.lookup("#aiCount");
        Label humanCount = (Label) scene.lookup("#humanCount");
        Label currSum = (Label) scene.lookup("#currSum");
        return new GameState(Integer.parseInt(aiCount.getText()),
                Integer.parseInt(humanCount.getText()),
                Integer.parseInt(currSum.getText()));
    }

    public void generate(Scene scene) {
        Label random = (Label) scene.lookup("#random");
        random.setText(String.valueOf(new Random().nextInt(10) + 3));
    }

    public static void main(String[] args) {
        launch();
    }
}
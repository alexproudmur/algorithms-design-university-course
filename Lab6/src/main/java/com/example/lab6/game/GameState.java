package com.example.lab6.game;

public class GameState {
    public int aiCount;
    public int humanCount;
    public int currSum;

    public GameState(int aiCount, int humanCount, int currSum) {
        this.aiCount = aiCount;
        this.humanCount = humanCount;
        this.currSum = currSum;
    }

    public void eval(boolean ai) {
        if (currSum / 13 > 0) {
            int delta = currSum % 13;
            if (ai) {
                aiCount += currSum / 13;
            } else {
                humanCount += currSum / 13;
            }
            currSum = delta;
        }
    }

    public void changeSum(Strategy strategy, int addition) {
        switch (strategy) {
            case X2P:
                currSum += addition * 2;
                break;
            case X3P:
                currSum += addition * 3;
                break;
            case X4P:
                currSum += addition * 4;
                break;
            case DEL2P:
                currSum += addition / 2;
                break;
            case DEL3P:
                currSum += addition / 3;
                break;
            case DEL4P:
                currSum += addition / 4;
                break;

            case X2M:
                currSum -= addition * 2;
                break;
            case X3M:
                currSum -= addition * 3;
                break;
            case X4M:
                currSum -= addition * 4;
                break;
            case DEL2M:
                currSum -= addition / 2;
                break;
            case DEL3M:
                currSum -= addition / 3;
                break;
            case DEL4M:
                currSum -= addition / 4;
                break;
        }
    }
}

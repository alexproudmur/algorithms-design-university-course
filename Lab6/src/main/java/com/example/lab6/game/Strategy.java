package com.example.lab6.game;

public enum Strategy {
    X2P("+X2"), X3P("+X3"), X4P("+X4"), DEL2P("+DEL2"), DEL3P("+DEL3"), DEL4P("+DEL4"),
    X2M("-X2"), X3M("-X3"), X4M("-X4"), DEL2M("-DEL2"), DEL3M("-DEL3"), DEL4M("-DEL4");

    private String value;

    Strategy(String strategy) {
        this.value = strategy;
    }

    public String getValue() {
        return value;
    }
}

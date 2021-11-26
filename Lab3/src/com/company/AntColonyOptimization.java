package com.company;

import java.util.*;
import java.util.stream.IntStream;

@SuppressWarnings("all")
public class AntColonyOptimization {

    private final double c = 1.0;
    private final double alpha = 1.25;
    private final double beta = 1.45;
    private final double evaporation = 0.55;
    private final double Q = 500;
    private final double randomFactor = 0.01;

    private final int iterations = 1;

    private final int numberOfCities = 5;
    private final int numberOfAnts = 3;

    private final double[][] graph;
    private final double[][] trails;
    private final List<Ant> ants = new ArrayList<>();
    private final Random random = new Random();
    private final double[] probabilities;

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    public static void main(String[] args) {
        AntColonyOptimization aco = new AntColonyOptimization();
        aco.startAntColonyOptimization();
    }

    public AntColonyOptimization() {
        graph = new double[][] {
                {0.0, 1.0, 15.0, 21.0, 5.0},
                {19.0, 0.0, 29.0, 2.0, 9.0},
                {11.0, 6.0, 0.0, 27.0, 4.0},
                {25.0, 10.0, 13.0, 0.0, 23.0},
                {7.0, 17.0, 8.0, 3.0, 0.0}
        };

        trails = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new Ant(numberOfCities)));
    }

    public double[][] generateRandomMatrix(int n) {
        double[][] randomMatrix = new double[n][n];
        IntStream.range(0, n)
                .forEach(i -> IntStream.range(0, n)
                        .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(50) + 5)));
        return randomMatrix;
    }

    public void startAntColonyOptimization() {
        IntStream.rangeClosed(1, 2)
                .forEach(i -> {
                    System.out.println("Attempt №" + i);
                    System.out.println(Arrays.toString(probabilities));
                    solve();
                });
    }

    public int[] solve() {
        setupAnts();
        clearTrails();
        IntStream.range(0, iterations)
                .forEach(i -> {
                    moveAnts();
                    updateTrails();
                    updateBest();
                });
        System.out.println("Best tour length: " + (bestTourLength - numberOfCities));
        System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));
        return bestTourOrder.clone();
    }

    private void setupAnts() {
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.forEach(ant -> {
                    ant.clear();
                    ant.visitCity(-1, random.nextInt(numberOfCities));
                }));
        currentIndex = 0;
    }

    private void moveAnts() {
        IntStream.range(currentIndex, numberOfCities - 1)
                .forEach(i -> {
                    ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
                    currentIndex++;
                });
    }

    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                    .filter(i -> i == t && !ant.visited(i))
                    .findFirst();
            if (cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }
        throw new RuntimeException("No other cities");
    }

    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
            }
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    private void updateTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(graph);
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }

    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0)
                    .trailLength(graph);
        }
        for (Ant a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
    }

    private void clearTrails() {
        IntStream.range(0, numberOfCities)
                .forEach(i -> {
                    IntStream.range(0, numberOfCities)
                            .forEach(j -> trails[i][j] = c);
                });
    }

}

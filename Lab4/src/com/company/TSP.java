package com.company;

import java.util.Random;

@SuppressWarnings("all")
public class TSP {
    public static int maxGenerations = 10000;
    public static int numCities = 300;

    public static void main(String[] args) {
        City[] cities = new City[numCities];

        generateGraph();
        //printGraph();
        for (int cityIndex = 0; cityIndex < numCities; cityIndex++) {
            cities[cityIndex] = new City(cityIndex);
        }


        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.9, 2, 5);
        Population population = ga.initPopulation(cities.length);
        ga.evalPopulation(population, cities);

        Route startRoute = new Route(population.getFittest(0), cities);
        System.out.println("Start Distance: " + startRoute.getDistance());

        int generation = 1;

        while (generation < maxGenerations) {

            Route route = new Route(population.getFittest(0), cities);
            if (generation % 1000 == 0) System.out.println("Generation "+generation+" distance: " + route.getDistance());

            population = ga.crossoverPopulation(population);
            population = ga.mutatePopulation(population);
            ga.evalPopulation(population, cities);

            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations.");
        Route route = new Route(population.getFittest(0), cities);
        System.out.println("Best distance: " + route.getDistance());

    }

    private static void generateGraph() {
        Random random = new Random();
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i == j) {
                    City.graph[i][j] = 0;
                }
                else if (City.graph[i][j] == 0) {
                    int num = random.nextInt(145) + 5;
                    City.graph[i][j] = num;
                    if (random.nextBoolean()) {
                        City.graph[j][i] = num;
                    } else {
                        City.graph[j][i] = random.nextInt(145) + 5;
                    }
                }
            }
        }
    }

    private static void printGraph() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                System.out.print(City.graph[i][j] + " ");
            }
            System.out.println();
        }
    }
}

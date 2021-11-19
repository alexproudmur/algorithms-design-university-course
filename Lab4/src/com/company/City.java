package com.company;

@SuppressWarnings("all")
public class City {

	public static final int[][] graph = new int[TSP.numCities][TSP.numCities];

	private final int num;

	public City(int num) {
		this.num = num;
	}


	public double distanceFrom(City city) {
		return graph[getNum()][city.getNum()];
	}

	public int getNum() {
		return num;
	}
}
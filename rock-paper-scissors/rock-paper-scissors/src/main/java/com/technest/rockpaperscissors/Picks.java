package com.technest.rockpaperscissors;

public enum Picks {
	ROCK(0),
	PAPER(1),
	SCISSORS(2);
	
	private final int value;
	
	
	
	private Picks (int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}

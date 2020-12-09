package com.technest.rockpaperscissors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RockPaperScissorsApplication {

	static Integer p1Wins;
	static Integer p2Wins;
	static Integer draws;
	
	static RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(RockPaperScissorsApplication.class, args);

		String gameMode = "1";
		String gameOutput = "1";

		gameMode = pickMode(gameMode);	
		gameOutput = pickOutput(gameOutput);
		resetCounter();

		playGame(gameMode, gameOutput);
	}
	
	
	private static void playGame(String mode, String output) throws IOException {
		
		Player p1 = new Player();
		Player p2 = new Player();
		
		if(mode.equals("1")) {
			p1.randomizePicks();
			p2.randomizePicks();
			
			CalculateGame(p1,p2, output);
		}
		
		if(mode.equals("2")) {
			p1.randomizePicks();
			p2.rockPicks();
			
			CalculateGame(p1,p2, output);
		}
		
		if(mode.equals("3")) {
			p1.randomizePicks();
			p2 = p2OnlineRequest();		
			
			CalculateGame(p1,p2, output);
		}
		
		
	}
	
	private static void resetCounter() {
		p1Wins = p2Wins = draws = 0;
	}
	
	
	private static String pickMode(String gameMode) throws IOException {

		do {
			System.out.println("\n\n\n\n");
			System.out.println("Pick a game mode:\n"
					+ "1:Fair\n"
					+ "2:Unfair\n"
					+ "3:Online\n");
			
			BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
			gameMode = reader.readLine();

			switch(gameMode) {
			case "1":
				System.out.println("You picked fair mode.");
				break;
			case "2":
				System.out.println("You picked unfair mode.");
				break;
			case "3":
				System.out.println("You picked online mode.");
				break;
			default:
				System.out.println("You have to pick game mode 1, 2 or 3.");
			}
		}while(!gameMode.equals("1") && !gameMode.equals("2") && !gameMode.equals("3"));
		
		return gameMode;
	}
	
	private static String pickOutput(String gameOutput) throws IOException {
		do {

			System.out.println("\nPick the output mode:\n"
					+ "1:Console\n"
					+ "2:File\n");
			
			BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
			gameOutput = reader.readLine();

			switch(gameOutput) {
			case "1":
				System.out.println("You picked console output.");
				break;
			case "2":
				System.out.println("You picked file output.");
				break;
			default:
				System.out.println("You have to pick output mode 1 or 2");
			}
			resetCounter();
		}
		while(!gameOutput.equals("1") && !gameOutput.equals("2"));
		return gameOutput;
	}
	
	private static void CalculateGame(Player p1, Player p2, String output) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter("Results.txt"));
		String result = "";

		for (int i = 0; i < 10; i++) {

			if(p1.getPicks().get(i) == p2.getPicks().get(i)) {
				draws++;

				result = "Both players picked ";
				
				if(p1.getPicks().get(i) == Picks.ROCK.getValue()) {
					result = result+ "rock";
				}
				if(p1.getPicks().get(i) == Picks.PAPER.getValue()) {
					result = result+ "paper";
				}
				if(p1.getPicks().get(i) == Picks.SCISSORS.getValue()) {
					result = result+ "scissors";
				}
				
				result = result +" its a draw";
				writeOutput(output, result, writer);
				
				continue;
			}
			if(p1.getPicks().get(i) == Picks.ROCK.getValue()) {
				if(p2.getPicks().get(i).equals(Picks.SCISSORS.getValue())) {
					result = "Player 1 picked rock, player 2 picked scissors, player 1 wins";
					writeOutput(output, result, writer);

					p1Wins++;
				}else {
					result = "Player 1 picked rock, player 2 picked paper, player 2 wins";
					writeOutput(output, result, writer);
					p2Wins++;
				}
				continue;
			}
			if(p1.getPicks().get(i) == Picks.PAPER.getValue()) {
				if(p2.getPicks().get(i).equals(Picks.ROCK.getValue())) {
					result = "Player 1 picked paper, player 2 picked rock, player 1 wins";
					writeOutput(output, result, writer);
					p1Wins++;
				}else {
					result = "Player 1 picked paper, player 2 picked scissors, player 2 wins";
					writeOutput(output, result, writer);
					p2Wins++;
				}
				continue;
			}
			if(p1.getPicks().get(i) == Picks.SCISSORS.getValue()) {
				if(p2.getPicks().get(i).equals(Picks.PAPER.getValue())) {
					result = "Player 1 picked scissors, player 2 picked paper, player 1 wins";
					writeOutput(output, result, writer);
					p1Wins++;
				}else {
					result = "Player 1 picked scissors, player 2 picked rock, player 2 wins";
					writeOutput(output, result, writer);
					p2Wins++;
				}
				continue;
			}
		}
		
		result = "Game ended. Results:"
				+ "\n Player1 wins: "+ p1Wins
				+ "\n Player2 wins: "+ p2Wins
				+ "\n Draws: "+ draws;
		
		writeOutput(output, result, writer);

		
		writer.close();
		
	}
	
	
	private static Player p2OnlineRequest() {
		String url = "http://localhost:8080/random";
		return restTemplate.getForObject(url, Player.class);
	}
	
	private static void writeOutput(String output, String result, BufferedWriter writer) throws IOException {
		if(output.equals("1")) {
			System.out.println(result);
		}else {
			writer.append("\n"+result);
		}
	}
}

package com.technest.rockpaperscissors;

import java.util.ArrayList;
import java.util.Random;

public class Player {

		public Player() {
			
		};
	
		private ArrayList<Integer> picks;

		public ArrayList<Integer> getPicks() {
			return picks;
		}

		private void setPicks(ArrayList<Integer> picks) {
			this.picks = picks;
		}

		public void randomizePicks() {
			Random random = new Random();
			ArrayList<Integer> randomPicks = new ArrayList<Integer>();
			
			for (int i = 0; i < 10; i++) {
				randomPicks.add(random.nextInt(3));
			}
			
			setPicks(randomPicks);
		}
		public void rockPicks() {
			ArrayList<Integer> rockPicks= new ArrayList<Integer>();
			for (int i = 0; i < 10; i++) {
				rockPicks.add(Picks.ROCK.getValue());
			}
			setPicks(rockPicks);
		}
		
		
}

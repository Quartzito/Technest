package com.technest.rockpaperscissors;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController{


	@GetMapping("/random")
	public Player Player2Picks( String name) {
		
		Player player2 = new Player();
		player2.randomizePicks();
		
		return player2;
	}
}
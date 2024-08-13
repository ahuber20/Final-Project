package library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import library.controller.model.GameData;
import library.controller.model.ReviewData;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/library")
@Slf4j
public class LibraryController {
	@Autowired
	private LibraryService libraryService;

	@PostMapping("/game")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GameData insertGame(
			@RequestBody GameData gameData) {
		log.info("Creating game {}", gameData);
		return libraryService.saveGame(gameData);
	}
	
	@PutMapping("/game/{gameId}")
	public GameData updateGame(@PathVariable Long gameId, 
			@RequestBody GameData gameData) {
		gameData.setGameId(gameId);
		log.info("Updating game {}", gameData);
		return libraryService.saveGame(gameData);
	}
	
	
	@GetMapping("/game")
	public List<GameData> retrieveAllGames() {
		log.info("Retrieve all games called.");
		return libraryService.retrieveAllGames();
	}
	
	@GetMapping("/game/{gameId}")
	public GameData retrieveGameById(@PathVariable Long gameId) {
		log.info("Retrieving game with ID={}", gameId);
		return libraryService.retrieveGameById(gameId);
	}
	
	@DeleteMapping("/game")
	public void deleteAllGames() {
		log.info("Attempting to delete all games");
		throw new UnsupportedOperationException("Deleting all games is prohibited.");
	}
	
	@DeleteMapping("/game/{gameId}")
	public Map<String, String> deleteGameById(@PathVariable Long gameId) {
		log.info("Deleting game with ID={}", gameId);
		
		libraryService.deleteGameById(gameId);
		return Map.of("message", "Deletion of game with ID=" + gameId + " was successful.");
	}
	
	@PostMapping("/game/{gameId}/review")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ReviewData insertReview(@PathVariable Long gameId, @RequestBody ReviewData reviewData) {
		log.info("Creating review {} for game with ID={}", reviewData, gameId);
		
		return libraryService.saveReview(gameId, reviewData);
	}
	
	@PutMapping("/game/{gameId}/review/{reviewId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ReviewData updateReview(@PathVariable Long gameId,
			@PathVariable Long reviewId,
			@RequestBody ReviewData reviewData) {
		
		reviewData.setReviewId(reviewId);
		
		log.info("Updating review {} for game with ID={}", reviewData, gameId);
		
		return libraryService.saveReview(gameId, reviewData);
	}
	
	@GetMapping("/game/{gameId}/review/{reviewId}")
	public ReviewData rertievedReviewById(@PathVariable Long gameId, @PathVariable Long reviewId) {
		log.info("Retrieving review with ID={} for game with ID={}", reviewId, gameId);
		return libraryService.retrieveReviewById(gameId, reviewId);
	}
}

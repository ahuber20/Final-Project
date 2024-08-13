package library.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.controller.model.GameData;
import library.controller.model.ReviewData;
import library.dao.GameDao;
import library.dao.AppraisalDao;
import library.dao.ReviewDao;
import library.entity.Game;
import library.entity.Appraisal;
import library.entity.Review;
import lombok.Data;

@Service
@Data
public class LibraryService {

	@Autowired
	private GameDao gameDao;

	@Autowired
	private AppraisalDao appraisalDao;

	@Autowired
	private ReviewDao reviewDao;

	@Transactional(readOnly = false)
	public GameData saveGame(GameData gameData) {
		Long gameId = gameData.getGameId();
		Game game = findOrCreateGame(gameId, gameData.getGameName());

		setFieldsInGame(game, gameData);
		return new GameData(gameDao.save(game));
	}

	private void setFieldsInGame(Game game, GameData gameData) {
		game.setGameDeveloper(gameData.getGameDeveloper());
		game.setGameName(gameData.getGameName());
	}

	private Game findOrCreateGame(Long gameId, String gameName) {
		Game game;

		if (Objects.isNull(gameId)) {
			Optional<Game> opGame = gameDao.findByGameName(gameName);

			if (opGame.isPresent()) {
				throw new DuplicateKeyException("Game with name " + gameName + " is already in the library.");
			}

			game = new Game();
		} else {
			game = findGameById(gameId);
		}

		return game;
	}

	private Game findGameById(Long gameId) {
		return gameDao.findById(gameId).orElseThrow(
				() -> new NoSuchElementException("Game with ID=" + gameId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<GameData> retrieveAllGames() {

		//@formatter:off
		return gameDao.findAll()
			.stream()
			.map(GameData::new)
			.toList();
		//@formatter:on
	}

	@Transactional(readOnly = true)
	public GameData retrieveGameById(Long gameId) {
		Game game = findGameById(gameId);
		return new GameData(game);
	}

	@Transactional(readOnly = false)
	public void deleteGameById(Long gameId) {
		Game game = findGameById(gameId);
		gameDao.delete(game);
	}

	@Transactional(readOnly = false)
	public ReviewData saveReview(Long gameId, ReviewData reviewData) {
		Game game = findGameById(gameId);

		Set<Appraisal> appraisals = appraisalDao.findAllByAppraisalIn(reviewData.getAppraisals());

		Review review = findOrCreateReview(reviewData.getReviewId());
		setReviewFields(review, reviewData);
		
		review.setGame(game);
		game.getReviews().add(review);
		
		for(Appraisal appraisal : appraisals) {
			appraisal.getReviews().add(review);
			review.getAppraisals().add(appraisal);
		}
		
		Review dbReview = reviewDao.save(review);
		return new ReviewData(dbReview);
	}

	private void setReviewFields(Review review, ReviewData reviewData) {
		review.setReviewDetails(reviewData.getReviewDetails());
		review.setReviewerName(reviewData.getReviewerName());
	}

	private Review findOrCreateReview(Long reviewId) {
		Review review;

		if (Objects.isNull(reviewId)) {
			review = new Review();
		} else {
			review = findReviewById(reviewId);
		}

		return review;
	}

	private Review findReviewById(Long reviewId) {
		return reviewDao.findById(reviewId)
				.orElseThrow(() -> new NoSuchElementException(
				"Review with ID=" + reviewId + " does not exist."));
	}

	@Transactional(readOnly = true)
	public ReviewData retrieveReviewById(Long gameId, Long reviewId) {
		findGameById(gameId);
		Review review = findReviewById(reviewId);
		
		if(review.getGame().getGameId() != gameId) {
			throw new IllegalStateException("Review with ID=" + reviewId + " is not written for the game with ID=" + gameId);
		}
		
		return new ReviewData(review);
	}

}

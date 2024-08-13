package library.controller.model;

import java.util.HashSet;
import java.util.Set;

import library.entity.Game;
import library.entity.Appraisal;
import library.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewData {
	private Long reviewId;
	  
	  private String reviewerName;
	  private String reviewDetails;
	  private ReviewedGame game;
	  private Set<String> appraisals = new HashSet<>();
	  
	  public ReviewData(Review review) {
		  reviewId = review.getReviewId();
		  reviewerName = review.getReviewerName();
		  reviewDetails = review.getReviewDetails();
		  game = new ReviewedGame(review.getGame());
		  
		  for(Appraisal appraisal : review.getAppraisals()) {
			  appraisals.add(appraisal.getAppraisal());
		  }
	  }
	  
	  @Data
	  @NoArgsConstructor
	  public static class ReviewedGame {
		  private Long gameId;
		  private String gameName;
		  private String gameDeveloper;
		  
		  public ReviewedGame(Game game) {
			  gameId = game.getGameId();
			  gameName = game.getGameName();
			  gameDeveloper = game.getGameDeveloper();
		  }
	  }
}
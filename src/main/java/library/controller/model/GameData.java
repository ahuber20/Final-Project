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
public class GameData {

		private Long gameId;
		private String gameName;
		private String gameDeveloper;
		private Set<ReviewResponse> reviews = new HashSet<>();
		
		public GameData(Game game) {
			gameId = game.getGameId();
			gameName = game.getGameName();
			gameDeveloper = game.getGameDeveloper();
			
			
			for(Review review : game.getReviews()) {
				reviews.add(new ReviewResponse(review));
			}
		}
		
		@Data
		@NoArgsConstructor
		static class ReviewResponse {
			private Long reviewId;
			private String reviewerName;
			private String reviewDetails;
			private Set<String> appraisals = new HashSet<>();
			
			public ReviewResponse(Review review) {
				reviewId = review.getReviewId();
				reviewerName = review.getReviewerName();
				reviewDetails = review.getReviewDetails();
				
				for(Appraisal appraisal : review.getAppraisals()) {
					appraisals.add(appraisal.getAppraisal());
				}
				
			}
		}
	}
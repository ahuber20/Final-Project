package library.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import library.entity.Game;


public interface GameDao extends JpaRepository<Game, Long> {

	Optional<Game> findByGameName(String gameName);
		
}

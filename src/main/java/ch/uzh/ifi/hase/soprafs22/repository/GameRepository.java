package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT game FROM Game game where game.gameCode=?1 and game.active=true")
    Optional<Game> findByGameCode(UUID gameCode);

}
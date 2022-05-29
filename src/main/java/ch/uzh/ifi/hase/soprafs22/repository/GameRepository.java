package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository("gameRepository")
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT game FROM Game game where game.gameCode=?1")
    Optional<Game> findByGameCode(UUID gameCode);

    @Query("select game from Game game where game.active = true and game.user2Id=NULL")
    Game findActiveGame();

    @Query("select game from Game game where (game.user1Id = ?1 or game.user2Id=?1 ) and (game.user1Joined=true or game.user2Joined=true)")
    List<Game> findGameByUserId(Long userId);

    @Query("SELECT game  FROM Game game WHERE game.active=false AND game.winner <> NULL ORDER BY game.id DESC")
    Game checkForLatestWinner();

    @Query("SELECT game  FROM Game game WHERE game.active=true and game.user1Id <> NULL and game.user2Id <> NULL")
    List<Game> checkEmptyLobby();
}

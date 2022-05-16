package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    @Autowired
    private GameRepository  gameRepository;

    public Game createGame(Game game) throws Exception {
        if (game.getUser1Id()==null)
            throw new Exception("Please mention user id");
        if (game.getUser1Joined()==null)
            throw new Exception("Please mention user id");
        game.setUser1Score(0L);
        game.setUser1Id(game.getUser1Id());
        game.setGameCode(UUID.randomUUID());
        game.setActive(false);
        return gameRepository.save(game);
    }

    public Game joinGame(Game game) throws Exception {
        if (game.getGameCode()==null) {
            throw new Exception("Please mention Game Code");
        }
        Game existingGame = gameRepository.findByGameCode(game.getGameCode()).orElseThrow(()-> new Exception("Game not found with  game code: "+game.getGameCode()));
        if (game.getUser2Id()==null) {
            throw new Exception("Please mention user2 id");
        }
        existingGame.setGameStartTime(new Date());
        existingGame.setUser2Score(0L);
        existingGame.setUser2Id(game.getUser2Id());
        existingGame.setUser2Joined(true);
        existingGame.setActive(true);
        return gameRepository.save(existingGame);
    }

    public Game quit(UUID gameCode, Long userId) throws Exception {
        if (gameCode==null) {
            throw new Exception("Please mention Game Code");
        }
        Game existingGame = gameRepository.findByGameCode(gameCode).orElseThrow(()-> new Exception("Game not found with  game code: "+gameCode));
        if (userId==null) {
            throw new Exception("Please mention user id to quit the game");
        }
        if (existingGame.getUser1Id()!= userId)
            existingGame.setWinner(existingGame.getUser1Id());
        if (existingGame.getUser2Id()!= userId)
            existingGame.setWinner(existingGame.getUser2Id());
        existingGame.setActive(false);
        return gameRepository.save(existingGame);
    }

    public Game findGameByGameCode(UUID gameCode) throws Exception {
        if (gameCode==null) {
            throw new Exception("Please mention Game Code");
        }
        return gameRepository.findByGameCode(gameCode).orElseThrow(()-> new Exception("Game not found with  game code: "+gameCode));
    }

    public Game updateScore(GameDTO gameDto) throws Exception {
        if (gameDto.getGameCode()==null)
            throw new Exception("Please mention Game Code");
        if (gameDto.getUserId()==null)
            throw new Exception("Please mention User id");
        if (gameDto.getScore1()==null && gameDto.getScore2()==null)
            throw new Exception("Please mention Some Score");
        Game existingGame = gameRepository.findByGameCode(gameDto.getGameCode()).orElseThrow(()-> new Exception("Game not found with  game code: "+gameDto.getGameCode()));
        if (gameDto.getScore1()!=null)
            existingGame.setUser1Score(existingGame.getUser1Score()+1);
        if (gameDto.getScore2()!=null)
            existingGame.setUser2Score(existingGame.getUser2Score()+1);
        return gameRepository.save(existingGame);
    }


    public List<Game> findAllGames() throws Exception {
        return gameRepository.findAll();
    }
}
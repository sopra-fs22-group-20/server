package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.repository.GameRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    @Autowired
    private GameRepository  gameRepository;
    @Autowired
    private UserService userService;

    public Game createGame(Game game) throws Exception {
        //now create a new game
        if (game.getUser1Id()==null)
            throw new Exception("Please mention user id");
        if (game.getUser1Joined()==null)
            throw new Exception("Please mention user id");
        game.setUser1Score(0L);
        game.setUser1Id(game.getUser1Id());
        game.setGameCode(UUID.randomUUID());
        game.setActive(true);
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
        setUserNames(existingGame);
        existingGame.setUser2Score(0L);
        existingGame.setUser2Id(game.getUser2Id());
        existingGame.setUser2Joined(true);
        existingGame.setActive(true);
        return gameRepository.save(existingGame);
    }

    private void setUserNames(Game existingGame) {
        if (existingGame.getUser1Id()!=null)
            existingGame.setUser1Name(userService.getUserByUserId(existingGame.getUser1Id()).getUsername());
        if (existingGame.getUser2Id()!=null)
            existingGame.setUser2Name(userService.getUserByUserId(existingGame.getUser2Id()).getUsername());
    }

    //to make auto rolback if any error occurs
    @Transactional
    public Game exitGame(UUID gameCode) throws Exception {
        Game findGame = gameRepository.findByGameCode(gameCode).orElseThrow(()-> new Exception("Game not found for Game code = "+gameCode));
        findGame.setActive(false);
        return gameRepository.save(findGame);
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
        setUserNames(existingGame);
        return gameRepository.save(existingGame);
    }

    public Game findGameByGameCode(UUID gameCode) throws Exception {
        if (gameCode==null) {
            throw new Exception("Please mention Game Code");
        }
        Game game = gameRepository.findByGameCode(gameCode).orElseThrow(()-> new Exception("Game not found with  game code: "+gameCode));
        setUserNames(game);
        return game;
    }

    public Game updateScore(GameDTO gameDto) throws Exception {
        try{
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
            setUserNames(existingGame);
            return gameRepository.save(existingGame);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Game> findAllGames() throws Exception {
        return gameRepository.findAll();
    }

    public Game findActiveGame() throws Exception {
        Game game= gameRepository.findActiveGame();
        if (game==null)
            throw new Exception("Game is not created yet, Please click on following button to create lobby first");
        setUserNames(game);
        return game;
    }

    public Game findGameByUserId(Long userId) throws Exception {
        List<Game> games= gameRepository.findGameByUserId(userId);
        if (games.isEmpty())
            throw new Exception("Game is not created yet, Please click on following button to create lobby first");
        if (games.get(0).getUser2Id()==null)
            throw new Exception("Lobby is ready to join you, please click on following button to join the Lobby");
        setUserNames(games.get(0));
        return games.get(0);
    }
    public Game checkForWinner() throws Exception {
        Game game= gameRepository.checkForLatestWinner();
        if (game==null)
            throw new Exception("NO WINNER EXISTS FOR NOW");
        setUserNames(game);
        return game;
    }
    public Game saveWinner(Long id, UUID gameCode) throws Exception {
        Game game= gameRepository.findByGameCode(gameCode).orElseThrow(()-> new Exception("Game not found with  game code: "+gameCode));
        game.setWinner(id);
        game.setActive(false);
        return game;
    }
    public List<Game> checkEmptyLobby() throws Exception {
        List<Game> games= gameRepository.checkEmptyLobby();
        for (Game game : games) {
            setUserNames(game);
        }
        return games;
    }
}

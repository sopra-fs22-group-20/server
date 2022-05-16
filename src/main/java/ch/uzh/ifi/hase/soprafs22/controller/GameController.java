package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameDTO;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping( "/game")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Endpoints in this controller
     * /create          POST
     * /joinGame        POST
     * /quit/{gameCode}/{userId} DELETE
     * /findGameByGameCode/{gameCode}      GET
     * /updateScore             PUT
     * /findAllGames            GET
     *
     */
    @PostMapping("/create")
    public Game createGame(@RequestBody Game game){
        try{
            return gameService.createGame(game);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    @PostMapping("/joinGame")
    public Game joinGame(@RequestBody Game game){
        try{
            return gameService.joinGame(game);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/quit/{gameCode}/{userId}")
    public Game quit(@PathVariable(name = "gameCode")UUID gameCode, @PathVariable(name = "userId") Long userId){
        try{
            return gameService.quit(gameCode, userId);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    @GetMapping("/findGameByGameCode/{gameCode}")
    public Game findGameByGameCode(@PathVariable(name = "gameCode")UUID gameCode){
        try{
            return gameService.findGameByGameCode(gameCode);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    @PutMapping("/updateScore")
    public Game updateScore(@RequestBody GameDTO gameDto){
        try{
            return gameService.updateScore(gameDto);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    @GetMapping("/findAllGames")
    public List<Game> findAllGames(){
        try{
            return gameService.findAllGames();
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }
}
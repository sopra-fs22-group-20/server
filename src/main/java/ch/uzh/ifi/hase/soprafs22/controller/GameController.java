package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.rest.dto.GameDTO;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<?> createGame(@RequestBody Game game){
        try{
            return ResponseEntity.ok(gameService.createGame(game));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/joinGame")
    public ResponseEntity<?> joinGame(@RequestBody Game game){
        try{
            return ResponseEntity.ok(gameService.joinGame(game));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/quit/{gameCode}/{userId}")
    public ResponseEntity<?> quit(@PathVariable(name = "gameCode")UUID gameCode, @PathVariable(name = "userId") Long userId){
        try{
            return ResponseEntity.ok(gameService.quit(gameCode, userId));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/exitGame/{gameCode}")
    public ResponseEntity<?> exitGame(@PathVariable(name = "gameCode")UUID gameCode){
        try{
            return ResponseEntity.ok(gameService.exitGame(gameCode));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findGameByGameCode/{gameCode}")
    public ResponseEntity<?> findGameByGameCode(@PathVariable(name = "gameCode")UUID gameCode){
        try{
            return ResponseEntity.ok(gameService.findGameByGameCode(gameCode));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateScore")
    public ResponseEntity<?> updateScore(@RequestBody GameDTO gameDto){
        try{
            return ResponseEntity.ok(gameService.updateScore(gameDto));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findAllGames")
    public ResponseEntity<?> findAllGames(){
        try{
            return ResponseEntity.ok(gameService.findAllGames());
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/findActiveGame")
    public ResponseEntity<?> findActiveGamme(){
        try{
            return ResponseEntity.ok(gameService.findActiveGame());
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/findGameByUserId/{user1Id}")
    public ResponseEntity<?> findGameByUserId(@PathVariable(name = "user1Id") Long user1Id){
        try{
            return ResponseEntity.ok(gameService.findGameByUserId(user1Id));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/checkForWinner")
    public ResponseEntity<?> checkForWinner(){
        try{
            return ResponseEntity.ok(gameService.checkForWinner());
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/saveWinner/{id}/{gameCode}")
    public ResponseEntity<?> saveWinner(@PathVariable(name = "id") Long id, @PathVariable(name = "gameCode") UUID gameCode){
        try{
            return ResponseEntity.ok(gameService.saveWinner(id, gameCode));
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/checkEmptyLobby")
    public ResponseEntity<?> checkEmptyLobby(){
        try{
            List<Game> allGames = gameService.checkEmptyLobby();
            if (allGames.size()>0)
                return ResponseEntity.status(IM_USED).body("Please create a new lobby for your partner");
            else
                return ResponseEntity.status(ALREADY_REPORTED).body("Ruff response");
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
}

package com.packt.football.controllers;

import com.packt.football.exceptions.AlreadyExistsException;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.models.Player;
import com.packt.football.services.FootballService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/players")
@RestController
public class PlayerController {
    private final FootballService footballService;

    public PlayerController(FootballService footballService) {
        this.footballService = footballService;
    }

    @GetMapping
    public List<Player> listPlayers() {
        return footballService.listPlayers();
    }

    @PostMapping
    public void createPlayer(@RequestBody Player player){
        footballService.addPlayer(player);
    }

    @GetMapping(value = "/{id}")
    public Player readPlayer(@PathVariable String id){
        return footballService.getPlayer(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePlayer(@PathVariable String id){
        footballService.deletePlayer(id);
    }

    @PutMapping(value = "/{id}")
    public void updatePlayer(@PathVariable String id, @RequestBody Player player){
        footballService.updatePlayer(player);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
    @ExceptionHandler(value = NotFoundException.class)
    public void notFoundHandler(){
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already Exists")
    @ExceptionHandler(value = AlreadyExistsException.class)
    public void alreadyExistsHandler(){

    }

    /*
    we can also manage errors in my api and using proper standard status codes by using response entity
    @GetMapping("/{id}")
    public ResponseEntity<Player> readPlayer(@PathVariable String id)
    {
        try {
            Player player = footballService.getPlayer(id);
             return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


        or use global exception handlers for consistent error-handling for all your
        RESTful endpoints in your application.

        @ControllerAdvice
        public class GlobalExceptionHandler {
            @ExceptionHandler(NotFoundException.class)
            public ResponseEntity<String> handleGlobalException(NotFoundException ex) {
                return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
}
}

      thirdly we can create a custom response class and using the builder pattern,
      we can build the appropriate response and return it right from the service class.
      the class can be a generic class with 3 fields:
      int status
      String message
      T response

      using a combination of ResponseEntity and exception handlers is a good approach to gain
      complete control of how to return response in both successfully and unsuccessful response cases.

     */
}



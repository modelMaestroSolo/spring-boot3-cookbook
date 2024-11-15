package com.packt.football.services;

import com.packt.football.exceptions.AlreadyExistsException;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.model.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootballService {
    private final Map<String, Player> players = new HashMap<>(
            Map.ofEntries(
            Map.entry("1884823", new Player("1884823", 5, "Ivana ANDRES", "Defender", LocalDate.of(1994, 7, 13))),
            Map.entry("325636", new Player("325636", 11, "Alexia PUTELLAS",  "Midfielder", LocalDate.of(1994, 2, 4)))
            ) );


    public List<Player> listPlayers(){
        return players.values().stream()
                .toList();
    }

    public Player getPlayer(String id){
        Player player = players.get(id);
        if(player == null){
            throw new NotFoundException("Player not found");
        }
        return player;
    }

    public void addPlayer(Player player){
        if(players.containsKey(player.id())){
            throw new AlreadyExistsException("Player already exists");
        }else {
            players.put(player.id(), player);
        }
    }

    public void updatePlayer(Player player){
        if(!players.containsKey(player.id())){
            throw new NotFoundException("Player does not exist");
        }else {
            players.put(player.id(), player);
        }
    }

    public void deletePlayer(String id){
        players.remove(id);
    }
}

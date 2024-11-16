package com.packt.albumsRC.external;

import com.packt.albumsRC.models.Player;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

public class FootballClientService {

    private final RestClient restClient;

    public FootballClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    List<Player> getPlayers(){
        return restClient.get().uri("/players").retrieve() //.get returns object that can help you customize request
                .body(new ParameterizedTypeReference<List<Player>>(){}); //  create a subclass of generic type List<Player> using anonymous inline
    }

    public Optional<Player> getPlayer(String id) {
        return restClient.get().uri("/players/{id}", id)
                .exchange((request, response) -> { // exchange method provide handler to manage response
                    if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Optional.empty();
                    }
                    return Optional.of(response.bodyTo(Player.class));
                });
    }

}



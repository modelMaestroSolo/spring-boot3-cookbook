package com.packt.albumsRC.external;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.packt.albumsRC.models.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"football.api.url=http://localhost:7979"}) // restClient bean depends on this property.
class FootballClientServiceTest {

    @Autowired
    FootballClientService footballClientService;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void init(){
        wireMockServer = new WireMockServer(7979);
        wireMockServer.start();
        WireMock.configureFor(7979); //configure WireMock client for where server is running.
    }

    @Test
    void getPlayer() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/players/325636"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                "id": "325636",
                                "jerseyNumber": 11,
                                "name": "Alexia PUTELLAS",
                                "position": "Midfielder",
                                "dateOfBirth": "1994-02-04"
                                }
                                """)));
        Optional<Player> player = footballClientService.getPlayer("325636");

        Player expectedPlayer = new Player("325636", 11, "Alexia PUTELLAS", "Midfielder",
                LocalDate.of(1994, 2, 4));
        assertEquals(expectedPlayer, player.orElseThrow());
    }
}
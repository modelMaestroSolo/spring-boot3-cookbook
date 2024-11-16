package com.packt.football.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.models.Player;
import com.packt.football.services.FootballService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PlayerController.class)
class PlayerControllerTest {

    @MockBean
    private FootballService footballService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listPlayers() throws Exception {

        Player player1 = new Player("1884823", 5, "Ivana ANDRES", "Defender",
                LocalDate.of(1994, 7, 13));
        Player player2 = new Player("325636", 11, "Alexia PUTELLAS", "Midfielder",
                LocalDate.of(1994, 2, 4));
        List<Player> players = List.of(player1, player2);
        given(footballService.listPlayers()).willReturn(players);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andReturn();

        // retrieved result with .andReturn() for further validation of the content for eg
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Player> resultedPlayers = mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(List.class, Player.class));
        assertArrayEquals(players.toArray(), resultedPlayers.toArray());
    }

    @Test
    void testGetPlayer_doesNotExist() throws Exception {
        String id = "182938";
        given(footballService.getPlayer(id)).willThrow(new NotFoundException("Player not found"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/players/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPlayer() {
    }

    @Test
    void readPlayer() {
    }

    @Test
    void deletePlayer() {
    }

    @Test
    void updatePlayer() {
    }
}
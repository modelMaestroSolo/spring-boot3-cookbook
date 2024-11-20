package com.packt.footballresource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/football")
@RestController
public class FootballController {

    @GetMapping("/teams")
    public List<String> getTeams(){
        return List.of("Argentina", "Australia", "Brazil");
    }
}

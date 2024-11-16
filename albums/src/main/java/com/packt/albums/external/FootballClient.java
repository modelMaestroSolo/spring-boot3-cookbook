package com.packt.albums.external;

import com.packt.albums.models.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
/* Alt:
there are tools that can auto generate client code based on openapi description
exposed by the REST api. eg: openapi generator or swagger codegen.
 */

@FeignClient(name = "football", url = "http://localhost:8080")
public interface FootballClient {

    @GetMapping(value = "/players")
    List<Player> getPlayers();
}

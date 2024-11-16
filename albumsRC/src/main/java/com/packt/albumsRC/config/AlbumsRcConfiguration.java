package com.packt.albumsRC.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AlbumsRcConfiguration {

    @Value("${football.api.url:http://localhost:8080}")
    String baseUrl;

    @Bean
    RestClient restClient(){
        return RestClient.create(baseUrl);
    }
}

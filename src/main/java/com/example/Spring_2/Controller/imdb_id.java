package com.example.Spring_2.Controller;


import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class imdb_id {

    private static final String API_KEY = "c7a42ce4"; // OMDb API key
    private static final String API_URL = "http://www.omdbapi.com/"; // site redirection

    @GetMapping("/api/getIMDbID")
    public Map<String, String> getIMDbID(@RequestParam String movieName) {

        Map<String, String> responseMap = new HashMap<>();
        Map<String, String> responseMap_2 = new HashMap<>();
        HttpResponse<JsonNode> response = Unirest.get(API_URL) // for handling http request

                .queryString("t", movieName)
                .queryString("apikey", API_KEY)
                .asJson();

        if (response.getStatus() == 200) {
            String responseBody = response.getBody().toString();
            System.out.println(responseBody);
            if (responseBody.contains("\"Response\":\"True\"")) {
                responseMap.put("imdbID", response.getBody().getObject().getString("imdbID"));


            } else {
                responseMap.put("error", "Movie not found.");
            }

        } else {
            responseMap.put("error", "Movie Not Available Right Now In Servers");
        }
        return responseMap;
    }

}

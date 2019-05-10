package com.orange.pandsclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

@RestController
public class PandSClientController {
    
    @Value("${pandsEndPoint}")
    private String pandsEndPoint;


    private static final Logger logger = LoggerFactory.getLogger(PandSClientController.class);

    @Value("${pandsuri}")
    private String pandsUri;

    @GetMapping("/api/pandsclient/{id}")
    public Collection getAtomicProducts(@PathVariable String id){

        String PANDS_URI = pandsEndPoint+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection> response =  restTemplate.exchange(PANDS_URI, HttpMethod.GET, entity, Collection.class);
        return response.getBody();
    }

    @GetMapping("/api/error")
    public String getError(){
        String resp ="";
        try {
            String PANDS_URI = pandsUri + "/api/error";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(PANDS_URI, HttpMethod.GET, entity, String.class);
            resp = "Response Body:"+response.getBody();
        }catch (HttpStatusCodeException ex){
            logger.error("HttpStatusCodeException");
            resp = ex.getStatusCode() + ":" + ex.getMessage();
        }catch (RestClientResponseException ex){
            logger.error("RestClientResponseException");
            resp = ex.getRawStatusCode() + ":" + ex.getMessage();
        }catch(Exception ex){
            logger.error("Exception");
            resp = ex.getMessage();
        }
        return resp;
    }
}

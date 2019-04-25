package com.orange.pandsclient;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
mport org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

@RestController
public class PandSClientController {
    
    @Value("${pandsEndPoint}")
    private String pandsEndPoint;


    @GetMapping("/api/pandsclient/{id}")
    public Collection getAtomicProducts(@PathVariable String id){

        //String PANDS_URI = "http://crm_acc_products-and-services:54046/api/de/epc/billingCodesByOfferId/"+id;
        String PANDS_URI = pandsEndPoint+id;
        //String PANDS_URI = "http://172.27.27.154:54046/api/de/epc/billingCodesByOfferId/"+id;
    	//String PANDS_URI = "http://localhost:54056/api/products/atomic/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Collection> response =  restTemplate.exchange(PANDS_URI, HttpMethod.GET, entity, Collection.class);
        return response.getBody();
    }
}

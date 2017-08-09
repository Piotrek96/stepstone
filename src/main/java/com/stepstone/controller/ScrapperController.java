package com.stepstone.controller;


import com.stepstone.model.request.GetLinksRequest;
import com.stepstone.service.ScrapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@RestController
public class ScrapperController {

    private final ScrapperService scrapperService;

    ScrapperController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }


    @PostMapping("/scrapper/links")
    public ResponseEntity<HashMap<String, Integer>> getLinks(@RequestBody GetLinksRequest domain) throws IOException, URISyntaxException {
        Assert.notNull(domain.name, "domain name's required.");
        return new ResponseEntity<>(scrapperService.getLinks(domain.name), HttpStatus.OK);
    }
}

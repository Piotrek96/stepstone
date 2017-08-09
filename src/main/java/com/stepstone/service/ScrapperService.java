package com.stepstone.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@Service
public class ScrapperService {
    private final UrlValidator urlValidator;

    private final HashMap<String, Integer> domainList;

    public ScrapperService() {
        urlValidator = new UrlValidator();
        domainList = new HashMap<String, Integer>();
    }
    public HashMap<String, Integer> getLinks(String domain) throws IOException, URISyntaxException{
        domainList.clear();
        Document document = Jsoup.connect(domain).get();
        String domainHost = new URI(domain).getHost();
        Elements links = document.select("a[href]");
        links.stream().map(link -> link.attr("href"))
                .filter(urlValidator::isValid)
                .map(this::getHost)
                .filter(link -> filterSelfLinks(domainHost, link))
                .filter(link -> filterEmptyLinks(domainHost, link))
                .forEach(domainName -> {
                    Integer count = domainList.get(domainName);
                    domainList.put(domainName, (count == null) ? 1 : count + 1);
                });
        return domainList;
    }

    private boolean filterSelfLinks(String domainHost, String link) {
        return !(link.equals(domainHost)  || link.equals("www.".concat(domainHost)));
    }

    private boolean filterEmptyLinks(String domainHost, String link) {
        return !link.equals("");
    }

    private String getHost(String link) {
        try {
            return  new URI(link).getHost();
        } catch (URISyntaxException e) {
            return "";
        }
    }
}

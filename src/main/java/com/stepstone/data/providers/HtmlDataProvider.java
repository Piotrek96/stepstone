package com.stepstone.data.providers;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class HtmlDataProvider {

    public Elements getData(String domain) throws IOException, URISyntaxException {
        Assert.isTrue(new UrlValidator().isValid(domain), "Wrong domain name.");
        Document document = Jsoup.connect(domain).get();
        return document.select("a[href]");
    }
}

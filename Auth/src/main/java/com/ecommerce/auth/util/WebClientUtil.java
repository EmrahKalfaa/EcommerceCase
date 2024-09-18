package com.ecommerce.auth.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class WebClientUtil {

    public static <T> T createClient(WebClient.Builder wcb, String url, Class<T> clazz) {
        wcb
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .baseUrl(url);
        WebClientAdapter wca = WebClientAdapter.create(wcb.build());
        HttpServiceProxyFactory build = HttpServiceProxyFactory.builderFor(wca).build();
        return build.createClient(clazz);
    }
}

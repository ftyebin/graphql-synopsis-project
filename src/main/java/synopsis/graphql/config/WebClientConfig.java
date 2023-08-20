package synopsis.graphql.config;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@Slf4j
public class WebClientConfig {

    private static final int DURATION_TIME_MILLIS = 5000;

    @Bean
    public WebClient webClient() {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);


        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(defaultUriBuilderFactory)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DURATION_TIME_MILLIS)
                        .responseTimeout(Duration.ofMillis(DURATION_TIME_MILLIS))))
                .build();
        log.info(webClient.toString());

        return webClient;
    }
}

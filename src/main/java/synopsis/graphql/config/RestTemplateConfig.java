package synopsis.graphql.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class RestTemplateConfig {

    private static final int DURATION_TIME_MILLIS = 500;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(DURATION_TIME_MILLIS))
                .setReadTimeout(Duration.ofMillis(DURATION_TIME_MILLIS))
                .build();
    }
}

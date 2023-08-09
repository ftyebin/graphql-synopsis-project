package synopsis.graphql.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "scs")
@Getter
@Setter
@RequiredArgsConstructor
public class ScsConfig {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> body;

}

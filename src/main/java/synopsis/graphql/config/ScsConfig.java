package synopsis.graphql.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "scs")
@Data
public class ScsConfig {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> body;
}

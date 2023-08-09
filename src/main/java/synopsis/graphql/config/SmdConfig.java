package synopsis.graphql.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "smd")
public class SmdConfig {
    private String url;
    private Map<String , String> headers;
    private Map<String, String> params;
}

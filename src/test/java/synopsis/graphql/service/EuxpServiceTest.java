package synopsis.graphql.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EuxpServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EuxpService euxpService;

    @Test
    void getEuxpResult() {
    }

    @Test
    void getEuxpResponse() {
    }
}
package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import synopsis.graphql.config.SmdConfig;
import synopsis.graphql.excpetion.ResultDataNotFoundException;
import synopsis.graphql.excpetion.SmdRequestException;
import synopsis.graphql.model.dto.request.RequestSmdData;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.util.converter.JsonToObjectConverter;
import synopsis.graphql.util.converter.SmdJsonToObjectConverter;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmdService {

    private final SmdConfig smdConfig;
    private final RestTemplate restTemplate;
    private final JsonToObjectConverter<SmdResult> smdConverter = new SmdJsonToObjectConverter();


    public SmdResult getSmdResult(RequestSmdData requestSmdData) {
        ResponseEntity<String> response = getSmdResponse(requestSmdData);
        return smdConverter.convert(response.getBody())
                .orElseThrow(() -> new ResultDataNotFoundException("SMD Result data not found"));
    }

    public ResponseEntity<String> getSmdResponse(RequestSmdData requestSmdData) {
        String url = constructUrl(requestSmdData);
        HttpEntity<Void> entity = new HttpEntity<>(constructHeaders());
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            throw new SmdRequestException("SMD 시스템에 GET 요청 실패 | " + e.getMessage());
        }
    }

    private HttpHeaders constructHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (smdConfig != null && smdConfig.getHeaders() != null) {
            smdConfig.getHeaders().forEach(headers::set);
        }
        return headers;
    }

    private String constructUrl(RequestSmdData requestSmdData) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(smdConfig.getUrl())
                .queryParam("mac_address", requestSmdData.getMac())
                .queryParam("stb_id", requestSmdData.getStbId())
                .queryParam("series_id", requestSmdData.getSeriesId());

        if (smdConfig.getParams() != null) {
            smdConfig.getParams().forEach(uriComponentsBuilder::queryParam);
        }
        return uriComponentsBuilder.toUriString();
    }
}

package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import synopsis.graphql.config.EuxpConfig;
import synopsis.graphql.excpetion.ResultDataNotFoundException;
import synopsis.graphql.excpetion.EuxpRequestException;
import synopsis.graphql.model.dto.request.RequestEuxpData;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.util.converter.EuxpJsonToObjectConverter;

@RequiredArgsConstructor
@Service
public class EuxpService {

    private final RestTemplate restTemplate;
    private final EuxpConfig euxpConfig;
    private final EuxpJsonToObjectConverter euxpConverter;

    public EuxpResult getEuxpResult(RequestEuxpData requestEuxpData) {
        ResponseEntity<String> response = getEuxpResponse(requestEuxpData);
        return euxpConverter.convert(response.getBody())
                .orElseThrow(() -> new ResultDataNotFoundException("EUXP Result data not found"));
    }

        public ResponseEntity<String> getEuxpResponse(RequestEuxpData requestEuxpData) {
        String url = constructUrl(requestEuxpData);
        HttpEntity<Void> entity = new HttpEntity<>(constructHeaders());

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            throw new EuxpRequestException("EUXP 시스템에 GET 요청 실패 | " + e.getMessage());
        }
    }

    private String constructUrl(RequestEuxpData requestEuxpData) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(euxpConfig.getUrl())
                .queryParam("stb_id", requestEuxpData.getStbId())
                .queryParam("search_type", requestEuxpData.getSynopsisSearchType())
                .queryParam("yn_recent", requestEuxpData.getLookupType())
                .queryParam("menu_stb_svc_id", requestEuxpData.getMenuStbServiceId())
                .queryParam("epsd_id", requestEuxpData.getEpisodeId());

        if (euxpConfig.getParams() != null) {
            euxpConfig.getParams().forEach(uriComponentsBuilder::queryParam);
        }
        return uriComponentsBuilder.toUriString();
    }

    private HttpHeaders constructHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (euxpConfig != null && euxpConfig.getHeaders() != null ) {
            euxpConfig.getHeaders().forEach(headers::set);
        }
        return headers;
    }
}

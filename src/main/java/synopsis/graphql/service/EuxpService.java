package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import synopsis.graphql.config.EuxpConfig;
import synopsis.graphql.model.dto.request.RequestEuxpData;
import synopsis.graphql.model.exup.EuxpResult;
import synopsis.graphql.util.converter.EuxpJsonToObjectConverter;

@RequiredArgsConstructor
@Slf4j
@Service
public class EuxpService {
    private final RestTemplate restTemplate;
    private final EuxpConfig euxpConfig;

    public EuxpResult getEuxpResult(RequestEuxpData requestEuxpData) {
        HttpHeaders headers = new HttpHeaders();
        euxpConfig.getHeaders().forEach(headers::set);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(euxpConfig.getUrl());
        euxpConfig.getParams().forEach(uriComponentsBuilder::queryParam);

        uriComponentsBuilder.queryParam("stb_id", requestEuxpData.getStbId());
        uriComponentsBuilder.queryParam("search_type", requestEuxpData.getSynopsisSearchType());
        uriComponentsBuilder.queryParam("yn_recent", requestEuxpData.getLookupType());
        uriComponentsBuilder.queryParam("menu_stb_svc_id", requestEuxpData.getMenuStbServiceId());
        uriComponentsBuilder.queryParam("epsd_id", requestEuxpData.getEpisodeId());
        String url = uriComponentsBuilder.toUriString();

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        log.info(response.getBody());

        return EuxpJsonToObjectConverter.convert(response.getBody());
    }
}

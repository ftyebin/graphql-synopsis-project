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
import synopsis.graphql.model.viewpage.ViewPage;
import synopsis.graphql.util.converter.ViewPageConverter;

@RequiredArgsConstructor
@Slf4j
@Service
public class ViewPageService {
    private final RestTemplate restTemplate;
    private final EuxpConfig euxpConfig;

    public ViewPage getViewPageResult(RequestEuxpData requestEuxpData) {

        HttpHeaders headers = new HttpHeaders();
        euxpConfig.getHeaders().forEach(headers::set);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(euxpConfig.getUrl())
                .queryParam("stb_id", requestEuxpData.getStbId())
                .queryParam("search_type", requestEuxpData.getSynopsisSearchType())
                .queryParam("yn_recent", requestEuxpData.getLookupType())
                .queryParam("menu_stb_svc_id", requestEuxpData.getMenuStbServiceId())
                .queryParam("epsd_id", requestEuxpData.getEpisodeId());

        euxpConfig.getParams().forEach(uriComponentsBuilder::queryParam);

        String url = uriComponentsBuilder.toUriString();

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        log.info(response.getBody());

        return ViewPageConverter.convert(response.getBody());
    }
}
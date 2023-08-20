package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import synopsis.graphql.config.SmdConfig;
import synopsis.graphql.excpetion.ResultDataNotFoundException;
import synopsis.graphql.excpetion.SmdRequestException;
import synopsis.graphql.model.dto.request.RequestSmdData;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.util.converter.SmdJsonToObjectConverter;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmdService {

    private final WebClient webClient;
    private final SmdConfig smdConfig;
    private final SmdJsonToObjectConverter smdConverter;


    public SmdResult getSmdResult(RequestSmdData requestSmdData) {
        String response = String.valueOf(fetchSmdResponse(requestSmdData));
        return smdConverter.convert(response)
                .orElseThrow(() -> new ResultDataNotFoundException("SMD Result data not found"));
    }

    public Mono<SmdResult> fetchSmdResponse(RequestSmdData requestSmdData) {
        String url = constructUrl(requestSmdData);
        HttpHeaders headers = constructHeaders();

        return webClient.get()
                .uri(url)
                .headers(h -> h.addAll(headers))
                .retrieve()
                .bodyToMono(SmdResult.class)
                .onErrorReturn(null)
                .onErrorResume(WebClientException.class, e -> {
                    log.error("SMD 시스템에 GET 요청 실패 : " + e.getMessage());
                    throw new SmdRequestException("SMD 시스템에 GET 요청 실패 | " + e.getMessage());
                })
                .onErrorResume(e -> {
                    log.error("SMD GET Request failed : " + e.getMessage());
                    return Mono.empty();
                });
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

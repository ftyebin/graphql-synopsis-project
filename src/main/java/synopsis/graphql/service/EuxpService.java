package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import synopsis.graphql.config.EuxpConfig;
import synopsis.graphql.excpetion.ResultDataNotFoundException;
import synopsis.graphql.excpetion.SmdRequestException;
import synopsis.graphql.model.dto.request.RequestEuxpData;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.util.converter.EuxpJsonToObjectConverter;

@RequiredArgsConstructor
@Service
@Slf4j
public class EuxpService {

    private final WebClient webClient;
    private final EuxpConfig euxpConfig;
    private final EuxpJsonToObjectConverter euxpConverter;

    public EuxpResult getEuxpResult(RequestEuxpData requestEuxpData) {
        String response = String.valueOf(fetchEuxpResponse(requestEuxpData));
        return euxpConverter.convert(response)
                .orElseThrow(() -> new ResultDataNotFoundException("EUXP Result data not found"));
    }

    public Mono<EuxpResult> fetchEuxpResponse(RequestEuxpData requestEuxpData) {
        String url = constructUrl(requestEuxpData);
        HttpHeaders headers = constructHeaders();

        log.info("WEBclient for euxp");
        return webClient.get()
                .uri(url)
                .headers(h -> h.addAll(headers))
                .retrieve()
                .bodyToMono(EuxpResult.class)
                .onErrorReturn(null)
                .onErrorResume(WebClientException.class, e -> {
                    log.error("EUXP 시스템에 GET 요청 실패 : " + e.getMessage());
                    throw new SmdRequestException("EUXP 시스템에 GET 요청 실패 | " + e.getMessage());
                })
                .onErrorResume(e -> {
                    log.error("EUXP GET Request failed : " + e.getMessage());
                    return Mono.empty();
                });
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
        log.info(uriComponentsBuilder.toUriString());
        return uriComponentsBuilder.toUriString();
    }

    private HttpHeaders constructHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (euxpConfig != null && euxpConfig.getHeaders() != null ) {
            euxpConfig.getHeaders().forEach(headers::set);
        }
        log.info(headers.toString());
        return headers;
    }
}

package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import synopsis.graphql.config.ScsConfig;
import synopsis.graphql.excpetion.ResultDataNotFoundException;
import synopsis.graphql.excpetion.SmdRequestException;
import synopsis.graphql.model.dto.request.CustomScsPpvProducts;
import synopsis.graphql.model.dto.request.CustomScsRequestBody;
import synopsis.graphql.model.dto.request.RequestScsData;
import synopsis.graphql.model.dto.request.RequestScsPpvProduct;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.util.converter.ScsJsonToObjectConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ScsService {

    private static final String RESPONSE_FORMAT = "json";
    private static final String VERSION = "5.3.0";

    private final WebClient webClient;
    private final ScsConfig scsConfig;
    private final ScsJsonToObjectConverter scsConverter;

    public ScsResult getScsResult(RequestScsData requestScsData){
        String response = String.valueOf(fetchScsResponse(requestScsData));
        return scsConverter.convert(response)
                .orElseThrow(() -> new ResultDataNotFoundException("SCS Result data not found"));
    }


    public Mono<ScsResult> fetchScsResponse(RequestScsData requestScsData) {
        List<CustomScsPpvProducts> customScsPpvProducts =
                Optional.ofNullable(requestScsData.getPpvProducts())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(this::toCustomPpvProducts)
                    .toList();


        HttpHeaders headers = constructHeaders();

        CustomScsRequestBody scsRequestBody = createRequestBody(requestScsData, customScsPpvProducts);

        return sendScsRequest(scsRequestBody, headers);
    }

    private HttpHeaders constructHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (scsConfig != null && scsConfig.getHeaders() != null) {
            scsConfig.getHeaders().forEach(headers::set);
        }
        return headers;
    }

    private CustomScsRequestBody createRequestBody(RequestScsData requestScsData, List<CustomScsPpvProducts> customScsPpvProducts) {
        return CustomScsRequestBody.builder()
                .response_format(RESPONSE_FORMAT)
                .ver(VERSION)
                .stb_id(requestScsData.getStbId())
                .hash_id(requestScsData.getHashId())
                .ui_name(requestScsData.getUiName())
                .sris_id(requestScsData.getSeriesId())
                .synopsis_type(requestScsData.getSynopsisSearchType())
                .ppv_products(customScsPpvProducts)
                .build();
    }


    private CustomScsPpvProducts toCustomPpvProducts(RequestScsPpvProduct product) {
        log.info(product.toString());
        return CustomScsPpvProducts.builder()
                .prd_prc_id(product.getProductPriceId())
                .yn_prd_nscreen(product.getIsNScreen())
                .prd_typ_cd((product.getProductTypeCode()))
                .purc_pref_rank(product.getPurchasePreferenceRank())
                .possn_yn(product.getIsPossession())
                .epsd_id(product.getEpisodeId())
                .build();
    }


    private Mono<ScsResult> sendScsRequest(CustomScsRequestBody body, HttpHeaders headers) {
        return webClient.post()
                .uri(scsConfig.getUrl())
                .headers(h -> h.addAll(headers))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ScsResult.class)
                .onErrorResume(WebClientException.class, e -> {
                    log.error("SCS 시스템에 POST 요청 실패 : " + e.getMessage());
                    throw new SmdRequestException("SCS 시스템에 GET 요청 실패 | " + e.getMessage());
                })
                .onErrorResume(e -> {
                    log.error("SCS POST Request failed : " + e.getMessage());
                    throw new SmdRequestException("SCS 시스템에 GET 요청 실패 | " + e.getMessage());
                });
    }
}

package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import synopsis.graphql.config.ScsConfig;
import synopsis.graphql.model.dto.request.CustomScsPpvProducts;
import synopsis.graphql.model.dto.request.CustomScsRequestBody;
import synopsis.graphql.model.dto.request.RequestScsData;
import synopsis.graphql.model.dto.request.RequestScsPpvProduct;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.util.converter.ScsJsonToObjectConverter;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ScsService {

    private static final String RESPONSE_FORMAT = "json";
    private static final String VERSION = "5.3.0";


    private final ScsConfig scsConfig;
    private final RestTemplate restTemplate;

    private CustomScsPpvProducts setCustomPpvProducts(RequestScsPpvProduct product) {
        return CustomScsPpvProducts.builder()
                .prd_prc_id(product.getProductPriceId())
                .yn_prd_nscreen(product.getIsNScreen())
                .prd_typ_cd((product.getProductTypeCode()))
                .purc_pref_rank(product.getPurchasePreferenceRank())
                .possn_yn(product.getIsPossession())
                .epsd_id(product.getEpisodeId())
            .build();
    }

    public ScsResult getScsResult(RequestScsData requestScsData){
        List<CustomScsPpvProducts> customScsPpvProducts = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        scsConfig.getHeaders().forEach(headers::set);

        requestScsData.getPpvProducts()
                .forEach(product -> customScsPpvProducts.add(setCustomPpvProducts(product)));


        CustomScsRequestBody scsRequestBody = CustomScsRequestBody.builder()
                .response_format(RESPONSE_FORMAT)
                .ver(VERSION)
                .stb_id(requestScsData.getStbId())
                .hash_id(requestScsData.getHashId())
                .ui_name(requestScsData.getUiName())
                .sris_id(requestScsData.getSeriesId())
                .synopsis_type(requestScsData.getSynopsisSearchType())
                .ppv_products(customScsPpvProducts)
            .build();

        ResponseEntity<String> response = restTemplate.exchange(
                scsConfig.getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(scsRequestBody, headers),
                String.class
        );

        log.info(response.getBody());

        return ScsJsonToObjectConverter.convert(response.getBody());
    }
}

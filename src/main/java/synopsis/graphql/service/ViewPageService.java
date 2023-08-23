package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import synopsis.graphql.model.dto.request.RequestData;
import synopsis.graphql.model.dto.request.RequestEuxpData;
import synopsis.graphql.model.dto.request.RequestScsData;
import synopsis.graphql.model.dto.request.RequestSmdData;
import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.dto.response.ViewPageFetchResult;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.model.viewpage.ViewPage;
import synopsis.graphql.util.converter.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ViewPageService {
    private final EuxpService euxpService;
    private final SmdService smdService;
    private final ScsService scsService;

    private final ViewPageConverter viewPageConverter;

    public ViewPage getViewPageResult(RequestData requestData) {

        EuxpResult euxpResult = euxpService.fetchEuxpResponse(new RequestEuxpData(requestData)).block();
        SmdResult smdResult = smdService.fetchSmdResponse(new RequestSmdData(requestData)).block();
        ScsResult scsResult = scsService.fetchScsResponse(new RequestScsData(requestData)).block();

        SynopsisData synopsisData = SynopsisData.builder()
                .euxpResult(euxpResult)
                .smdResult(smdResult)
                .scsResult(scsResult)
                .build();

        return viewPageConverter.convert(synopsisData).getViewPage();
    }

    public ViewPageFetchResult getViewPageWithRErrors(RequestData requestData) {

        Mono<EuxpResult> euxpResult = euxpService.fetchEuxpResponse(new RequestEuxpData(requestData));
        Mono<SmdResult> smdResult = smdService.fetchSmdResponse(new RequestSmdData(requestData));
        Mono<ScsResult> scsResult = scsService.fetchScsResponse(new RequestScsData(requestData));

        Tuple3<EuxpResult, SmdResult, ScsResult> combinedResult = Mono.zip(euxpResult, smdResult, scsResult).block();

        SynopsisData synopsisData = getSynopsisData(combinedResult);

        return viewPageConverter.convert(synopsisData);
    }

    private static SynopsisData getSynopsisData(Tuple3<EuxpResult, SmdResult, ScsResult> combinedResult) {
        return SynopsisData.builder()
                .euxpResult(combinedResult != null ? combinedResult.getT1() : null)
                .smdResult(combinedResult != null ? combinedResult.getT2() : null)
                .scsResult(combinedResult != null ? combinedResult.getT3() : null)
                .build();
    }
}
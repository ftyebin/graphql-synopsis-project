package synopsis.graphql.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import synopsis.graphql.model.dto.request.RequestData;
import synopsis.graphql.model.dto.request.RequestEuxpData;
import synopsis.graphql.model.dto.request.RequestScsData;
import synopsis.graphql.model.dto.request.RequestSmdData;
import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.dto.response.ViewPageFetchResult;
import synopsis.graphql.model.viewpage.ViewPage;
import synopsis.graphql.service.*;


@Controller
@RequiredArgsConstructor
public class SynopsisController {
    private final ViewPageService viewPageService;

    private final EuxpService euxpService;
    private final SmdService smdService;
    private final ScsService scsService;

    @QueryMapping
    public SynopsisData synopsisView(@Argument RequestData requestData) {

        RequestEuxpData requestEuxpData = new RequestEuxpData(requestData);
        RequestSmdData requestSmdData = new RequestSmdData(requestData);
        RequestScsData requestScsData = new RequestScsData(requestData);

        return SynopsisData.builder()
                .euxpResult(euxpService.getEuxpResult(requestEuxpData))
                .smdResult(smdService.getSmdResult(requestSmdData))
                .scsResult(scsService.getScsResult(requestScsData))
                .build();
    }


    @QueryMapping
    public ViewPage synopsisPage(@Argument RequestData requestData) {
        return viewPageService.getViewPageResult(requestData);
    }

    @QueryMapping
    public ViewPageFetchResult synopsisPageWithErrors(@Argument RequestData requestData) {
        return viewPageService.getViewPageWithRErrors(requestData);
    }
}

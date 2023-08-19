package synopsis.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synopsis.graphql.model.dto.request.RequestData;
import synopsis.graphql.model.dto.request.RequestEuxpData;
import synopsis.graphql.model.dto.request.RequestScsData;
import synopsis.graphql.model.dto.request.RequestSmdData;
import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.viewpage.ViewPage;
import synopsis.graphql.util.converter.ViewPageConverter;

@RequiredArgsConstructor
@Service
public class ViewPageService {
    private final EuxpService euxpService;
    private final SmdService smdService;
    private final ScsService scsService;

    private final ViewPageConverter viewPageConverter;

    public ViewPage getViewPageResult(RequestData requestData) {
        RequestEuxpData requestEuxpData = new RequestEuxpData(requestData);
        RequestSmdData requestSmdData = new RequestSmdData(requestData);
        RequestScsData requestScsData = new RequestScsData(requestData);

        SynopsisData synopsisData = SynopsisData.builder()
                .euxpResult(euxpService.getEuxpResult(requestEuxpData))
                .smdResult(smdService.getSmdResult(requestSmdData))
                .scsResult(scsService.getScsResult(requestScsData))
            .build();

        return viewPageConverter.convert(synopsisData);
    }
}

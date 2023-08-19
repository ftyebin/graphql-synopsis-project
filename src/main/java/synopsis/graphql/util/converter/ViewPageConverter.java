package synopsis.graphql.util.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import synopsis.graphql.excpetion.ResultDataNotFoundException;
import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.euxp.*;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.model.viewpage.*;
import synopsis.graphql.util.converter.viewpage.*;

import java.util.*;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewPageConverter {

    private static final String RESULT_DATA_NOT_FOUND = "응답 결과 데이터에 오류가 있습니다.";

    private final SynopsisBannerConverter synopsisBannerConverter;
    private final ContentsTitleConverter contentsTitleConverter;
    private final ContentsDetailConverter contentsDetailConverter;
    private final ContentsAdditionalConverter contentsAdditionalConverter;
    private final UserContentsPreferenceConverter userContentsPreferenceConverter;
    private final ContentsEpisodeListConverter contentsEpisodeListConverter;
    private final PurchaseInfoConverter purchaseInfoConverter;
    private final PlayInfoConverter playInfoConverter;

    public ViewPage convert(SynopsisData synopsisData) {

        EuxpResult euxpResult = getEuxpResult(synopsisData);
        SmdResult smdResult = getSmdResult(synopsisData);
        ScsResult scsResult = getScsResult(synopsisData);

        validateSynopsisData(synopsisData);

        Contents euxpContents = euxpResult.getContents();

        return ViewPage.builder()
                .banners(synopsisBannerConverter.convert(euxpContents))
                .title(contentsTitleConverter.convert(euxpContents))
                .details(contentsDetailConverter.convert(euxpContents))
                .contentsAdditional(contentsAdditionalConverter.convert(euxpContents))
                .userPreference(userContentsPreferenceConverter.convert(smdResult, scsResult))
                .episodeList(contentsEpisodeListConverter.convert(euxpContents))
                .purchaseInfo(purchaseInfoConverter.convert(euxpResult))
                .playInfo(playInfoConverter.convert(euxpContents))
            .build();
    }

    private <T> T getRequiredData(Supplier<T> dataSupplier, String errorPrefix) {
        T data = dataSupplier.get();
        if (data == null) {
            throw new ResultDataNotFoundException(errorPrefix + RESULT_DATA_NOT_FOUND);
        }
        return data;
    }

    private EuxpResult getEuxpResult(SynopsisData synopsisData) {
        return getRequiredData(synopsisData::getEuxpResult, "EUXP");
    }

    private SmdResult getSmdResult(SynopsisData synopsisData) {
        return getRequiredData(synopsisData::getSmdResult, "SMD");
    }

    private ScsResult getScsResult(SynopsisData synopsisData) {
        return getRequiredData(synopsisData::getScsResult, "SCS");
    }

    private void validateSynopsisData(SynopsisData synopsisData) {
        List<String> errors = new ArrayList<>();

        if (synopsisData.getEuxpResult() == null || synopsisData.getEuxpResult().getContents() == null) {
            errors.add("EUXP" + RESULT_DATA_NOT_FOUND);
        }
        if (synopsisData.getSmdResult() == null) {
            errors.add("SMD" + RESULT_DATA_NOT_FOUND);
        }
        if (synopsisData.getScsResult() == null) {
            errors.add("SCS" + RESULT_DATA_NOT_FOUND);
        }

        handleErrors(errors);
    }

    private void handleErrors(List<String> errors) {
        if (!errors.isEmpty()) {
            String errorMessage = String.join(", ", errors);
            log.error(errorMessage);
            throw new ResultDataNotFoundException(errorMessage);
        }
    }
}

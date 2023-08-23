package synopsis.graphql.util.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import synopsis.graphql.model.dto.response.Error;
import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.dto.response.ViewPageFetchResult;
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

    private List<Error> errors = new ArrayList<>();

    private final SynopsisTypeConverter synopsisTypeConverter;
    private final SynopsisBannerConverter synopsisBannerConverter;
    private final ContentsTitleConverter contentsTitleConverter;
    private final ContentsDetailConverter contentsDetailConverter;
    private final ContentsAdditionalConverter contentsAdditionalConverter;
    private final UserContentsPreferenceConverter userContentsPreferenceConverter;
    private final ContentsEpisodeListConverter contentsEpisodeListConverter;
    private final PurchaseInfoConverter purchaseInfoConverter;
    private final PlayInfoConverter playInfoConverter;

    public ViewPageFetchResult convert(SynopsisData synopsisData) {

        EuxpResult euxpResult = getEuxpResult(synopsisData);
        SmdResult smdResult = getSmdResult(synopsisData);
        ScsResult scsResult = getScsResult(synopsisData);

        errors = validateSynopsisData(synopsisData);

        Contents euxpContents = euxpResult.getContents();

        SynopsisType synopsisType = synopsisTypeConverter.convert(euxpResult);
        SynopsisBanner synopsisBanner = null;
        ContentsTitle contentsTitle = null;
        ContentsDetail contentsDetail = null;
        ContentsAdditional contentsAdditional = null;
        ContentsEpisodeList contentsEpisodeList = null;
        PurchaseInfo purchaseInfo = null;
        PlayInfo playInfo = null;
        UserContentsPreference userContentsPreference = null;

        if (euxpContents != null) {
            log.info(euxpContents.toString());
            synopsisBanner = synopsisBannerConverter.convert(euxpContents);
            contentsTitle = contentsTitleConverter.convert(euxpContents);
            contentsDetail = contentsDetailConverter.convert(euxpContents);
            contentsAdditional = contentsAdditionalConverter.convert(euxpContents);

            contentsEpisodeList = contentsEpisodeListConverter.convert(euxpContents);
            purchaseInfo = purchaseInfoConverter.convert(euxpResult);
            playInfo = playInfoConverter.convert(euxpContents);
        }
        if (smdResult != null && scsResult != null) {
            userContentsPreference = userContentsPreferenceConverter.convert(smdResult, scsResult);
        }

        return ViewPageFetchResult.builder()
                .viewPage(
                        ViewPage.builder()
                            .type(synopsisType)
                            .banners(synopsisBanner)
                            .title(contentsTitle)
                            .details(contentsDetail)
                            .contentsAdditional(contentsAdditional)
                            .userPreference(userContentsPreference)
                            .episodeList(contentsEpisodeList)
                            .purchaseInfo(purchaseInfo)
                            .playInfo(playInfo)
                            .build()
                )
                .errors(errors)
                .build();
    }

    private <T> T getRequiredData(Supplier<T> dataSupplier, String errorPrefix) {
        T data = dataSupplier.get();

        /*if (data == null) {
            throw new ResultDataNotFoundException(errorPrefix + RESULT_DATA_NOT_FOUND);
        }

         */
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

    private List<Error> validateSynopsisData(SynopsisData synopsisData) {
        validateData(synopsisData.getEuxpResult(), "EUXP");
        validateData(synopsisData.getSmdResult(), "SMD");
        validateData(synopsisData.getScsResult(), "SCS");

        handleErrors(errors);

        return errors;
    }

    private <T> void validateData(T data, String systemName) {
        if (data == null) {
            errors.add(new Error(systemName + RESULT_DATA_NOT_FOUND));
        }
    }
    private void handleErrors(List<Error> errors) {
        if (!errors.isEmpty()) {
            errors.forEach(e -> log.error(String.valueOf(e)));
        }
    }
}

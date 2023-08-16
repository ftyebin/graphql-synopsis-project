package synopsis.graphql.util.converter;

import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.euxp.EpsdRsluInfo;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.model.viewpage.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ViewPageConverter {

    private static final String YES = "Y";
    private static final int FIRST_INDEX = 0;


    private ViewPageConverter(){
        throw new IllegalStateException("View Page Converter");
    }

    public static ViewPage convert(SynopsisData synopsisData) {

        EuxpResult euxpResult = synopsisData.getEuxpResult();
        SmdResult smdResult = synopsisData.getSmdResult();
        ScsResult scsResult = synopsisData.getScsResult();


        // Todo: SynopsisType type = getSynopsisType(euxpContents);
        // Todo: SynopsisBanner banners = getSynopsisBanner(euxpContents);

        Contents euxpContents = Objects.requireNonNull(euxpResult).getContents();
        ContentsTitle title = getContentsTitle(euxpContents);
        ContentsDetail contentsDetail = getContentsDetail(euxpContents);
        ContentsAdditional contentsAdditional = getContentsAdditional(euxpContents);

        // Todo: UserContentsPreference userPreference = getUserContentsPreference(euxpContents);

        ContentsEpisodeList episodeList = getContentsEpisodeList(euxpContents);

        // Todo: PurchaseInfo purchaseInfo = getPurchaseInfo(euxpContents);

        PlayInfo playInfo = getPlayInfo(euxpContents);

        return ViewPage.builder()
                .title(title)
                .details(contentsDetail)
                .contentsAdditional(contentsAdditional)
                .episodeList(episodeList)
                .playInfo(playInfo)
            .build();
    }


    private static PlayInfo getPlayInfo(Contents euxpContents) {
        EpsdRsluInfo epsdRsluInfo = euxpContents.epsd_rslu_info.get(FIRST_INDEX); // rslu_typ_cd 기준 정렬 필요 (임시- 첫번째 인덱스)

        return PlayInfo.builder()
                .mainPreviewPlay(
                        MainPreviewPlay.builder()
                                .episodeId(euxpContents.epsd_id)
                                .seriesId(euxpContents.sris_id)
                                .episodeResolutionId(epsdRsluInfo.epsd_rslu_id)
                                .startTime((int) epsdRsluInfo.openg_tmtag_tmsc)
                                .previewTime(Integer.parseInt(epsdRsluInfo.preview_time))
                                .totalTime(Integer.parseInt(euxpContents.play_time))
                            .build()
                )
                .trailerPlay(
                        TrailerPlay.builder()
                                .episodeId(euxpContents.epsd_id)
                                .seriesId(euxpContents.sris_id)
                                .productPriceId(euxpContents.preview.get(FIRST_INDEX).prd_prc_id)
                            .build()
                )
            .build();
    }

    private static ContentsEpisodeList getContentsEpisodeList(Contents euxpContents) {
        ContentsEpisode contentsEpisode = ContentsEpisode.builder()
                .imagePath(euxpContents.epsd_poster_filename_h)
                .episodeNumber(euxpContents.brcast_tseq_nm)
                .isNotBroadcasted(String.valueOf(Objects.equals(euxpContents.cacbro_yn, YES)
                        ? Boolean.TRUE
                        : Boolean.FALSE)
                )
            .build();

        return ContentsEpisodeList.builder()
                .list(Collections.singletonList(contentsEpisode))
                .build();
    }

    private static ContentsAdditional getContentsAdditional(Contents euxpContents) {
        return ContentsAdditional.builder()
                .stillCut(euxpContents.stillCut.stream()
                        .map(cut -> StillCut.builder().imagePath(cut.img_path).build())
                        .toList()
                )
                .similarContents(SimilarContents.builder().callId(euxpContents.cw_call_id_val).build())
            .build();
    }

    private static ContentsDetail getContentsDetail(Contents euxpContents) {
        List<Cast> casts = new ArrayList<>();
        euxpContents.peoples
                .forEach(cast -> casts.add(
                        Cast.builder()
                                .birth(cast.brth_ymd)
                                .imagePath(cast.img_path)
                                .id(cast.prs_id)
                                .actorName(cast.prs_nm)
                                .castingName(cast.prs_plrl_nm)
                                .roleCode(cast.prs_role_cd)
                                .roleName(cast.prs_role_nm)
                                .build()
                ));

        ArrayList<Prize> prizes = new ArrayList<>();
        euxpContents.site_review.prize_history
                .forEach(prize -> prizes.add(
                        Prize.builder()
                                .name(prize.awardc_nm)
                                .description(prize.prize_dts_cts)
                                .year(prize.prize_yr)
                                .build()
                ));


        return ContentsDetail.builder()
                .summary(euxpContents.epsd_snss_cts)
                .castInfos(casts)
                .prizeInfos(prizes)
            .build();
    }

    private static ContentsTitle getContentsTitle(Contents euxpContents) {
        return ContentsTitle.builder()
                .imageTitle(
                        ContentsImageTitle.builder()
                                .isDark(Objects.equals(euxpContents.dark_img_yn, YES)
                                        ? Boolean.TRUE
                                        : Boolean.FALSE)

                                .imagePath(euxpContents.title_img_path)
                            .build())
                .textTitle(ContentsTextTitle.builder().text(euxpContents.title).build())
            .build();
    }

}

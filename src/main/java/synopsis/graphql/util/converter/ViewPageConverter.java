package synopsis.graphql.util.converter;

import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.model.dto.response.SynopsisData;
import synopsis.graphql.model.euxp.*;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.model.viewpage.*;
import synopsis.graphql.model.viewpage.StillCut;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ViewPageConverter {

    private static final String YES = "Y";
    private static final String LIKE_INFO_CHOSEN = "1";
    private static final int FIRST_INDEX = 0;
    private static final Integer PREVIEW_TIME_FIXED = 180;


    private ViewPageConverter(){
        throw new IllegalStateException("View Page Converter");
    }

    public static ViewPage convert(SynopsisData synopsisData) {

        EuxpResult euxpResult = synopsisData.getEuxpResult();
        SmdResult smdResult = synopsisData.getSmdResult();
        ScsResult scsResult = synopsisData.getScsResult();

        Contents euxpContents = Objects.requireNonNull(euxpResult).getContents();

        SynopsisBanner banners = getSynopsisBanner(euxpContents);
        ContentsTitle title = getContentsTitle(euxpContents);
        ContentsDetail contentsDetail = getContentsDetail(euxpContents);
        ContentsAdditional contentsAdditional = getContentsAdditional(euxpContents);
        UserContentsPreference userPreference = getUserContentsPreference(smdResult, scsResult);
        ContentsEpisodeList episodeList = getContentsEpisodeList(euxpContents);
        PurchaseInfo purchaseInfo = getPurchaseInfo(euxpResult);
        PlayInfo playInfo = getPlayInfo(euxpContents);


        return ViewPage.builder()
                .banners(banners)
                .title(title)
                .details(contentsDetail)
                .contentsAdditional(contentsAdditional)
                .userPreference(userPreference)
                .episodeList(episodeList)
                .purchaseInfo(purchaseInfo)
                .playInfo(playInfo)
            .build();
    }

    private static SynopsisBanner getSynopsisBanner(Contents euxpContents) {
        return SynopsisBanner.builder()
                .imageBanner(
                        SynopsisImageBanner.builder()
                                .imagePath(euxpContents.sris_evt_comt_img_path)
                                .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd2)
                                .callUrl(euxpContents.sris_evt_comt_call_url)
                                .vasId(euxpContents.sris_evt_comt_call_objt_id)
                                .vasServiceId(euxpContents.sris_evt_vas_svc_id)
                            .build())
                .textBanner(
                        SynopsisTextBanner.builder()
                                .text(euxpContents.sris_evt_comt_title)
                                .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd)
                                .callUrl(euxpContents.sris_evt_comt_call_url)
                                .vasId(euxpContents.sris_evt_comt_call_objt_id)
                                .vasServiceId(euxpContents.sris_evt_vas_svc_id)
                            .build()
                )
                .plccBanner(
                        SynopsisPlccBanner.builder()
                                .imagePath(euxpContents.sris_evt_comt_img_path2)
                                .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd2)
                                .callUrl(euxpContents.sris_evt_comt_call_url2)
                                .vasId(euxpContents.sris_evt_comt_call_objt_id2)
                                .vasServiceId(euxpContents.sris_evt_vas_svc_id2)
                            .build()
                ).build();
    }

    private static PurchaseInfo getPurchaseInfo(EuxpResult euxpResult) {

        List<Purchare> purchares = euxpResult.getPurchares();
        ArrayList<Product> products = new ArrayList<>();

        purchares.forEach(purchare -> products.add(Product.builder()
                .productTypeCode(purchare.prd_typ_cd)
                .episodeId(purchare.epsd_id)
                .episodeResolutionId(purchare.epsd_rslu_id)
                .discountTypeCode(purchare.dsc_typ_cd)
                .languageCaptionTypeCode(purchare.lag_capt_typ_cd)
                .isNscreen(purchare.nscrn_yn.equals(YES))
                .isPossession(purchare.possn_yn.equals(YES))
                .isPpmFreeJoin(purchare.ppm_free_join_yn.equals(YES))
                .ppmGridIconImagePath(purchare.ppm_grid_icon_img_path)
                .ppmProductName(purchare.ppm_prd_nm)
                .productPriceId(purchare.prd_prc_id)
                .productPriceVat((int) purchare.prd_prc_vat)
                .salePrice((int) purchare.sale_prc)
                .salePriceVat((int) purchare.sale_prc_vat)
                .purchasePreferenceRank(purchare.purc_pref_rank)
                .purchasedWatchedCount(purchare.purc_wat_dd_cnt + purchare.purc_wat_dd_fg_cd)
                .resolutionTypeCode(purchare.rslu_typ_cd)
                .isUsed(purchare.use_yn.equals(YES))
                .isReservation(euxpResult.getContents().rsv_orgnz_yn.equals(YES))
            .build()
        ));

        return PurchaseInfo.builder().products(products).build();
    }

    private static UserContentsPreference getUserContentsPreference(SmdResult smdResult, ScsResult scsResult) {
        UserContentsPreference userPreference = new UserContentsPreference();

        if (Objects.equals(smdResult.like, LIKE_INFO_CHOSEN)) {
            userPreference.setLikeInfo(LikeInfo.LIKE);
        } else if (Objects.equals(smdResult.dislike, LIKE_INFO_CHOSEN)) {
            userPreference.setLikeInfo(LikeInfo.DISLIKE);
        } else {
            userPreference.setLikeInfo(LikeInfo.NONE);
        }

        userPreference.setBookmark(Objects.equals(scsResult.is_bookmark, YES));

        return userPreference;
    }


    private static PlayInfo getPlayInfo(Contents euxpContents) {
        List<EpsdRsluInfo> sortedEpsdRsluInfo = euxpContents.epsd_rslu_info.stream()
                .sorted(Comparator.comparing(e -> e.rslu_typ_cd))
                .toList();
        EpsdRsluInfo epsdRsluInfo = sortedEpsdRsluInfo.get(FIRST_INDEX);

        MainPreviewPlay mainPreviewPlay = MainPreviewPlay.builder()
                .episodeId(euxpContents.epsd_id)
                .seriesId(euxpContents.sris_id)
                .episodeResolutionId(epsdRsluInfo.epsd_rslu_id)
                .startTime((int) epsdRsluInfo.openg_tmtag_tmsc)
                .previewTime(Integer.parseInt(epsdRsluInfo.preview_time))
                .totalTime(Integer.parseInt(euxpContents.play_time))
            .build();

        TrailerPlay trailerPlay = TrailerPlay.builder()
                .episodeId(euxpContents.epsd_id)
                .seriesId(euxpContents.sris_id)
                .productPriceId(euxpContents.preview.get(FIRST_INDEX).prd_prc_id)
            .build();

        AIHighlightPlay aiHighlightPlay;
        if (euxpContents.ai_inside_scenes.isEmpty()) {
            aiHighlightPlay = null;
        } else {
            aiHighlightPlay = AIHighlightPlay.builder()
                    .episodeId(euxpContents.epsd_id)
                    .seriesId(euxpContents.sris_id)
                    .startTime(Integer.valueOf(euxpContents.ai_inside_scenes.get(FIRST_INDEX).tmtag_fr_tmsc))
                    .previewTime(PREVIEW_TIME_FIXED)
                .build();
        }
        var corners = euxpContents.corners;
        List<CornerPlay> cornerPlays = Collections.emptyList();
        if (corners != null && (!euxpContents.corners.isEmpty())) {
                cornerPlays = euxpContents.corners.stream()
                        .map(corner ->
                                CornerPlay.builder()
                                        .cornerBottomName(corner.cnr_btm_nm)
                                        .cornerGroupName(corner.cnr_grp_id)
                                        .cornerId(corner.cnr_id)
                                        .cornerName(corner.cnr_nm)
                                        .episodeResolutionId(corner.epsd_rslu_id)
                                        .imagePath(corner.img_path)
                                        .build())
                        .collect(Collectors.toCollection(ArrayList::new));

        }

        List<SpecialPlay> specialPlays = Collections.emptyList();
        if (!euxpContents.special.isEmpty()) {
            specialPlays = euxpContents.special.stream()
                    .map(special ->
                            SpecialPlay.builder()
                                    .episodeResolutionId(special.epsd_rslu_id)
                                    .imagePath(special.img_path)
                                    .productPriceId(special.prd_prc_id)
                                    .title(special.title)
                                    .build())
                    .collect(Collectors.toCollection(ArrayList::new));
        }


        return PlayInfo.builder()
                .mainPreviewPlay(mainPreviewPlay)
                .trailerPlay(trailerPlay)
                .aiHighlightPlay(aiHighlightPlay)
                .cornerPlays(cornerPlays)
                .specialPlays(specialPlays)
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
        ArrayList<StillCut> stillCuts = new ArrayList<>();
        euxpContents.stillCut.forEach(still -> stillCuts.add(StillCut.builder().imagePath(still.img_path).build()));

        return ContentsAdditional.builder()
                .stillCut(stillCuts)
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

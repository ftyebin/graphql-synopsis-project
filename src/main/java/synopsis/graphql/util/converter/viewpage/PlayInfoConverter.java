package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.euxp.EpsdRsluInfo;
import synopsis.graphql.model.viewpage.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayInfoConverter implements ViewpageConverter<PlayInfo> {

    private static final int FIRST_INDEX = 0;
    private static final int PREVIEW_TIME_FIXED = 180;

    @Override
    public PlayInfo convert(Object... sources) {
        Contents euxpContents = (Contents) sources[0];

        MainPreviewPlay mainPreviewPlay = getMainPreviewPlay(euxpContents);
        TrailerPlay trailerPlay = getTrailerPlay(euxpContents);
        AIHighlightPlay aiHighlightPlay = getAiHighlightPlay(euxpContents);
        List<CornerPlay> cornerPlays = getCornerPlays(euxpContents);
        List<SpecialPlay> specialPlays = getSpecialPlays(euxpContents);

        return PlayInfo.builder()
                .mainPreviewPlay(mainPreviewPlay)
                .trailerPlay(trailerPlay)
                .aiHighlightPlay(aiHighlightPlay)
                .cornerPlays(cornerPlays)
                .specialPlays(specialPlays)
                .build();
    }

    private static TrailerPlay getTrailerPlay(Contents euxpContents) {
        return TrailerPlay.builder()
                .episodeId(euxpContents.epsd_id)
                .seriesId(euxpContents.sris_id)
                .productPriceId(euxpContents.preview.get(FIRST_INDEX).prd_prc_id)
                .build();
    }

    private static MainPreviewPlay getMainPreviewPlay(Contents euxpContents) {
        List<EpsdRsluInfo> sortedEpsdRsluInfo = euxpContents.epsd_rslu_info.stream()
                .sorted(Comparator.comparing(e -> e.rslu_typ_cd))
                .toList();
        EpsdRsluInfo epsdRsluInfo = sortedEpsdRsluInfo.get(FIRST_INDEX);

        return MainPreviewPlay.builder()
                .episodeId(euxpContents.epsd_id)
                .seriesId(euxpContents.sris_id)
                .episodeResolutionId(epsdRsluInfo.epsd_rslu_id)
                .startTime((int) epsdRsluInfo.openg_tmtag_tmsc)
                .previewTime(Integer.parseInt(epsdRsluInfo.preview_time))
                .totalTime(Integer.parseInt(euxpContents.play_time))
                .build();
    }

    private static AIHighlightPlay getAiHighlightPlay(Contents euxpContents) {
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
        return aiHighlightPlay;
    }

    private static List<SpecialPlay> getSpecialPlays(Contents euxpContents) {
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
        return specialPlays;
    }

    private static List<CornerPlay> getCornerPlays(Contents euxpContents) {
        var corners = euxpContents.corners;
        List<CornerPlay> cornerPlays = Collections.emptyList();
        if (corners != null && (!euxpContents.corners.isEmpty())) {
            cornerPlays = getCornerPlayList(euxpContents);
        }
        return cornerPlays;
    }

    private static List<CornerPlay> getCornerPlayList(Contents euxpContents) {
        List<CornerPlay> cornerPlays;
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
        return cornerPlays;
    }

}

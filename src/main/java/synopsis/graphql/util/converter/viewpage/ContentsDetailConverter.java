package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.euxp.People;
import synopsis.graphql.model.euxp.PrizeHistory;
import synopsis.graphql.model.viewpage.Cast;
import synopsis.graphql.model.viewpage.ContentsDetail;
import synopsis.graphql.model.viewpage.Prize;

import java.util.List;

@Service
public class ContentsDetailConverter implements ViewpageConverter<ContentsDetail> {

    @Override
    public ContentsDetail convert(Object... sources) {
        Contents euxpContents = (Contents) sources[0];

        List<Cast> casts = convertToCasts(euxpContents.peoples);
        List<Prize> prizes = convertToPrizes(euxpContents.site_review.prize_history);

        return ContentsDetail.builder()
                .summary(euxpContents.epsd_snss_cts)
                .castInfos(casts)
                .prizeInfos(prizes)
                .build();
    }

    private List<Cast> convertToCasts(List<People> peoples) {
        return peoples.stream()
                .map(people -> Cast.builder()
                        .birth(people.brth_ymd)
                        .imagePath(people.img_path)
                        .id(people.prs_id)
                        .actorName(people.prs_nm)
                        .castingName(people.prs_plrl_nm)
                        .roleCode(people.prs_role_cd)
                        .roleName(people.prs_role_nm)
                        .build())
                .toList();

    }

    private List<Prize> convertToPrizes(List<PrizeHistory> prizeHistories) {
        return prizeHistories.stream()
                .map(prizeHistory -> Prize.builder()
                        .name(prizeHistory.awardc_nm)
                        .description(prizeHistory.prize_dts_cts)
                        .year(prizeHistory.prize_yr)
                        .build())
                .toList();
    }
}
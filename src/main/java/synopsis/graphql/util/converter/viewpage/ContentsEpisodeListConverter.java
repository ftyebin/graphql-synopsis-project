package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.viewpage.ContentsEpisode;
import synopsis.graphql.model.viewpage.ContentsEpisodeList;

import java.util.Collections;
import java.util.Objects;

@Service
public class ContentsEpisodeListConverter implements ViewpageConverter<ContentsEpisodeList> {

    private static final String YES = "y";

    @Override
    public ContentsEpisodeList convert(Object... sources) {

        Contents euxpContents = (Contents) sources[0];

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
}

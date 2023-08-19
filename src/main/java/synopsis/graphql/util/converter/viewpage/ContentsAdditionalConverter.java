package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.viewpage.ContentsAdditional;
import synopsis.graphql.model.viewpage.SimilarContents;
import synopsis.graphql.model.viewpage.StillCut;

import java.util.ArrayList;

@Service
public class ContentsAdditionalConverter implements ViewpageConverter<ContentsAdditional> {
    @Override
    public ContentsAdditional convert(Object... sources) {
        Contents euxpContents = (Contents) sources[0];
        ArrayList<StillCut> stillCuts = new ArrayList<>();
        euxpContents.stillCut.forEach(still -> stillCuts.add(StillCut.builder().imagePath(still.img_path).build()));

        return ContentsAdditional.builder()
                .stillCut(stillCuts)
                .similarContents(SimilarContents.builder().callId(euxpContents.cw_call_id_val).build())
                .build();
    }
}
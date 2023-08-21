package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.model.viewpage.SynopsisType;

@Service
public class SynopsisTypeConverter implements ViewpageConverter<SynopsisType> {

    @Override
    public SynopsisType convert(Object... sources) {
        EuxpResult euxpResult = (EuxpResult) sources[0];

        if (euxpResult.series.isEmpty()){
            return SynopsisType.SHORTS;
        }
        return SynopsisType.SEASON;
    }
}

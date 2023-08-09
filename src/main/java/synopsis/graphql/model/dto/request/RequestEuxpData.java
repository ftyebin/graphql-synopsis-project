package synopsis.graphql.model.dto.request;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;

@Data
public class RequestEuxpData implements GraphQLInputType {

    private String stbId;
    private String synopsisSearchType; // search_type
    private String lookupType; // yn_recent
    private String menuStbServiceId;
    private String episodeId;


    @Override
    public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
        return null;
    }

    @Override
    public GraphQLSchemaElement copy() {
        return null;
    }
}

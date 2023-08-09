package synopsis.graphql.model.dto.request;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestScsPpvProduct implements GraphQLInputType {

    private String productPriceId;
    private String isNScreen;
    private String productTypeCode;
    private  String purchasePreferenceRank;
    private String isPossession;
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

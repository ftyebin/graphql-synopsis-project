package synopsis.graphql.model.dto.request;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;

import java.util.List;

@Data
public class RequestScsData implements GraphQLInputType {

    private String stbId;
    private String hashId;
    private String seriesId;
    private String synopsisSearchType;
    private String uiName;
    private List<RequestScsPpvProduct> ppvProducts;


    @Override
    public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
        return null;
    }

    @Override
    public GraphQLSchemaElement copy() {
        return null;
    }
}

package synopsis.graphql.model.dto.request;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;

@Data
public class RequestSmdData implements GraphQLInputType {
    private String mac;
    private String stbId;
    private String seriesId;

    public RequestSmdData() {}

    public RequestSmdData(RequestData requestData) {
        this.mac = requestData.getMac();
        this.stbId = requestData.getStbId();
        this.seriesId = requestData.getSeriesId();
    }

    @Override
    public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
        return null;
    }

    @Override
    public GraphQLSchemaElement copy() {
        return null;
    }
}

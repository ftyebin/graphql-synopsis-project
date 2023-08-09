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

    public RequestScsData() {}
    public RequestScsData(RequestData requestData) {
        this.stbId = requestData.getStbId();
        this.hashId = requestData.getHashId();
        this.uiName = requestData.getUiName();
        this.seriesId = requestData.getSeriesId();
        this.synopsisSearchType = requestData.getSynopsisSearchType();
        this.ppvProducts = requestData.getPpvProducts();
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

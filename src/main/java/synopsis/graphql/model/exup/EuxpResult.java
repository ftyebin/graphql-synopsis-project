package synopsis.graphql.model.exup;

import com.fasterxml.jackson.annotation.JsonProperty;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.schema.SchemaElementChildrenContainer;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;

import java.util.List;


@Data
public class EuxpResult implements GraphQLOutputType {
    private String result;
    public String reason;
    public String request_time;
    public Contents contents;
    public List<Purchare> purchares;
    public List<Object> series;
    public int total_banner_count;
    public String response_time;
    @JsonProperty("IF")
    public String iF;
    public List<Object> banners;

    @Override
    public List<GraphQLSchemaElement> getChildren() {
        return GraphQLOutputType.super.getChildren();
    }

    @Override
    public SchemaElementChildrenContainer getChildrenWithTypeReferences() {
        return GraphQLOutputType.super.getChildrenWithTypeReferences();
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

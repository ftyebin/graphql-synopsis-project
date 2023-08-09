package synopsis.graphql.model.smd;


import com.fasterxml.jackson.annotation.JsonProperty;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;

@Data
public class SmdResult implements GraphQLOutputType {
    public String result;
    public String reason;
    public String dislike_total;
    public String updateDate_total;
    public String like_total;
    public String dislike;
    public String like;
    @JsonProperty("IF")
    public String iF;
    public String updateDate;

    @Override
    public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
        return null;
    }

    @Override
    public GraphQLSchemaElement copy() {
        return null;
    }
}

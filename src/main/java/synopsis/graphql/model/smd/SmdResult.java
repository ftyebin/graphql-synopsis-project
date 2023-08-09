package synopsis.graphql.model.smd;


import com.fasterxml.jackson.annotation.JsonProperty;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class SmdResult implements GraphQLOutputType {
    public final String result;
    public final String reason;
    public final String dislike_total;
    public final String updateDate_total;
    public final String like_total;
    public final String dislike;
    public final String like;
    @JsonProperty("IF")
    public final String iF;
    public final String updateDate;

    @Override
    public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
        return null;
    }

    @Override
    public GraphQLSchemaElement copy() {
        return null;
    }
}

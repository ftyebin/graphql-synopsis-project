package synopsis.graphql.model.exup;

import com.fasterxml.jackson.annotation.JsonProperty;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.schema.SchemaElementChildrenContainer;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EuxpResult implements GraphQLOutputType {
    public final String result;
    public final String reason;
    public final String request_time;
    public final Contents contents;
    public final List<Purchare> purchares;
    public final List<Object> series;
    public final int total_banner_count;
    public final String response_time;
    @JsonProperty("IF")
    public final String iF;
    public final List<Object> banners;

    @Override
    public List<GraphQLSchemaElement> getChildren() {
        return GraphQLOutputType.super.getChildren();
    }

    @Override
    public SchemaElementChildrenContainer getChildrenWithTypeReferences() {
        return GraphQLOutputType.super.getChildrenWithTypeReferences();
    }

    @Override
    public GraphQLSchemaElement withNewChildren(SchemaElementChildrenContainer newChildren) {
        return GraphQLOutputType.super.withNewChildren(newChildren);
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

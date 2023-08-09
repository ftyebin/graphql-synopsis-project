package synopsis.graphql.model.dto.request;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.schema.SchemaElementChildrenContainer;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestData implements GraphQLInputType {
    private String stbId;
    private String mac;
    private String hashId;
    private String menuStbServiceId;
    private String seriesId;
    private String episodeId;
    private String lookupType;
    private String synopsisSearchType;
    private String uiName;
    private List<RequestScsPpvProduct> ppvProducts;

    @Override
    public List<GraphQLSchemaElement> getChildren() {
        return GraphQLInputType.super.getChildren();
    }

    @Override
    public SchemaElementChildrenContainer getChildrenWithTypeReferences() {
        return GraphQLInputType.super.getChildrenWithTypeReferences();
    }

    @Override
    public GraphQLSchemaElement withNewChildren(SchemaElementChildrenContainer newChildren) {
        return GraphQLInputType.super.withNewChildren(newChildren);
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

package synopsis.graphql.model.scs;

import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLTypeVisitor;
import graphql.schema.SchemaElementChildrenContainer;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.Getter;

import java.util.List;

@Getter
public class PpvProduct implements GraphQLOutputType {
    public String prd_prc_id;
    public String epsd_id;
    public String yn_directview;
    public String yn_purchase;
    public Object end_date;
    public Object end_date_hhmm;
    public String period;
    public String period_hour;
    public String period_min;
    public String yn_recv_gift;
    public String recv_gift_sts_cd;
    public Object ppm_free_join_yn;
    public Object ppm_free_join_perd_cd;
    public Object ppm_free_join_perd_end_dt;
    public Object use_ppv_omni_ppm_info;

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

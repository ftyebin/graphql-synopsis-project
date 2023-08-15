package synopsis.graphql.model.smd;

import lombok.Data;

@Data
public class SmdResult {
    public String result;
    public String reason;
    public String dislike_total;
    public String updateDate_total;
    public String like_total;
    public String dislike;
    public String like;
    public String IF;
    public String updateDate;
}

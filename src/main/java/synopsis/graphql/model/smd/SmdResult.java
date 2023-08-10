package synopsis.graphql.model.smd;


import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("IF")
    public String iF;
    public String updateDate;
}

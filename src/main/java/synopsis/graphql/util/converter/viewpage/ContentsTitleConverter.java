package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.viewpage.ContentsImageTitle;
import synopsis.graphql.model.viewpage.ContentsTextTitle;
import synopsis.graphql.model.viewpage.ContentsTitle;

import java.util.Objects;

@Service
public class ContentsTitleConverter implements ViewpageConverter<ContentsTitle> {

    private static final String YES = "y";

    @Override
    public ContentsTitle convert(Object... sources) {
        Contents euxpContents = (Contents) sources[0];

        return ContentsTitle.builder()
                .imageTitle(
                        ContentsImageTitle.builder()
                                .isDark(Objects.equals(euxpContents.dark_img_yn, YES)
                                        ? Boolean.TRUE
                                        : Boolean.FALSE)

                                .imagePath(euxpContents.title_img_path)
                                .build())
                .textTitle(ContentsTextTitle.builder().text(euxpContents.title).build())
                .build();
    }
}

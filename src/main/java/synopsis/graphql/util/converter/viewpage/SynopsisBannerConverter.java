package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.viewpage.SynopsisBanner;
import synopsis.graphql.model.viewpage.SynopsisImageBanner;
import synopsis.graphql.model.viewpage.SynopsisPlccBanner;
import synopsis.graphql.model.viewpage.SynopsisTextBanner;

@Service
public class SynopsisBannerConverter implements ViewpageConverter<SynopsisBanner> {

    @Override
    public SynopsisBanner convert(Object... sources) {
        Contents euxpContents = (Contents) sources[0];

        return SynopsisBanner.builder()
                .imageBanner(
                        SynopsisImageBanner.builder()
                                .imagePath(euxpContents.sris_evt_comt_img_path)
                                .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd2)
                                .callUrl(euxpContents.sris_evt_comt_call_url)
                                .vasId(euxpContents.sris_evt_comt_call_objt_id)
                                .vasServiceId(euxpContents.sris_evt_vas_svc_id)
                                .build())
                .textBanner(
                        SynopsisTextBanner.builder()
                                .text(euxpContents.sris_evt_comt_title)
                                .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd)
                                .callUrl(euxpContents.sris_evt_comt_call_url)
                                .vasId(euxpContents.sris_evt_comt_call_objt_id)
                                .vasServiceId(euxpContents.sris_evt_vas_svc_id)
                                .build()
                )
                .plccBanner(
                        SynopsisPlccBanner.builder()
                                .imagePath(euxpContents.sris_evt_comt_img_path2)
                                .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd2)
                                .callUrl(euxpContents.sris_evt_comt_call_url2)
                                .vasId(euxpContents.sris_evt_comt_call_objt_id2)
                                .vasServiceId(euxpContents.sris_evt_vas_svc_id2)
                                .build()
                ).build();
    }
}

package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.Contents;
import synopsis.graphql.model.viewpage.SynopsisBanner;
import synopsis.graphql.model.viewpage.SynopsisImageBanner;
import synopsis.graphql.model.viewpage.SynopsisPlccBanner;
import synopsis.graphql.model.viewpage.SynopsisTextBanner;

@Service
public class SynopsisBannerConverter implements ViewpageConverter<SynopsisBanner> {

    private static final String TEXT_BANNER_CODE = "10";
    private static final String IMAGE_BANNER_CODE = "20";
    private static final String PLCC_BANNER_CODE = "30";

    @Override
    public SynopsisBanner convert(Object... sources) {
        Contents euxpContents = (Contents) sources[0];

        SynopsisImageBanner synopsisImageBanner = null;
        SynopsisTextBanner synopsisTextBanner = null;
        SynopsisPlccBanner synopsisPlccBanner = null;

        if (euxpContents.sris_evt_comt_exps_mthd_cd.equals(TEXT_BANNER_CODE)) {
            synopsisTextBanner = SynopsisTextBanner.builder()
                    .text(euxpContents.sris_evt_comt_title)
                    .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd)
                    .callUrl(euxpContents.sris_evt_comt_call_url)
                    .vasId(euxpContents.sris_evt_comt_call_objt_id)
                    .vasServiceId(euxpContents.sris_evt_vas_svc_id)
                    .build();
        }  else if (euxpContents.sris_evt_comt_exps_mthd_cd.equals(IMAGE_BANNER_CODE)) {
            synopsisImageBanner = SynopsisImageBanner.builder()
                    .imagePath(euxpContents.sris_evt_comt_img_path)
                    .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd2)
                    .callUrl(euxpContents.sris_evt_comt_call_url)
                    .vasId(euxpContents.sris_evt_comt_call_objt_id)
                    .vasServiceId(euxpContents.sris_evt_vas_svc_id)
                    .build();
        }
        if (euxpContents.sris_evt_comt_exps_mthd_cd2.equals(PLCC_BANNER_CODE)) {
            synopsisPlccBanner = SynopsisPlccBanner.builder()
                    .imagePath(euxpContents.sris_evt_comt_img_path2)
                    .callTypeCode(euxpContents.sris_evt_comt_call_typ_cd2)
                    .callUrl(euxpContents.sris_evt_comt_call_url2)
                    .vasId(euxpContents.sris_evt_comt_call_objt_id2)
                    .vasServiceId(euxpContents.sris_evt_vas_svc_id2)
                    .build();
        }

        return SynopsisBanner.builder()
                .imageBanner(synopsisImageBanner)
                .textBanner(synopsisTextBanner)
                .plccBanner(synopsisPlccBanner).build();
    }
}

package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.model.euxp.Purchare;
import synopsis.graphql.model.viewpage.Product;
import synopsis.graphql.model.viewpage.PurchaseInfo;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseInfoConverter implements ViewpageConverter<PurchaseInfo> {

    private static final String YES = "y";

    @Override
    public PurchaseInfo convert(Object... sources) {

        EuxpResult euxpResult = (EuxpResult) sources[0];

        List<Purchare> purchares = euxpResult.getPurchares();
        ArrayList<Product> products = new ArrayList<>();

        purchares.forEach(purchare -> products.add(Product.builder()
                .productTypeCode(purchare.prd_typ_cd)
                .episodeId(purchare.epsd_id)
                .episodeResolutionId(purchare.epsd_rslu_id)
                .discountTypeCode(purchare.dsc_typ_cd)
                .languageCaptionTypeCode(purchare.lag_capt_typ_cd)
                .isNscreen(purchare.nscrn_yn.equals(YES))
                .isPossession(purchare.possn_yn.equals(YES))
                .isPpmFreeJoin(purchare.ppm_free_join_yn.equals(YES))
                .ppmGridIconImagePath(purchare.ppm_grid_icon_img_path)
                .ppmProductName(purchare.ppm_prd_nm)
                .productPriceId(purchare.prd_prc_id)
                .productPriceVat((int) purchare.prd_prc_vat)
                .salePrice((int) purchare.sale_prc)
                .salePriceVat((int) purchare.sale_prc_vat)
                .purchasePreferenceRank(purchare.purc_pref_rank)
                .purchasedWatchedCount(purchare.purc_wat_dd_cnt + purchare.purc_wat_dd_fg_cd)
                .resolutionTypeCode(purchare.rslu_typ_cd)
                .isUsed(purchare.use_yn.equals(YES))
                .isReservation(euxpResult.getContents().rsv_orgnz_yn.equals(YES))
                .build()
        ));

        return PurchaseInfo.builder().products(products).build();
    }
}

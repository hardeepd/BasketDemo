package uk.co.hd_tech.basketdemo.data.model;


import uk.co.hd_tech.basketdemo.R;

/**
 * Basket item units
 */
public enum Unit {

    PACK(R.string.unit_pack),
    BAG(R.string.unit_bag),
    DOZEN(R.string.unit_dozen),
    BOTTLE(R.string.unit_bottle),
    CAN(R.string.unit_can);

    private int stringId;

    Unit(int stringId) {
        this.stringId = stringId;
    }

    public int getStringId() {
        return stringId;
    }
}

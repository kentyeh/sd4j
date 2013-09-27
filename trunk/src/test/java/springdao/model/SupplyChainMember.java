package springdao.model;

import java.util.Locale;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Kent Yeh
 */
public enum SupplyChainMember {

    /**
     * Customer(客戶)
     */
    C,
    /**
     * Vender(供應商)
     */
    V,
    /**
     * WareHousing(倉儲業者)
     */
    W;
    private static Logger logger = LogManager.getLogger(SupplyChainMember.class);
    private Locale descriptLocale = Locale.getDefault();

    public void setDescriptLocale(Locale descriptLocale) {
        this.descriptLocale = descriptLocale;
    }

    public Locale getDescriptLocale() {
        return descriptLocale;
    }

    @Override
    public String toString() {
        return V.equals(this) ? "Vender" : W.equals(this) ? "WareHousing" : "Customer";
    }
}

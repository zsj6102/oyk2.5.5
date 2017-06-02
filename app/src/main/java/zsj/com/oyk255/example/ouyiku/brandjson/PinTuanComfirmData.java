package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinTuanComfirmData {

    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("brand_title")
    @Expose
    private String brandTitle;
    @SerializedName("brand_logo")
    @Expose
    private String brandLogo;
    @Expose
    private String title;
    @Expose
    private String photo;
    @Expose
    private String pnum;
    @SerializedName("single_price")
    @Expose
    private String singlePrice;
    @Expose
    private String sku;
    @Expose
    private String youhui;
    @Expose
    private String xiaoji;
    @Expose
    private String total;

    /**
     * 
     * @return
     *     The brandId
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * 
     * @param brandId
     *     The brand_id
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    /**
     * 
     * @return
     *     The brandTitle
     */
    public String getBrandTitle() {
        return brandTitle;
    }

    /**
     * 
     * @param brandTitle
     *     The brand_title
     */
    public void setBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
    }

    /**
     * 
     * @return
     *     The brandLogo
     */
    public String getBrandLogo() {
        return brandLogo;
    }

    /**
     * 
     * @param brandLogo
     *     The brand_logo
     */
    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 
     * @param photo
     *     The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 
     * @return
     *     The pnum
     */
    public String getPnum() {
        return pnum;
    }

    /**
     * 
     * @param pnum
     *     The pnum
     */
    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    /**
     * 
     * @return
     *     The singlePrice
     */
    public String getSinglePrice() {
        return singlePrice;
    }

    /**
     * 
     * @param singlePrice
     *     The single_price
     */
    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }

    /**
     * 
     * @return
     *     The sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * 
     * @param sku
     *     The sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * 
     * @return
     *     The youhui
     */
    public String getYouhui() {
        return youhui;
    }

    /**
     * 
     * @param youhui
     *     The youhui
     */
    public void setYouhui(String youhui) {
        this.youhui = youhui;
    }

    /**
     * 
     * @return
     *     The xiaoji
     */
    public String getXiaoji() {
        return xiaoji;
    }

    /**
     * 
     * @param xiaoji
     *     The xiaoji
     */
    public void setXiaoji(String xiaoji) {
        this.xiaoji = xiaoji;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

}

package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultDatum {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;
    @Expose
    private String title;
    @SerializedName("old_price")
    @Expose
    private String oldPrice;
    @SerializedName("new_price")
    @Expose
    private String newPrice;
    @Expose
    private String rebate;

    /**
     * 
     * @return
     *     The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId
     *     The product_id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 
     * @return
     *     The picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 
     * @param picUrl
     *     The pic_url
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
     *     The oldPrice
     */
    public String getOldPrice() {
        return oldPrice;
    }

    /**
     * 
     * @param oldPrice
     *     The old_price
     */
    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    /**
     * 
     * @return
     *     The newPrice
     */
    public String getNewPrice() {
        return newPrice;
    }

    /**
     * 
     * @param newPrice
     *     The new_price
     */
    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    /**
     * 
     * @return
     *     The rebate
     */
    public String getRebate() {
        return rebate;
    }

    /**
     * 
     * @param rebate
     *     The rebate
     */
    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

}

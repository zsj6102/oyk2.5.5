package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinTuanTuiJianDatum {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("groups_id")
    @Expose
    private String groupsId;
    @SerializedName("add_time")
    @Expose
    private String addTime;
    @Expose
    private String buynum;
    @SerializedName("buy_number")
    @Expose
    private String buyNumber;
    @Expose
    private String sku;
    @SerializedName("groups_price")
    @Expose
    private String groupsPrice;
    @SerializedName("single_price")
    @Expose
    private String singlePrice;
    @Expose
    private String photo;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @Expose
    private String title;

    /**
     * 
     * @return
     *     The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * @return
     *     The groupsId
     */
    public String getGroupsId() {
        return groupsId;
    }

    /**
     * 
     * @param groupsId
     *     The groups_id
     */
    public void setGroupsId(String groupsId) {
        this.groupsId = groupsId;
    }

    /**
     * 
     * @return
     *     The addTime
     */
    public String getAddTime() {
        return addTime;
    }

    /**
     * 
     * @param addTime
     *     The add_time
     */
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /**
     * 
     * @return
     *     The buynum
     */
    public String getBuynum() {
        return buynum;
    }

    /**
     * 
     * @param buynum
     *     The buynum
     */
    public void setBuynum(String buynum) {
        this.buynum = buynum;
    }

    /**
     * 
     * @return
     *     The buyNumber
     */
    public String getBuyNumber() {
        return buyNumber;
    }

    /**
     * 
     * @param buyNumber
     *     The buy_number
     */
    public void setBuyNumber(String buyNumber) {
        this.buyNumber = buyNumber;
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
     *     The groupsPrice
     */
    public String getGroupsPrice() {
        return groupsPrice;
    }

    /**
     * 
     * @param groupsPrice
     *     The groups_price
     */
    public void setGroupsPrice(String groupsPrice) {
        this.groupsPrice = groupsPrice;
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

}

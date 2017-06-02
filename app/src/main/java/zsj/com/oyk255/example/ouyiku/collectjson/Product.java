package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("groups_id")
    @Expose
    private String groupsId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @Expose
    private String begintime;
    @Expose
    private String status;
    @Expose
    private String endtime;
    @Expose
    private String sku;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @SerializedName("groups_price")
    @Expose
    private String groupsPrice;
    @SerializedName("groups_number")
    @Expose
    private String groupsNumber;
    @SerializedName("single_price")
    @Expose
    private String singlePrice;
    @SerializedName("single_number")
    @Expose
    private String singleNumber;
    @SerializedName("buy_number")
    @Expose
    private String buyNumber;
    @Expose
    private String photo;
    @Expose
    private String type;
    @Expose
    private String title;

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
     *     The merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 
     * @param merchantId
     *     The merchant_id
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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
     *     The begintime
     */
    public String getBegintime() {
        return begintime;
    }

    /**
     * 
     * @param begintime
     *     The begintime
     */
    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The endtime
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * 
     * @param endtime
     *     The endtime
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
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
     *     The createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The create_time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
     *     The groupsNumber
     */
    public String getGroupsNumber() {
        return groupsNumber;
    }

    /**
     * 
     * @param groupsNumber
     *     The groups_number
     */
    public void setGroupsNumber(String groupsNumber) {
        this.groupsNumber = groupsNumber;
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
     *     The singleNumber
     */
    public String getSingleNumber() {
        return singleNumber;
    }

    /**
     * 
     * @param singleNumber
     *     The single_number
     */
    public void setSingleNumber(String singleNumber) {
        this.singleNumber = singleNumber;
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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
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

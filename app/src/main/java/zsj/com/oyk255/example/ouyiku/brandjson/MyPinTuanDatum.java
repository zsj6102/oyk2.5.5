package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPinTuanDatum {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @Expose
    private String ordernum;
    @Expose
    private String buynum;
    @Expose
    private String gid;
    @SerializedName("groups_id")
    @Expose
    private String groupsId;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @Expose
    private String status;
    @SerializedName("buy_number")
    @Expose
    private String buyNumber;
    @SerializedName("groups_price")
    @Expose
    private String groupsPrice;
    @Expose
    private String photo;
    @Expose
    private String sku;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @Expose
    private String endtime;
    @Expose
    private String title;
    @Expose
    private String strstatus;
    @Expose
    private Brand brand;
    @Expose
    private String tuan_id;

    

	/**
     * 
     * @return
     *     The orderId
     */
    
    public String getTuan_id() {
    	return tuan_id;
    }
    
    public void setTuan_id(String tuan_id) {
    	this.tuan_id = tuan_id;
    }
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
     *     The ordernum
     */
    public String getOrdernum() {
        return ordernum;
    }

    /**
     * 
     * @param ordernum
     *     The ordernum
     */
    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
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
     *     The gid
     */
    public String getGid() {
        return gid;
    }

    /**
     * 
     * @param gid
     *     The gid
     */
    public void setGid(String gid) {
        this.gid = gid;
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
     *     The strstatus
     */
    public String getStrstatus() {
        return strstatus;
    }

    /**
     * 
     * @param strstatus
     *     The strstatus
     */
    public void setStrstatus(String strstatus) {
        this.strstatus = strstatus;
    }

    /**
     * 
     * @return
     *     The brand
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * 
     * @param brand
     *     The brand
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}

package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradingDatum {

    @SerializedName("flow_id")
    @Expose
    private String flowId;
    @Expose
    private String number;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @SerializedName("pay_time")
    @Expose
    private String payTime;
    @Expose
    private String money;
    @Expose
    private String status;
    @Expose
    private String type;
    @Expose
    private String content;

    /**
     * 
     * @return
     *     The flowId
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * 
     * @param flowId
     *     The flow_id
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     *     The adminId
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * 
     * @param adminId
     *     The admin_id
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

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
     *     The payTime
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * 
     * @param payTime
     *     The pay_time
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    /**
     * 
     * @return
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
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
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

}

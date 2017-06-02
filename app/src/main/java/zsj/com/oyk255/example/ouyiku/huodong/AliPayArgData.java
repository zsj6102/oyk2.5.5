package zsj.com.oyk255.example.ouyiku.huodong;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AliPayArgData {

    @SerializedName("_input_charset")
    @Expose
    private String InputCharset;
    @Expose
    private String body;
    @SerializedName("notify_url")
    @Expose
    private String notifyUrl;
    @SerializedName("out_trade_no")
    @Expose
    private String outTradeNo;
    @Expose
    private String partner;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @Expose
    private String service;
    @Expose
    private String sign;
    @SerializedName("sign_type")
    @Expose
    private String signType;
    @Expose
    private String subject;
    @SerializedName("total_fee")
    @Expose
    private String totalFee;

    /**
     * 
     * @return
     *     The InputCharset
     */
    public String getInputCharset() {
        return InputCharset;
    }

    /**
     * 
     * @param InputCharset
     *     The _input_charset
     */
    public void setInputCharset(String InputCharset) {
        this.InputCharset = InputCharset;
    }

    /**
     * 
     * @return
     *     The body
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body
     *     The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 
     * @return
     *     The notifyUrl
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 
     * @param notifyUrl
     *     The notify_url
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 
     * @return
     *     The outTradeNo
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * 
     * @param outTradeNo
     *     The out_trade_no
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    /**
     * 
     * @return
     *     The partner
     */
    public String getPartner() {
        return partner;
    }

    /**
     * 
     * @param partner
     *     The partner
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * 
     * @return
     *     The paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * 
     * @param paymentType
     *     The payment_type
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 
     * @return
     *     The sellerId
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * 
     * @param sellerId
     *     The seller_id
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 
     * @return
     *     The service
     */
    public String getService() {
        return service;
    }

    /**
     * 
     * @param service
     *     The service
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * 
     * @return
     *     The sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * 
     * @param sign
     *     The sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 
     * @return
     *     The signType
     */
    public String getSignType() {
        return signType;
    }

    /**
     * 
     * @param signType
     *     The sign_type
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * 
     * @return
     *     The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject
     *     The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 
     * @return
     *     The totalFee
     */
    public String getTotalFee() {
        return totalFee;
    }

    /**
     * 
     * @param totalFee
     *     The total_fee
     */
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

}

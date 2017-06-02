package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WuLiuListDatum {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @Expose
    private String ordernum;
    @Expose
    private String price;
    @Expose
    private String expressname;
    @Expose
    private String expressnum;
    @Expose
    private String exphone;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @Expose
    private String phone1;
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
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The expressname
     */
    public String getExpressname() {
        return expressname;
    }

    /**
     * 
     * @param expressname
     *     The expressname
     */
    public void setExpressname(String expressname) {
        this.expressname = expressname;
    }

    /**
     * 
     * @return
     *     The expressnum
     */
    public String getExpressnum() {
        return expressnum;
    }

    /**
     * 
     * @param expressnum
     *     The expressnum
     */
    public void setExpressnum(String expressnum) {
        this.expressnum = expressnum;
    }

    /**
     * 
     * @return
     *     The exphone
     */
    public String getExphone() {
        return exphone;
    }

    /**
     * 
     * @param exphone
     *     The exphone
     */
    public void setExphone(String exphone) {
        this.exphone = exphone;
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
     *     The phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * 
     * @param phone1
     *     The phone1
     */
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
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

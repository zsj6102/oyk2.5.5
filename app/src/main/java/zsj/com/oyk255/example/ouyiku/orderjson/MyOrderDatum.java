package zsj.com.oyk255.example.ouyiku.orderjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrderDatum {

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
    @Expose
    private String status;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @Expose
    private List<Good> goods = new ArrayList<Good>();

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
     *     The goods
     */
    public List<Good> getGoods() {
        return goods;
    }

    /**
     * 
     * @param goods
     *     The goods
     */
    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

}

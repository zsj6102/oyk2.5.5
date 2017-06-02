package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSaleListDatum {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @Expose
    private String begintime;
    @Expose
    private String endtime;
    @Expose
    private String title;
    @Expose
    private String phone1;
    @Expose
    private String price;
    @Expose
    private String rebate;
    @Expose
    private String marketprice;
    @Expose
    private String currprice;
    @Expose
    private String stock;
    @Expose
    private String attr;

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

    /**
     * 
     * @return
     *     The marketprice
     */
    public String getMarketprice() {
        return marketprice;
    }

    /**
     * 
     * @param marketprice
     *     The marketprice
     */
    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    /**
     * 
     * @return
     *     The currprice
     */
    public String getCurrprice() {
        return currprice;
    }

    /**
     * 
     * @param currprice
     *     The currprice
     */
    public void setCurrprice(String currprice) {
        this.currprice = currprice;
    }

    /**
     * 
     * @return
     *     The stock
     */
    public String getStock() {
        return stock;
    }

    /**
     * 
     * @param stock
     *     The stock
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * 
     * @return
     *     The attr
     */
    public String getAttr() {
        return attr;
    }

    /**
     * 
     * @param attr
     *     The attr
     */
    public void setAttr(String attr) {
        this.attr = attr;
    }

}

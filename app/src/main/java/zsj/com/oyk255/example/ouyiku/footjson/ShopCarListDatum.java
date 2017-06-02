package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopCarListDatum {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @Expose
    private String number;
    @Expose
    private String price;
    @Expose
    private String marketprice;
    @Expose
    private String rebate;
    @Expose
    private String stock;
    @Expose
    private String title;
    @SerializedName("brandmerchant_id")
    @Expose
    private String brandmerchantId;
    @Expose
    private String brandname;
    @Expose
    private String image;
    @Expose
    private String attr;

    /**
     * 
     * @return
     *     The cartId
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * 
     * @param cartId
     *     The cart_id
     */
    public void setCartId(String cartId) {
        this.cartId = cartId;
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
     *     The brandmerchantId
     */
    public String getBrandmerchantId() {
        return brandmerchantId;
    }

    /**
     * 
     * @param brandmerchantId
     *     The brandmerchant_id
     */
    public void setBrandmerchantId(String brandmerchantId) {
        this.brandmerchantId = brandmerchantId;
    }

    /**
     * 
     * @return
     *     The brandname
     */
    public String getBrandname() {
        return brandname;
    }

    /**
     * 
     * @param brandname
     *     The brandname
     */
    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
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

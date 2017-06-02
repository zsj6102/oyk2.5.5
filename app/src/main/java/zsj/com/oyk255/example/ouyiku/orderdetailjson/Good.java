package zsj.com.oyk255.example.ouyiku.orderdetailjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Good {

    @Expose
    private String image;
    @Expose
    private String title;
    @Expose
    private String num;
    @Expose
    private String price;
    @Expose
    private String brandtitle;
    @Expose
    private String brandlogo;
    @SerializedName("brandmerchant_id")
    @Expose
    private String brandmerchantId;
    @Expose
    private String attr;
    @SerializedName("is_review")
    @Expose
    private String isReview;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("goods_id")
    @Expose
    private String goodsId;

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
     *     The num
     */
    public String getNum() {
        return num;
    }

    /**
     * 
     * @param num
     *     The num
     */
    public void setNum(String num) {
        this.num = num;
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
     *     The brandtitle
     */
    public String getBrandtitle() {
        return brandtitle;
    }

    /**
     * 
     * @param brandtitle
     *     The brandtitle
     */
    public void setBrandtitle(String brandtitle) {
        this.brandtitle = brandtitle;
    }

    /**
     * 
     * @return
     *     The brandlogo
     */
    public String getBrandlogo() {
        return brandlogo;
    }

    /**
     * 
     * @param brandlogo
     *     The brandlogo
     */
    public void setBrandlogo(String brandlogo) {
        this.brandlogo = brandlogo;
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

    /**
     * 
     * @return
     *     The isReview
     */
    public String getIsReview() {
        return isReview;
    }

    /**
     * 
     * @param isReview
     *     The is_review
     */
    public void setIsReview(String isReview) {
        this.isReview = isReview;
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
     *     The goodsId
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 
     * @param goodsId
     *     The goods_id
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

}

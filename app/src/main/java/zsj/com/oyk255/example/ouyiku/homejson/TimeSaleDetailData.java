package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSaleDetailData {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("fpro_id")
    @Expose
    private String fproId;
    @Expose
    private String islink;
    @Expose
    private String begintime;
    @Expose
    private String endtime;
    @Expose
    private String ltime;
    @Expose
    private String currtime;
    @Expose
    private String title;
    @SerializedName("product_image")
    @Expose
    private List<String> productImage = new ArrayList<String>();
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
    private String isbuy;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    @Expose
    private String attr;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @Expose
    private String brandtitle;
    @Expose
    private String logo;
    @Expose
    private String brandsalenum;
    @Expose
    private String brandtotal;
    public String getCollectnum() {
		return collectnum;
	}

	public void setCollectnum(String collectnum) {
		this.collectnum = collectnum;
	}

	@Expose
    private String isbcollect;
    @SerializedName("pro_detail")
    @Expose
    private String proDetail;
    
    private String collectnum;

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
     *     The fproId
     */
    public String getFproId() {
        return fproId;
    }

    /**
     * 
     * @param fproId
     *     The fpro_id
     */
    public void setFproId(String fproId) {
        this.fproId = fproId;
    }

    /**
     * 
     * @return
     *     The islink
     */
    public String getIslink() {
        return islink;
    }

    /**
     * 
     * @param islink
     *     The islink
     */
    public void setIslink(String islink) {
        this.islink = islink;
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
     *     The ltime
     */
    public String getLtime() {
        return ltime;
    }

    /**
     * 
     * @param ltime
     *     The ltime
     */
    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

    /**
     * 
     * @return
     *     The currtime
     */
    public String getCurrtime() {
        return currtime;
    }

    /**
     * 
     * @param currtime
     *     The currtime
     */
    public void setCurrtime(String currtime) {
        this.currtime = currtime;
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
     *     The productImage
     */
    public List<String> getProductImage() {
        return productImage;
    }

    /**
     * 
     * @param productImage
     *     The product_image
     */
    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
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
     *     The isbuy
     */
    public String getIsbuy() {
        return isbuy;
    }

    /**
     * 
     * @param isbuy
     *     The isbuy
     */
    public void setIsbuy(String isbuy) {
        this.isbuy = isbuy;
    }

    /**
     * 
     * @return
     *     The shareUrl
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     * 
     * @param shareUrl
     *     The share_url
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
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
     *     The brandId
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * 
     * @param brandId
     *     The brand_id
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
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
     *     The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 
     * @param logo
     *     The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 
     * @return
     *     The brandsalenum
     */
    public String getBrandsalenum() {
        return brandsalenum;
    }

    /**
     * 
     * @param brandsalenum
     *     The brandsalenum
     */
    public void setBrandsalenum(String brandsalenum) {
        this.brandsalenum = brandsalenum;
    }

    /**
     * 
     * @return
     *     The brandtotal
     */
    public String getBrandtotal() {
        return brandtotal;
    }

    /**
     * 
     * @param brandtotal
     *     The brandtotal
     */
    public void setBrandtotal(String brandtotal) {
        this.brandtotal = brandtotal;
    }

    /**
     * 
     * @return
     *     The isbcollect
     */
    public String getIsbcollect() {
        return isbcollect;
    }

    /**
     * 
     * @param isbcollect
     *     The isbcollect
     */
    public void setIsbcollect(String isbcollect) {
        this.isbcollect = isbcollect;
    }

    /**
     * 
     * @return
     *     The proDetail
     */
    public String getProDetail() {
        return proDetail;
    }

    /**
     * 
     * @param proDetail
     *     The pro_detail
     */
    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

}
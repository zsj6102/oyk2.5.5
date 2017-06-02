package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NineDetailData {

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
    @Expose
    private String phone1;
    @Expose
    private String phone2;
    @Expose
    private String phone3;
    @Expose
    private String phone4;
    @Expose
    private String phone5;
    @Expose
    private String phone6;
   

	@Expose
    private String price;
    @Expose
    private String rebate;
    @SerializedName("m_content")
    @Expose
    private List<MContent> mContent = new ArrayList<MContent>();
    @SerializedName("use_id")
    @Expose
    private String useId;
    @Expose
    private String marketprice;
    @Expose
    private String currprice;
    @Expose
    private String stock;
    @Expose
    private String isbuy;
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
     *     The phone2
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * 
     * @param phone2
     *     The phone2
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     * 
     * @return
     *     The phone3
     */
    public String getPhone3() {
        return phone3;
    }

    /**
     * 
     * @param phone3
     *     The phone3
     */
    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    /**
     * 
     * @return
     *     The phone4
     */
    public String getPhone4() {
        return phone4;
    }

    /**
     * 
     * @param phone4
     *     The phone4
     */
    public void setPhone4(String phone4) {
        this.phone4 = phone4;
    }

    /**
     * 
     * @return
     *     The phone5
     */
    public String getPhone5() {
        return phone5;
    }

    /**
     * 
     * @param phone5
     *     The phone5
     */
    public void setPhone5(String phone5) {
        this.phone5 = phone5;
    }

    /**
     * 
     * @return
     *     The phone6
     */
    public String getPhone6() {
        return phone6;
    }

    /**
     * 
     * @param phone6
     *     The phone6
     */
    public void setPhone6(String phone6) {
        this.phone6 = phone6;
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
     *     The mContent
     */
    public List<MContent> getMContent() {
        return mContent;
    }

    /**
     * 
     * @param mContent
     *     The m_content
     */
    public void setMContent(List<MContent> mContent) {
        this.mContent = mContent;
    }

    /**
     * 
     * @return
     *     The useId
     */
    public String getUseId() {
        return useId;
    }

    /**
     * 
     * @param useId
     *     The use_id
     */
    public void setUseId(String useId) {
        this.useId = useId;
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
    
    public String getLtime() {
		return ltime;
	}

	public void setLtime(String ltime) {
		this.ltime = ltime;
	}

}

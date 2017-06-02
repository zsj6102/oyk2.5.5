package zsj.com.oyk255.example.ouyiku.brandjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedBagListDatum {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String money;
    @Expose
    private String condition;
    @Expose
    private String createnum;
    @SerializedName("send_num")
    @Expose
    private String sendNum;
    @SerializedName("bm_id")
    @Expose
    private String bmId;
    @Expose
    private String btitle;
    @Expose
    private String blogo;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("active_title")
    @Expose
    private String activeTitle;
    @Expose
    private List<Product> product = new ArrayList<Product>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
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
     *     The condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * 
     * @param condition
     *     The condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 
     * @return
     *     The createnum
     */
    public String getCreatenum() {
        return createnum;
    }

    /**
     * 
     * @param createnum
     *     The createnum
     */
    public void setCreatenum(String createnum) {
        this.createnum = createnum;
    }

    /**
     * 
     * @return
     *     The sendNum
     */
    public String getSendNum() {
        return sendNum;
    }

    /**
     * 
     * @param sendNum
     *     The send_num
     */
    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    /**
     * 
     * @return
     *     The bmId
     */
    public String getBmId() {
        return bmId;
    }

    /**
     * 
     * @param bmId
     *     The bm_id
     */
    public void setBmId(String bmId) {
        this.bmId = bmId;
    }

    /**
     * 
     * @return
     *     The btitle
     */
    public String getBtitle() {
        return btitle;
    }

    /**
     * 
     * @param btitle
     *     The btitle
     */
    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    /**
     * 
     * @return
     *     The blogo
     */
    public String getBlogo() {
        return blogo;
    }

    /**
     * 
     * @param blogo
     *     The blogo
     */
    public void setBlogo(String blogo) {
        this.blogo = blogo;
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
     *     The activeTitle
     */
    public String getActiveTitle() {
        return activeTitle;
    }

    /**
     * 
     * @param activeTitle
     *     The active_title
     */
    public void setActiveTitle(String activeTitle) {
        this.activeTitle = activeTitle;
    }

    /**
     * 
     * @return
     *     The product
     */
    public List<Product> getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(List<Product> product) {
        this.product = product;
    }

}

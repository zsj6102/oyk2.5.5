package zsj.com.oyk255.example.ouyiku.collectjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CanTuanDetailData {

    @Expose
    private Product product;
    @Expose
    private List<String> user = new ArrayList<String>();
    @Expose
    private String isjoin;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @Expose
    private String endtime;
    @Expose
    private String numleft;
    @Expose
    private String endmin;
    @Expose
    private String link;
    @Expose
    private String tcode;



	/**
     * 
     * @return
     *     The product
     */
    
    public String getTcode() {
    	return tcode;
    }
    
    public void setTcode(String tcode) {
    	this.tcode = tcode;
    }
    
    public String getLink() {
    	return link;
    }
    
    public void setLink(String link) {
    	this.link = link;
    }
    public Product getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * 
     * @return
     *     The user
     */
    public List<String> getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(List<String> user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The isjoin
     */
    public String getIsjoin() {
        return isjoin;
    }

    /**
     * 
     * @param isjoin
     *     The isjoin
     */
    public void setIsjoin(String isjoin) {
        this.isjoin = isjoin;
    }

    /**
     * 
     * @return
     *     The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime
     *     The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
     *     The numleft
     */
    public String getNumleft() {
        return numleft;
    }

    /**
     * 
     * @param numleft
     *     The numleft
     */
    public void setNumleft(String numleft) {
        this.numleft = numleft;
    }

    /**
     * 
     * @return
     *     The endmin
     */
    public String getEndmin() {
        return endmin;
    }

    /**
     * 
     * @param endmin
     *     The endmin
     */
    public void setEndmin(String endmin) {
        this.endmin = endmin;
    }

}

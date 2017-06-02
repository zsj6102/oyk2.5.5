package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datainfo {

    @Expose
    private String title;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @Expose
    private String background;
    @SerializedName("is_collect")
    @Expose
    private String isCollect;
    @Expose
    private String pers;
    @Expose
    private String pnumber;
    @Expose
    private String cnumber;
    @Expose
    private String evaluationg;
    @Expose
    private String evaluationm;
    @Expose
    private String evaluationb;
    @Expose
    private String evaluationn;
    @Expose
    private String bsalesum;


	/**
     * 
     * @return
     *     The title
     */
    public String getBsalesum() {
    	return bsalesum;
    }
    
    public void setBsalesum(String bsalesum) {
    	this.bsalesum = bsalesum;
    }
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
     *     The background
     */
    public String getBackground() {
        return background;
    }

    /**
     * 
     * @param background
     *     The background
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * 
     * @return
     *     The isCollect
     */
    public String getIsCollect() {
        return isCollect;
    }

    /**
     * 
     * @param isCollect
     *     The is_collect
     */
    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    /**
     * 
     * @return
     *     The pers
     */
    public String getPers() {
        return pers;
    }

    /**
     * 
     * @param pers
     *     The pers
     */
    public void setPers(String pers) {
        this.pers = pers;
    }

    /**
     * 
     * @return
     *     The pnumber
     */
    public String getPnumber() {
        return pnumber;
    }

    /**
     * 
     * @param pnumber
     *     The pnumber
     */
    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    /**
     * 
     * @return
     *     The cnumber
     */
    public String getCnumber() {
        return cnumber;
    }

    /**
     * 
     * @param cnumber
     *     The cnumber
     */
    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    /**
     * 
     * @return
     *     The evaluationg
     */
    public String getEvaluationg() {
        return evaluationg;
    }

    /**
     * 
     * @param evaluationg
     *     The evaluationg
     */
    public void setEvaluationg(String evaluationg) {
        this.evaluationg = evaluationg;
    }

    /**
     * 
     * @return
     *     The evaluationm
     */
    public String getEvaluationm() {
        return evaluationm;
    }

    /**
     * 
     * @param evaluationm
     *     The evaluationm
     */
    public void setEvaluationm(String evaluationm) {
        this.evaluationm = evaluationm;
    }

    /**
     * 
     * @return
     *     The evaluationb
     */
    public String getEvaluationb() {
        return evaluationb;
    }

    /**
     * 
     * @param evaluationb
     *     The evaluationb
     */
    public void setEvaluationb(String evaluationb) {
        this.evaluationb = evaluationb;
    }

    /**
     * 
     * @return
     *     The evaluationn
     */
    public String getEvaluationn() {
        return evaluationn;
    }

    /**
     * 
     * @param evaluationn
     *     The evaluationn
     */
    public void setEvaluationn(String evaluationn) {
        this.evaluationn = evaluationn;
    }

}

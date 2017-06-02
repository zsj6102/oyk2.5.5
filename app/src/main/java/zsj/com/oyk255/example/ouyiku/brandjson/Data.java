package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private String evaluationn;
    @Expose
    private String number;
    @SerializedName("is_collect")
    @Expose
    private String isCollect;
    @Expose
    private String title;
    @Expose
    private String logo;
    @Expose
    private String background;
    @Expose
    private String link;
    @Expose
    private String active_title;


	/**
     * 
     * @return
     *     The evaluationn
     */
    public String getLink() {
    	return link;
    }
    
    public void setLink(String link) {
    	this.link = link;
    }
    
    public String getActive_title() {
    	return active_title;
    }
    
    public void setActive_title(String active_title) {
    	this.active_title = active_title;
    }
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

}

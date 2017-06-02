package zsj.com.oyk255.example.ouyiku.huodong;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeChatPayData {

    @Expose
    private String appid;
    @Expose
    private String partnerid;
    @Expose
    private String prepayid;
    @SerializedName("package")
    @Expose
    private String _package;
    @Expose
    private String noncestr;
    @Expose
    private Integer timestamp;
    @Expose
    private String sign;

    /**
     * 
     * @return
     *     The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 
     * @param appid
     *     The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 
     * @return
     *     The partnerid
     */
    public String getPartnerid() {
        return partnerid;
    }

    /**
     * 
     * @param partnerid
     *     The partnerid
     */
    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    /**
     * 
     * @return
     *     The prepayid
     */
    public String getPrepayid() {
        return prepayid;
    }

    /**
     * 
     * @param prepayid
     *     The prepayid
     */
    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    /**
     * 
     * @return
     *     The _package
     */
    public String getPackage() {
        return _package;
    }

    /**
     * 
     * @param _package
     *     The package
     */
    public void setPackage(String _package) {
        this._package = _package;
    }

    /**
     * 
     * @return
     *     The noncestr
     */
    public String getNoncestr() {
        return noncestr;
    }

    /**
     * 
     * @param noncestr
     *     The noncestr
     */
    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    public Integer getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     * @return
     *     The sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * 
     * @param sign
     *     The sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

}

package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class UpdataVerData {

    @Expose
    private String appname;
    @Expose
    private String vername;
    @Expose
    private String newcontent;
    @Expose
    private String apkurl;

    /**
     * 
     * @return
     *     The appname
     */
    public String getAppname() {
        return appname;
    }

    /**
     * 
     * @param appname
     *     The appname
     */
    public void setAppname(String appname) {
        this.appname = appname;
    }

    /**
     * 
     * @return
     *     The vername
     */
    public String getVername() {
        return vername;
    }

    /**
     * 
     * @param vername
     *     The vername
     */
    public void setVername(String vername) {
        this.vername = vername;
    }

    /**
     * 
     * @return
     *     The newcontent
     */
    public String getNewcontent() {
        return newcontent;
    }

    /**
     * 
     * @param newcontent
     *     The newcontent
     */
    public void setNewcontent(String newcontent) {
        this.newcontent = newcontent;
    }

    /**
     * 
     * @return
     *     The apkurl
     */
    public String getApkurl() {
        return apkurl;
    }

    /**
     * 
     * @param apkurl
     *     The apkurl
     */
    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }

}

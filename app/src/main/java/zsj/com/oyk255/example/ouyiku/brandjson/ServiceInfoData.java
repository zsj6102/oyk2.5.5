package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class ServiceInfoData {

    @Expose
    private String mobile;
    @Expose
    private String qq;

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * 
     * @param qq
     *     The qq
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

}

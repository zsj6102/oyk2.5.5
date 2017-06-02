package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoData{

    @Expose
    private String nickname;
    @Expose
    private String photo;
    @Expose
    private String mobile;
    @Expose
    private String sex;
    @SerializedName("per_sig")
    @Expose
    private String perSig;
    @Expose
    private String level;

    /**
     * 
     * @return
     *     The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 
     * @param nickname
     *     The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 
     * @return
     *     The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 
     * @param photo
     *     The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

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
     *     The sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 
     * @param sex
     *     The sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 
     * @return
     *     The perSig
     */
    public String getPerSig() {
        return perSig;
    }

    /**
     * 
     * @param perSig
     *     The per_sig
     */
    public void setPerSig(String perSig) {
        this.perSig = perSig;
    }

    /**
     * 
     * @return
     *     The level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(String level) {
        this.level = level;
    }

}

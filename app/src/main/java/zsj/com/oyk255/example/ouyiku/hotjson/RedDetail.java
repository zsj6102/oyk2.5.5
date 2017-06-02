package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedDetail {

    @Expose
    private String photo;
    @Expose
    private String sex;
    @Expose
    private String nickname;
    @SerializedName("per_sig")
    @Expose
    private String perSig;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String level;
    @Expose
    private String isfollow;
    @Expose
    private String followcount;
    @Expose
    private String fanscount;

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
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    /**
     * 
     * @return
     *     The isfollow
     */
    public String getIsfollow() {
        return isfollow;
    }

    /**
     * 
     * @param isfollow
     *     The isfollow
     */
    public void setIsfollow(String isfollow) {
        this.isfollow = isfollow;
    }

    /**
     * 
     * @return
     *     The followcount
     */
    public String getFollowcount() {
        return followcount;
    }

    /**
     * 
     * @param followcount
     *     The followcount
     */
    public void setFollowcount(String followcount) {
        this.followcount = followcount;
    }

    /**
     * 
     * @return
     *     The fanscount
     */
    public String getFanscount() {
        return fanscount;
    }

    /**
     * 
     * @param fanscount
     *     The fanscount
     */
    public void setFanscount(String fanscount) {
        this.fanscount = fanscount;
    }

}

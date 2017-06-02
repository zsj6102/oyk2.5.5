package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HisAttentDatum {

    @SerializedName("rankinguser_id")
    @Expose
    private String rankinguserId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String nickname;
    @Expose
    private String photo;
    @Expose
    private String isfollow;
    @SerializedName("per_sig")
    @Expose
    private String perSig;

    /**
     * 
     * @return
     *     The rankinguserId
     */
    public String getRankinguserId() {
        return rankinguserId;
    }

    /**
     * 
     * @param rankinguserId
     *     The rankinguser_id
     */
    public void setRankinguserId(String rankinguserId) {
        this.rankinguserId = rankinguserId;
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

}

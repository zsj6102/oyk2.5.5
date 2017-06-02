package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotPictureCommentDatum {

    @Expose
    private String nickname;
    @Expose
    private String photo;
    @Expose
    private String content;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @SerializedName("user_id")
    @Expose
    private String userId;

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
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The create_time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

}

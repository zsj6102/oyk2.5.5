package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotNetListviewDatum {

    @SerializedName("post_image")
    @Expose
    private String postImage;
    @SerializedName("talent_title")
    @Expose
    private String talentTitle;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String photo;
    @Expose
    private String nickname;
    @Expose
    private String clickgood;
    @SerializedName("talent_post_id")
    @Expose
    private String talentPostId;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @Expose
    private String isclickgood;
    @Expose
    private String messagecount;

    /**
     * 
     * @return
     *     The postImage
     */
    public String getPostImage() {
        return postImage;
    }

    /**
     * 
     * @param postImage
     *     The post_image
     */
    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    /**
     * 
     * @return
     *     The talentTitle
     */
    public String getTalentTitle() {
        return talentTitle;
    }

    /**
     * 
     * @param talentTitle
     *     The talent_title
     */
    public void setTalentTitle(String talentTitle) {
        this.talentTitle = talentTitle;
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
     *     The clickgood
     */
    public String getClickgood() {
        return clickgood;
    }

    /**
     * 
     * @param clickgood
     *     The clickgood
     */
    public void setClickgood(String clickgood) {
        this.clickgood = clickgood;
    }

    /**
     * 
     * @return
     *     The talentPostId
     */
    public String getTalentPostId() {
        return talentPostId;
    }

    /**
     * 
     * @param talentPostId
     *     The talent_post_id
     */
    public void setTalentPostId(String talentPostId) {
        this.talentPostId = talentPostId;
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
     *     The isclickgood
     */
    public String getIsclickgood() {
        return isclickgood;
    }

    /**
     * 
     * @param isclickgood
     *     The isclickgood
     */
    public void setIsclickgood(String isclickgood) {
        this.isclickgood = isclickgood;
    }

    /**
     * 
     * @return
     *     The messagecount
     */
    public String getMessagecount() {
        return messagecount;
    }

    /**
     * 
     * @param messagecount
     *     The messagecount
     */
    public void setMessagecount(String messagecount) {
        this.messagecount = messagecount;
    }

}

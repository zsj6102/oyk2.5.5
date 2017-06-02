package zsj.com.oyk255.example.ouyiku.hotjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotPictureDetailData {

    @SerializedName("talent_title")
    @Expose
    private String talentTitle;
    @SerializedName("talent_content")
    @Expose
    private String talentContent;
    @Expose
    private List<String> pic = new ArrayList<String>();
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @Expose
    private String clickgood;
    @SerializedName("comment_num")
    @Expose
    private String commentNum;
    @SerializedName("is_clickgood")
    @Expose
    private String isClickgood;

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
     *     The talentContent
     */
    public String getTalentContent() {
        return talentContent;
    }

    /**
     * 
     * @param talentContent
     *     The talent_content
     */
    public void setTalentContent(String talentContent) {
        this.talentContent = talentContent;
    }

    /**
     * 
     * @return
     *     The pic
     */
    public List<String> getPic() {
        return pic;
    }

    /**
     * 
     * @param pic
     *     The pic
     */
    public void setPic(List<String> pic) {
        this.pic = pic;
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
     *     The commentNum
     */
    public String getCommentNum() {
        return commentNum;
    }

    /**
     * 
     * @param commentNum
     *     The comment_num
     */
    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 
     * @return
     *     The isClickgood
     */
    public String getIsClickgood() {
        return isClickgood;
    }

    /**
     * 
     * @param isClickgood
     *     The is_clickgood
     */
    public void setIsClickgood(String isClickgood) {
        this.isClickgood = isClickgood;
    }

}

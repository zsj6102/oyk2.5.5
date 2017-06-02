package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinTuanListDatum {

    @SerializedName("groups_id")
    @Expose
    private String groupsId;
    @Expose
    private String title;
    @SerializedName("groups_price")
    @Expose
    private String groupsPrice;
    @Expose
    private String photo;
    @SerializedName("single_price")
    @Expose
    private String singlePrice;

	@Expose
    private String buy_number;

    /**
     * 
     * @return
     *     The groupsId
     */
	public String getBuy_number() {
		return buy_number;
	}
	
	public void setBuy_number(String buy_number) {
		this.buy_number = buy_number;
	}
	
	
    public String getGroupsId() {
        return groupsId;
    }

    /**
     * 
     * @param groupsId
     *     The groups_id
     */
    public void setGroupsId(String groupsId) {
        this.groupsId = groupsId;
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
     *     The groupsPrice
     */
    public String getGroupsPrice() {
        return groupsPrice;
    }

    /**
     * 
     * @param groupsPrice
     *     The groups_price
     */
    public void setGroupsPrice(String groupsPrice) {
        this.groupsPrice = groupsPrice;
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
     *     The singlePrice
     */
    public String getSinglePrice() {
        return singlePrice;
    }

    /**
     * 
     * @param singlePrice
     *     The single_price
     */
    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }

}

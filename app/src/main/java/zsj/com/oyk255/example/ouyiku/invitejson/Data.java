package zsj.com.oyk255.example.ouyiku.invitejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private String icode;
    @Expose
    private String photo;
    @SerializedName("share_title")
    @Expose
    private String shareTitle;
//    @SerializedName("qr_code")
//    @Expose
//    private String qrCode;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    

	private String image;

	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
    /**
     * 
     * @return
     *     The icode
     */
    public String getIcode() {
        return icode;
    }

    /**
     * 
     * @param icode
     *     The icode
     */
    public void setIcode(String icode) {
        this.icode = icode;
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
     *     The shareTitle
     */
    public String getShareTitle() {
        return shareTitle;
    }

    /**
     * 
     * @param shareTitle
     *     The share_title
     */
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }
//
//    /**
//     * 
//     * @return
//     *     The qrCode
//     */
//    public String getQrCode() {
//        return qrCode;
//    }
//
//    /**
//     * 
//     * @param qrCode
//     *     The qr_code
//     */
//    public void setQrCode(String qrCode) {
//        this.qrCode = qrCode;
//    }

    /**
     * 
     * @return
     *     The shareUrl
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     * 
     * @param shareUrl
     *     The share_url
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

}

package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInfoData {

    @SerializedName("card_id")
    @Expose
    private String cardId;
    @Expose
    private String cardnumber;
    @Expose
    private String bankname;
    @Expose
    private String bankimage;

    /**
     * 
     * @return
     *     The cardId
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 
     * @param cardId
     *     The card_id
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * 
     * @return
     *     The cardnumber
     */
    public String getCardnumber() {
        return cardnumber;
    }

    /**
     * 
     * @param cardnumber
     *     The cardnumber
     */
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    /**
     * 
     * @return
     *     The bankname
     */
    public String getBankname() {
        return bankname;
    }

    /**
     * 
     * @param bankname
     *     The bankname
     */
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    /**
     * 
     * @return
     *     The bankimage
     */
    public String getBankimage() {
        return bankimage;
    }

    /**
     * 
     * @param bankimage
     *     The bankimage
     */
    public void setBankimage(String bankimage) {
        this.bankimage = bankimage;
    }

}

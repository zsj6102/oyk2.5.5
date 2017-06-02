package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;

public class BankListDatum {

    @Expose
    private String id;
    @Expose
    private String bankname;
    @Expose
    private String bankimage;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
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

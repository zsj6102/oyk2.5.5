package zsj.com.oyk255.example.ouyiku.huodong;

import com.google.gson.annotations.Expose;

public class NineZeroBuyData {

    @Expose
    private String status;
    @Expose
    private String oids;

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The oids
     */
    public String getOids() {
        return oids;
    }

    /**
     * 
     * @param oids
     *     The oids
     */
    public void setOids(String oids) {
        this.oids = oids;
    }

}

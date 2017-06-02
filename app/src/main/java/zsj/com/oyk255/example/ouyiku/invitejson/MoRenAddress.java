package zsj.com.oyk255.example.ouyiku.invitejson;

import com.google.gson.annotations.Expose;

public class MoRenAddress {

    @Expose
    private Status status;
    @Expose
    private MoRenAddressData data;

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The data
     */
    public MoRenAddressData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(MoRenAddressData data) {
        this.data = data;
    }

}

package zsj.com.oyk255.example.ouyiku.huodong;

import com.google.gson.annotations.Expose;

public class NineZeroBuy {

    @Expose
    private Status status;
    @Expose
    private NineZeroBuyData data;

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
    public NineZeroBuyData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(NineZeroBuyData data) {
        this.data = data;
    }

}

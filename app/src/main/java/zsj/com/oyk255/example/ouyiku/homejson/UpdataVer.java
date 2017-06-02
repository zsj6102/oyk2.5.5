package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class UpdataVer {

    @Expose
    private Status status;
    @Expose
    private UpdataVerData data;

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
    public UpdataVerData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(UpdataVerData data) {
        this.data = data;
    }

}

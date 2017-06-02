package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;

public class DetaiBrandData {

    @Expose
    private Datainfo data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Datainfo getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Datainfo data) {
        this.data = data;
    }

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

}

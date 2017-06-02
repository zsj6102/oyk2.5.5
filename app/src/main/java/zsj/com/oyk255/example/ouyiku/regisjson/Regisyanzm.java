package zsj.com.oyk255.example.ouyiku.regisjson;

import com.google.gson.annotations.Expose;

public class Regisyanzm {

    @Expose
    private Status status;
    @Expose
    private Data data;
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
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }
}

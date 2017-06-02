package zsj.com.oyk255.example.ouyiku.regisjson;

import com.google.gson.annotations.Expose;

public class SetPass {

    @Expose
    private Status status;
    @Expose
    private PassData data;

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
    public PassData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(PassData data) {
        this.data = data;
    }

}

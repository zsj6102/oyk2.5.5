package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class Sign {

    @Expose
    private Status status;
    @Expose
    private SignData data;

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
    public SignData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(SignData data) {
        this.data = data;
    }

}

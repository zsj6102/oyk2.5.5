package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class IsSign {

    @Expose
    private Status status;
    @Expose
    private IsSignData data;

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
    public IsSignData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(IsSignData data) {
        this.data = data;
    }

}

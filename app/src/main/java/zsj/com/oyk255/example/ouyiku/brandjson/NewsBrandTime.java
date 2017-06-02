package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class NewsBrandTime {

    @Expose
    private Status status;
    @Expose
    private NewsBrandTimeData data;

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
    public NewsBrandTimeData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(NewsBrandTimeData data) {
        this.data = data;
    }

}

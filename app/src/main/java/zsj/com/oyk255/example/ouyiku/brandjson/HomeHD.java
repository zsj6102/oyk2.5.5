package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class HomeHD {

    @Expose
    private Status status;
    @Expose
    private HomeHDData data;

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
    public HomeHDData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(HomeHDData data) {
        this.data = data;
    }

}

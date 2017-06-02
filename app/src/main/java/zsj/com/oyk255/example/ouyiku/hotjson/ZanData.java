package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;

public class ZanData {

    @Expose
    private String status;

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}

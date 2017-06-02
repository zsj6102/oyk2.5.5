package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;

public class Data {

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

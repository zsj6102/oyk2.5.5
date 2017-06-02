package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;

public class IfSuccess {

    @Expose
    private Status status;

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

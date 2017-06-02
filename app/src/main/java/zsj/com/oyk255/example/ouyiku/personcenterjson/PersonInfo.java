package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;

public class PersonInfo {

    @Expose
    private Status status;
    @Expose
    private InfoData data;

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
    public InfoData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(InfoData data) {
        this.data = data;
    }

}

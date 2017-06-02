package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;

public class HotNetRedDetail {

    @Expose
    private Status status;
    @Expose
    private RedDetail data;

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
    public RedDetail getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(RedDetail data) {
        this.data = data;
    }

}

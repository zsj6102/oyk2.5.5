package zsj.com.oyk255.example.ouyiku.invitejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class InviteCord {

    @Expose
    private Status status;
    @Expose
    private List<InviteCordDatum> data = new ArrayList<InviteCordDatum>();

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
    public List<InviteCordDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<InviteCordDatum> data) {
        this.data = data;
    }

}

package zsj.com.oyk255.example.ouyiku.footjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class BankList {

    @Expose
    private Status status;
    @Expose
    private List<BankListDatum> data = new ArrayList<BankListDatum>();

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
    public List<BankListDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<BankListDatum> data) {
        this.data = data;
    }

}

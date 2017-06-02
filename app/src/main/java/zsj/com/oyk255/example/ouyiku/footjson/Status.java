package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;

public class Status {

    @Expose
    private String succeed;

    /**
     * 
     * @return
     *     The succeed
     */
    public String getSucceed() {
        return succeed;
    }

    /**
     * 
     * @param succeed
     *     The succeed
     */
    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

}

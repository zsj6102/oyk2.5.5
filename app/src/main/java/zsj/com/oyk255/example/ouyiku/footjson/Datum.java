package zsj.com.oyk255.example.ouyiku.footjson;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;

public class Datum {

    @Expose
    private String time;
    @Expose
    private java.util.List<List> list = new ArrayList<List>();

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<List> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<List> list) {
        this.list = list;
    }

}

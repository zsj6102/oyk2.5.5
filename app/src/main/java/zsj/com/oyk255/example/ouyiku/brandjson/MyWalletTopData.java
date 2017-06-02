package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyWalletTopData {

    @SerializedName("usefull_money")
    @Expose
    private String usefullMoney;
    @SerializedName("all_money")
    @Expose
    private String allMoney;

    /**
     * 
     * @return
     *     The usefullMoney
     */
    public String getUsefullMoney() {
        return usefullMoney;
    }

    /**
     * 
     * @param usefullMoney
     *     The usefull_money
     */
    public void setUsefullMoney(String usefullMoney) {
        this.usefullMoney = usefullMoney;
    }

    /**
     * 
     * @return
     *     The allMoney
     */
    public String getAllMoney() {
        return allMoney;
    }

    /**
     * 
     * @param allMoney
     *     The all_money
     */
    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

}

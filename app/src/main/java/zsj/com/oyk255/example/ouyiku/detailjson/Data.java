package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private String title;
    @Expose
    private String price;
    @Expose
    private String rebate;
    @SerializedName("m_price")
    @Expose
    private String mPrice;
    @Expose
    private String is_c;
    
    /**
     * 
     * @return
     *     The is_c
     */
    public String getIs_c() {
		return is_c;
	}
    /**
     * 
     * @param is_c
     *     The is_c
     */
	public void setIs_c(String is_c) {
		this.is_c = is_c;
	}

	/**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The rebate
     */
    public String getRebate() {
        return rebate;
    }

    /**
     * 
     * @param rebate
     *     The rebate
     */
    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    /**
     * 
     * @return
     *     The mPrice
     */
    public String getMPrice() {
        return mPrice;
    }

    /**
     * 
     * @param mPrice
     *     The m_price
     */
    public void setMPrice(String mPrice) {
        this.mPrice = mPrice;
    }


}

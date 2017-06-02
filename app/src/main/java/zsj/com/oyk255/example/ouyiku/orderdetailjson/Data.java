package zsj.com.oyk255.example.ouyiku.orderdetailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @Expose
    private String ordernum;
    @Expose
    private String price;
    @Expose
    private String expressname;
    @Expose
    private String expressnum;
    @Expose
    private String exphone;
    @Expose
    private String status;
    @SerializedName("create_time")
    @Expose
    private String createTime;
    @SerializedName("pay_time")
    @Expose
    private String payTime;
    @Expose
    private String deliverytime;
    @Expose
    private String colsetime;
    @Expose
    private String remark;
    @Expose
    private String address;
    @Expose
    private String delivery_name;

	@Expose
    private String delivery_postcode;
    @Expose
    private String delivery_telephone;
    
    @Expose
    private String tstatusinfo;
    @Expose
    private String tstatus;
    @Expose
    private String endtimes;
    @Expose
    private String endtime_s;
    
    
    
    
    
    
    public String getTstatusinfo() {
		return tstatusinfo;
	}

	public void setTstatusinfo(String tstatusinfo) {
		this.tstatusinfo = tstatusinfo;
	}

	public String getTstatus() {
		return tstatus;
	}

	public void setTstatus(String tstatus) {
		this.tstatus = tstatus;
	}

	public String getEndtimes() {
		return endtimes;
	}

	public void setEndtimes(String endtimes) {
		this.endtimes = endtimes;
	}

	public String getEndtime_s() {
		return endtime_s;
	}

	public void setEndtime_s(String endtime_s) {
		this.endtime_s = endtime_s;
	}

	@Expose
    private List<Good> goods = new ArrayList<Good>();

    /**
     * 
     * @return
     *     The orderId
     */
    
    public String getDelivery_name() {
		return delivery_name;
	}

	public void setDelivery_name(String delivery_name) {
		this.delivery_name = delivery_name;
	}

	public String getDelivery_postcode() {
		return delivery_postcode;
	}

	public void setDelivery_postcode(String delivery_postcode) {
		this.delivery_postcode = delivery_postcode;
	}

	public String getDelivery_telephone() {
		return delivery_telephone;
	}

	public void setDelivery_telephone(String delivery_telephone) {
		this.delivery_telephone = delivery_telephone;
	}
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * @return
     *     The ordernum
     */
    public String getOrdernum() {
        return ordernum;
    }

    /**
     * 
     * @param ordernum
     *     The ordernum
     */
    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
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
     *     The expressname
     */
    public String getExpressname() {
        return expressname;
    }

    /**
     * 
     * @param expressname
     *     The expressname
     */
    public void setExpressname(String expressname) {
        this.expressname = expressname;
    }

    /**
     * 
     * @return
     *     The expressnum
     */
    public String getExpressnum() {
        return expressnum;
    }

    /**
     * 
     * @param expressnum
     *     The expressnum
     */
    public void setExpressnum(String expressnum) {
        this.expressnum = expressnum;
    }

    /**
     * 
     * @return
     *     The exphone
     */
    public String getExphone() {
        return exphone;
    }

    /**
     * 
     * @param exphone
     *     The exphone
     */
    public void setExphone(String exphone) {
        this.exphone = exphone;
    }

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

    /**
     * 
     * @return
     *     The createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     *     The create_time
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return
     *     The payTime
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * 
     * @param payTime
     *     The pay_time
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    /**
     * 
     * @return
     *     The deliverytime
     */
    public String getDeliverytime() {
        return deliverytime;
    }

    /**
     * 
     * @param deliverytime
     *     The deliverytime
     */
    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    /**
     * 
     * @return
     *     The colsetime
     */
    public String getColsetime() {
        return colsetime;
    }

    /**
     * 
     * @param colsetime
     *     The colsetime
     */
    public void setColsetime(String colsetime) {
        this.colsetime = colsetime;
    }

    /**
     * 
     * @return
     *     The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark
     *     The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The goods
     */
    public List<Good> getGoods() {
        return goods;
    }

    /**
     * 
     * @param goods
     *     The goods
     */
    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

}

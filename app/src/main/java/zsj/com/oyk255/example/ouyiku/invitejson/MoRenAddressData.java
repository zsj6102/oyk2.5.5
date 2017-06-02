package zsj.com.oyk255.example.ouyiku.invitejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoRenAddressData {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private String address;
    @Expose
    private String name;
    @Expose
    private String mobile;
    @SerializedName("is_use")
    @Expose
    private String isUse;

    /**
     * 
     * @return
     *     The addressId
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * 
     * @param addressId
     *     The address_id
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * 
     * @return
     *     The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * 
     * @param province
     *     The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The isUse
     */
    public String getIsUse() {
        return isUse;
    }

    /**
     * 
     * @param isUse
     *     The is_use
     */
    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

}

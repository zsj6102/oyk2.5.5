package zsj.com.oyk255.example.ouyiku.footjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaySuccessAddressData {

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
    private String zipcode;
    @Expose
    private String name;
    @Expose
    private String mobile;
    @Expose
    private String phone;
    @Expose
    private String phone1;
    @Expose
    private String phone2;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;
    @SerializedName("create_time")
    @Expose
    private String createTime;
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
     *     The zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 
     * @param zipcode
     *     The zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * 
     * @param phone1
     *     The phone1
     */
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    /**
     * 
     * @return
     *     The phone2
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * 
     * @param phone2
     *     The phone2
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The isDelete
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 
     * @param isDelete
     *     The is_delete
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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

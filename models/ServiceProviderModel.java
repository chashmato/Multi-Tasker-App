package rtp.raidtechpro.co_tasker.models;

public class ServiceProviderModel {

    String id;
    String uid;
    String category;
    String name;
    String email;
    String phonenumber;
    String province;
    String city;
    String address;
    String photopath;
    String about;
    String type;
    String rate_per_hour;

    String rating;
    String count;


    public ServiceProviderModel(String id, String uid, String category, String name, String email, String phonenumber, String province, String city, String address, String photopath, String about, String type, String rate_per_hour, String rating, String count) {
        this.id = id;
        this.uid = uid;
        this.category = category;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.province = province;
        this.city = city;
        this.address = address;
        this.photopath = photopath;
        this.about = about;
        this.type = type;
        this.rate_per_hour = rate_per_hour;
        this.rating = rating;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate_per_hour() {
        return rate_per_hour;
    }

    public void setRate_per_hour(String rate_per_hour) {
        this.rate_per_hour = rate_per_hour;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
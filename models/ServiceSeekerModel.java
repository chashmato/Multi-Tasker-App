package rtp.raidtechpro.co_tasker.models;

public class ServiceSeekerModel {


    String docid;
    String uid;
    String name;
    String email;
    String mobileno;

    String province;
    String city;
    String address;
    String imagepath;
    String type;


    public ServiceSeekerModel(String docid, String uid, String name, String email, String mobileno, String province, String city, String address, String imagepath, String type) {
        this.docid = docid;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.province = province;
        this.city = city;
        this.address = address;
        this.imagepath = imagepath;
        this.type = type;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
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

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package rtp.raidtechpro.co_tasker.models;

public class OrderModel {

    String id;
    String seekerid;
    String seekername;
    String seekerimage;
    String providerid;
    String providername;
    String providerimage;
    String category;
    String date;
    String sdate;
    String edate;
    String location;
    String totalhours;
    String n_o_hours;
    String rate_per_hour;
    String totalamount;
    String lati;
    String longi;
    String status;

    public OrderModel(String id, String seekerid, String seekername, String seekerimage, String providerid, String providername, String providerimage, String category, String date, String sdate, String edate, String location, String totalhours, String n_o_hours, String rate_per_hour, String totalamount, String lati, String longi, String status) {
        this.id = id;
        this.seekerid = seekerid;
        this.seekername = seekername;
        this.seekerimage = seekerimage;
        this.providerid = providerid;
        this.providername = providername;
        this.providerimage = providerimage;
        this.category = category;
        this.date = date;
        this.sdate = sdate;
        this.edate = edate;
        this.location = location;
        this.totalhours = totalhours;
        this.n_o_hours = n_o_hours;
        this.rate_per_hour = rate_per_hour;
        this.totalamount = totalamount;
        this.lati = lati;
        this.longi = longi;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeekerid() {
        return seekerid;
    }

    public void setSeekerid(String seekerid) {
        this.seekerid = seekerid;
    }

    public String getSeekername() {
        return seekername;
    }

    public void setSeekername(String seekername) {
        this.seekername = seekername;
    }

    public String getSeekerimage() {
        return seekerimage;
    }

    public void setSeekerimage(String seekerimage) {
        this.seekerimage = seekerimage;
    }

    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

    public String getProvidername() {
        return providername;
    }

    public void setProvidername(String providername) {
        this.providername = providername;
    }

    public String getProviderimage() {
        return providerimage;
    }

    public void setProviderimage(String providerimage) {
        this.providerimage = providerimage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(String totalhours) {
        this.totalhours = totalhours;
    }

    public String getN_o_hours() {
        return n_o_hours;
    }

    public void setN_o_hours(String n_o_hours) {
        this.n_o_hours = n_o_hours;
    }

    public String getRate_per_hour() {
        return rate_per_hour;
    }

    public void setRate_per_hour(String rate_per_hour) {
        this.rate_per_hour = rate_per_hour;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


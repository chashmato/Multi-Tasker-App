package rtp.raidtechpro.co_tasker.models;
public class RatingModel {

    String id;
    String user;
    String raring;

    public RatingModel(String id, String name, String icon) {
        this.id = id;
        this.user = name;
        this.raring = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRaring() {
        return raring;
    }

    public void setRaring(String raring) {
        this.raring = raring;
    }
}

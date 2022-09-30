package vttp2022.assessment.csf.orderbackend.models;

public class Request {
    private String name;
	private String email;
    private Integer size;
    private String base;
	private String sauce;
    private String[] toppings;

    private String comments;

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
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getSauce() {
        return sauce;
    }
    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String[] getToppings() {
        return toppings;
    }
    public void setToppings(String[] toppings) {
        this.toppings = toppings;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }


}

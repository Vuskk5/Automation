package practice.serenityTest.models;

public class Plane {
    private int id;
    private String planeType;
    private String seatsQuantity;

    public Plane() {
    }

    public Plane(int id, String planeType, String seatsQuantity) {
        this.id = id;
        this.planeType = planeType;
        this.seatsQuantity = seatsQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public String getSeatsQuantity() {
        return seatsQuantity;
    }

    public void setSeatsQuantity(String seatsQuantity) {
        this.seatsQuantity = seatsQuantity;
    }
}

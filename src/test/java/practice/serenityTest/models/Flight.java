package practice.serenityTest.models;

public class Flight {
    private int id;
    private String takeOffTime;
    private Airport takeOffAirport;
    private String arrivalTime;
    private Airport arrivalAirport;
    private double price;
    private Plane plane;

    public Flight() {
    }

    public Flight(int id) {
        this.id = id;
    }

    public Flight(
            int id,
            String takeOffTime,
            Airport takeOffAirport,
            String arrivalTime,
            Airport arrivalAirport,
            double price,
            Plane plane) {
        this.id = id;
        this.takeOffTime = takeOffTime;
        this.takeOffAirport = takeOffAirport;
        this.arrivalTime = arrivalTime;
        this.arrivalAirport = arrivalAirport;
        this.price = price;
        this.plane = plane;
    }

    public Flight(int id, Airport takeOffAirport, Airport arrivalAirport) {
        this.id = id;
        this.takeOffAirport = takeOffAirport;
        this.arrivalAirport = arrivalAirport;
    }

    public Flight(
            int id,
            Airport takeOffAirport,
            Airport arrivalAirport,
            String takeOffTime,
            String arrivalTime) {
        this(id, takeOffAirport, arrivalAirport);
        this.takeOffTime = takeOffTime;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeoff_time) {
        this.takeOffTime = takeoff_time;
    }

    public Airport getTakeOffAirport() {
        return takeOffAirport;
    }

    public void setTakeOffAirport(Airport takeoff_airport) {
        this.takeOffAirport = takeoff_airport;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrival_time) {
        this.arrivalTime = arrival_time;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrival_airport) {
        this.arrivalAirport = arrival_airport;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane planeid) {
        this.plane = planeid;
    }

    @Override
    public boolean equals(Object ob) {
        return (this.id == ((Flight) ob).getId());
    }
}

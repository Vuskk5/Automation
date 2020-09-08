package practice.serenityTest.models;

import java.util.List;

public class Airport {
    private String iata;
    private double lat;
    private double lon;
    private String name;
    private City city;

    private List<Flight> arrivalFlights;
    private List<Flight> departureFlights;

    public Airport() {
    }

    public Airport(String iata) {
        this.iata = iata;
    }

    public Airport(String iata, String name) {
        this.iata = iata;
        this.name = name;
    }

    public Airport(String iata, double lat, double lon, String name, City city) {
        this.iata = iata;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.city = city;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Flight> arrivalFlights() {
        return arrivalFlights;
    }

    public List<Flight> departureFlights() {
        return departureFlights;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Airport) {
            Airport airport = (Airport) obj;

            return this.iata.equals(airport.getIata());
        }

        return false;
    }
}

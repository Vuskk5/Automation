package practice.serenityTest.models;

import java.util.ArrayList;
import java.util.List;

public class ConnectionFlight {
  private Airport origin;
  private Airport destination;
  private List<Flight> flights;

  public ConnectionFlight(Airport origin, Airport destination) {
    this.origin = origin;
    this.destination = destination;
    this.flights = new ArrayList<>();
  }

  public ConnectionFlight(Airport origin, Airport destination, List<Flight> flights) {
    this.origin = origin;
    this.destination = destination;
    this.flights = flights;
  }

  public ConnectionFlight(Airport origin, Airport destination, Flight flight) {
    this(origin, destination);
    this.addFlight(flight);
  }

  public Airport getOrigin() {
    return origin;
  }

  public void setOrigin(Airport origin) {
    this.origin = origin;
  }

  public Airport getDestination() {
    return destination;
  }

  public void setDestination(Airport destination) {
    this.destination = destination;
  }

  public List<Flight> getFlights() {
    return flights;
  }

  public void setFlights(ArrayList flights) {
    this.flights = flights;
  }

  public void addFlight(Flight flight) {
    this.flights.add(flight);
  }

  @Override
  public boolean equals(Object ob) {
    ConnectionFlight conFlight = (ConnectionFlight) ob;
    return (this.origin.equals(conFlight.getOrigin())
        && this.destination.equals(conFlight.getDestination())
        && this.flights.equals(conFlight.getFlights()));
  }
}

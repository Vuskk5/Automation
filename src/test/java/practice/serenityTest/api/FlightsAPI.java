package practice.serenityTest.api;

import io.restassured.RestAssured;
import practice.serenityTest.models.ConnectionFlight;
import practice.serenityTest.models.Flight;

public class FlightsAPI {
    private static final String HOST = "http://localhost:9000/";

    public static ConnectionFlight[] getFlightsFromTo(String fromIata, String toIata) {
        String requestPath = HOST + "flights/from/" + fromIata + "/to/" + toIata;

        return RestAssured.get(requestPath).getBody().as(ConnectionFlight[].class);
    }

    public static void main(String[] args) {
        ConnectionFlight[] connectionFlights = getFlightsFromTo("TLV", "DUB");
        Flight testFlight = connectionFlights[0].getFlights().get(0);
        String takeOffTime = testFlight.getTakeOffTime();
        String arrivalTime = testFlight.getArrivalTime();
    }
}

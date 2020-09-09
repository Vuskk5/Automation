package asisimAdvanced.managers;

import asisimAdvanced.models.Clinic;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class ClinicManager {
    static {
        init();
    }

    private static List<Clinic> clinics;

    private static void init() {
        clinics =
        Arrays.asList(
            given()
                .baseUri("http://localhost")
                .port(9000)
                .cookie(authenticate("developer", "developer"))
            .when()
                .get("/clinics").as(Clinic[].class)
        );
    }

    public static Clinic byId(Long clinicId) {
        Optional<Clinic> optionalClinic = clinics.stream().filter(clinic -> clinic.id().equals(clinicId)).findFirst();

        if (optionalClinic.isPresent()) {
            return optionalClinic.get();
        }

        throw new InvalidArgumentException("No such clinic id");
    }
}

package asisimAdvanced.managers;

import asisimAdvanced.models.Clinic;
import asisimAdvanced.tests.base.BaseTest;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class ClinicManager implements Manager<Clinic> {
    private static final ThreadLocal<List<Clinic>> clinics = new ThreadLocal<>();
    private static final ThreadLocal<ClinicManager> manager = new ThreadLocal<>();

    public static ClinicManager getInstance() {
        if (manager.get() == null) {
            manager.set(new ClinicManager());
        }

        return manager.get();
    }

    @Override
    public List<Clinic> requestAll() {
        clinics.set(Arrays.asList(
            given()
                .port(BaseTest.PORT)
                .cookie(authenticate("developer", "developer"))
            .when()
                .get("/clinics").as(Clinic[].class)
        ));

        return clinics.get();
    }

    @Override
    public List<Clinic> getAll() {
        if (clinics.get() == null) {
            requestAll();
        }

        return clinics.get();
    }

    @Override
    public Clinic getById(Long clinicId) {
        Optional<Clinic> optionalRank = getAll().stream().filter(clinic -> clinic.id().equals(clinicId)).findFirst();

        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }

        throw new InvalidArgumentException("No such clinic id");
    }
}

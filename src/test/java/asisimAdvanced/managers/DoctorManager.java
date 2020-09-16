package asisimAdvanced.managers;

import asisimAdvanced.models.Clinic;
import asisimAdvanced.models.Doctor;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class DoctorManager implements Manager<Doctor> {
    private static final ThreadLocal<List<Doctor>> doctors = new ThreadLocal<>();
    private static final ThreadLocal<DoctorManager> manager = new ThreadLocal<>();

    public static DoctorManager getInstance() {
        if (manager.get() == null) {
            manager.set(new DoctorManager());
        }

        return manager.get();
    }

    @Override
    public List<Doctor> requestAll() {
        doctors.set(Arrays.asList(
            given()
                .port(9000)
                .cookie(authenticate("developer", "developer"))
            .when()
                .get("/doctors").as(Doctor[].class)
        ));

        return doctors.get();
    }

    @Override
    public List<Doctor> getAll() {
        if (doctors.get() == null) {
            requestAll();
        }

        return doctors.get();
    }

    @Override
    public Doctor getById(Long doctorId) {
        Optional<Doctor> optionalRank = getAll().stream().filter(doctor -> doctor.soldierId().equals(doctorId)).findFirst();

        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }

        throw new InvalidArgumentException("No such doctor id");
    }
}

package asisimAdvanced.managers;

import asisimAdvanced.models.Severity;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class SeverityManager implements Manager<Severity> {
    private static final ThreadLocal<List<Severity>> severities = new ThreadLocal<>();
    private static final ThreadLocal<SeverityManager> manager = new ThreadLocal<>();

    public static SeverityManager getInstance() {
        if (manager.get() == null) {
            manager.set(new SeverityManager());
        }

        return manager.get();
    }

    @Override
    public List<Severity> requestAll() {
        severities.set(Arrays.asList(
            given()
                .port(9000)
                .cookie(authenticate("developer", "developer"))
            .when()
                .get("/severities").as(Severity[].class)
        ));

        return severities.get();
    }

    @Override
    public List<Severity> getAll() {
        if (severities.get() == null) {
            requestAll();
        }

        return severities.get();
    }

    @Override
    public Severity getById(Long severityId) {
        Optional<Severity> optionalRank = getAll().stream().filter(severity -> severity.id().equals(severityId)).findFirst();

        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }

        throw new InvalidArgumentException("No such severity id");
    }
}

package asisimAdvanced.managers;

import asisimAdvanced.models.Clinic;
import asisimAdvanced.models.Soldier;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class SoldierManager implements Manager<Soldier> {
    private static final ThreadLocal<List<Soldier>> soldiers = new ThreadLocal<>();
    private static final ThreadLocal<SoldierManager> manager = new ThreadLocal<>();

    public static SoldierManager getInstance() {
        if (manager.get() == null) {
            manager.set(new SoldierManager());
        }

        return manager.get();
    }

    @Override
    public List<Soldier> requestAll() {
        soldiers.set(Arrays.asList(
            given()
                .port(9000)
                .cookie(authenticate("developer", "developer"))
            .when()
                .get("/soldiers").as(Soldier[].class)
        ));

        return soldiers.get();
    }

    @Override
    public List<Soldier> getAll() {
        if (soldiers.get() == null) {
            requestAll();
        }

        return soldiers.get();
    }

    @Override
    public Soldier getById(Long soldierId) {
        Optional<Soldier> optionalRank = getAll().stream().filter(soldier -> soldier.id().equals(soldierId)).findFirst();

        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }

        throw new InvalidArgumentException("No such soldier id");
    }
}

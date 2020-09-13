package asisimAdvanced.managers;

import asisimAdvanced.models.Rank;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class RankManager implements Manager<Rank> {
    private static final ThreadLocal<List<Rank>> ranks = new ThreadLocal<>();
    private static final ThreadLocal<RankManager> manager = new ThreadLocal<>();

    public static RankManager getInstance() {
        if (manager.get() == null) {
            manager.set(new RankManager());
        }

        return manager.get();
    }

    @Override
    public List<Rank> requestAll() {
        ranks.set(Arrays.asList(
            given()
                .port(9000)
                .cookie(authenticate("developer", "developer"))
            .when()
                .get("/ranks").as(Rank[].class)
        ));

        return ranks.get();
    }

    @Override
    public List<Rank> getAll() {
        if (ranks.get() == null) {
            requestAll();
        }

        return ranks.get();
    }

    @Override
    public Rank getById(Long rankId) {
        Optional<Rank> optionalRank = getAll().stream().filter(rank -> rank.id().equals(rankId)).findFirst();

        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }

        throw new InvalidArgumentException("No such rank id");
    }
}

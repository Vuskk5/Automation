package asisimAdvanced.managers;

import asisimAdvanced.models.Clinic;
import asisimAdvanced.models.Rank;
import org.openqa.selenium.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static asisimAdvanced.managers.Authenticator.authenticate;
import static io.restassured.RestAssured.given;

public class RankManager {
    static {
        init();
    }

    private static List<Rank> ranks;

    private static void init() {
        ranks =
            Arrays.asList(
                    given()
                        .port(9000)
                        .cookies(authenticate("developer", "developer"))
                    .when()
                        .get("/ranks").as(Rank[].class)
            );
    }

    public static Rank byId(Long rankId) {
        Optional<Rank> optionalRank = ranks.stream().filter(rank -> rank.id().equals(rankId)).findFirst();

        if (optionalRank.isPresent()) {
            return optionalRank.get();
        }

        throw new InvalidArgumentException("No such rank id");
    }
}

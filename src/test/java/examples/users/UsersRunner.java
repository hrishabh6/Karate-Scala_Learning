package examples.users;

import com.example.loadtest.LoadtestApplication;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        classes = LoadtestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class UsersRunner {

    @LocalServerPort
    int port;   // ✅ Spring injects the RANDOM port here

    @BeforeAll
    static void setup() {
        // no-op, required for JUnit lifecycle
    }

    @Karate.Test
    Karate testUsers() {
        // ✅ THIS IS THE CRITICAL FIX
        return Karate.run("users")
                .systemProperty("local.server.port", String.valueOf(port))
                .relativeTo(getClass());
    }
}

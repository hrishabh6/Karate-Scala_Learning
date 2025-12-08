package simulations

import com.intuit.karate.gatling.KarateProtocol
import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

class LoadTestSimulation extends Simulation {

  // ✅ Read baseUrl from -DbaseUrl passed via Maven
  val baseUrl: String = System.getProperty("baseUrl", "http://localhost:8080")

  // ✅ Tell Karate to use THIS baseUrl
  val protocol: KarateProtocol = karateProtocol(
    "/api/products" -> Nil
  )

  val crudTest: ScenarioBuilder = scenario("CRUD Operations Performance Test")
    .exec(karateFeature("classpath:examples/users/users.feature"))

  setUp(
    crudTest.inject(
      rampUsers(10).during(10),
      constantUsersPerSec(5).during(20)
    )
  ).protocols(protocol)
}

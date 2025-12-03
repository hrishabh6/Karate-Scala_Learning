package simulations

import com.intuit.karate.gatling.KarateProtocol
import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

class LoadTestSimulation extends Simulation {

  // Load the Karate feature file
  val protocol: KarateProtocol = karateProtocol()

  // Scenario 1: Full CRUD flow in your feature
  val crudTest: ScenarioBuilder = scenario("CRUD Operations Performance Test")
    .exec(karateFeature("classpath:examples/users/users.feature"))

  // Simulation Setup
  setUp(
    crudTest.inject(
      rampUsers(10).during(10),          // Slowly ramp 10 users over 10 sec
      constantUsersPerSec(5).during(20) // Then keep 5 users/sec for 20 sec
    )
  ).protocols(protocol)
}


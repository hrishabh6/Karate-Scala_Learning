function fn() {

    var env = karate.env || 'dev';
    karate.log('karate.env:', env);

    // ✅ FIX: always read from karate.port for Gatling
    var port = karate.properties['karate.port'];

    // ✅ fallback for JUnit/SpringBootTest
    if (!port) {
        port = karate.properties['local.server.port'];
    }

    // ✅ FINAL fallback
    if (!port) {
        port = 8080;
    }

    var config = {
        env: env,
        baseUrl: 'http://localhost:' + port
    };

    karate.log('✅ Using baseUrl:', config.baseUrl);
    return config;
}

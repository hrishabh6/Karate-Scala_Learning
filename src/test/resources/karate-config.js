function fn() {

    var env = karate.env || 'dev';
    karate.log('karate.env:', env);

    // ✅ 1. FIRST PRIORITY: Base URL from Gatling / CI / CD
    var baseUrl = karate.properties['baseUrl'];

    // ✅ 2. FALLBACK: Gatling port-based execution
    if (!baseUrl) {
        var port = karate.properties['karate.port'];
        if (port) {
            baseUrl = 'http://localhost:' + port;
        }
    }

    // ✅ 3. FALLBACK: SpringBootTest / JUnit random port
    if (!baseUrl) {
        var localPort = karate.properties['local.server.port'];
        if (localPort) {
            baseUrl = 'http://localhost:' + localPort;
        }
    }

    // ✅ 4. FINAL FALLBACK: Pure localhost
    if (!baseUrl) {
        baseUrl = 'http://localhost:8080';
    }

    var config = {
        env: env,
        baseUrl: baseUrl
    };

    karate.log('✅ Using baseUrl:', config.baseUrl);
    return config;
}

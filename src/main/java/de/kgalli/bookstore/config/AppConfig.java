package de.kgalli.bookstore.config;


import java.util.ArrayList;
import java.util.List;


public class AppConfig {
    private List<Environment> environments;

    public AppConfig() {
        environments = new ArrayList<>();

        var defaultEnv = new Environment();
        defaultEnv.setEnvironment(EnvironmentType.DEFAULT);
        defaultEnv.setPort(3000);

        var testEnv = new Environment();
        testEnv.setEnvironment(EnvironmentType.TEST);
        testEnv.setPort(3099);

        environments.add(defaultEnv);
        environments.add(testEnv);
    }

    public Integer getAppPort(EnvironmentType environmentType) {
        Environment env = environments.stream()
                .filter(e -> e.getEnvironmentType().equals(environmentType))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Unsupported environment " + environmentType.name()));

        return env.getPort();
    }

    public enum EnvironmentType {
        DEFAULT, TEST;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public class Environment {
        private EnvironmentType environment;
        private Integer port;

        public EnvironmentType getEnvironmentType() {
            return environment;
        }

        public void setEnvironment(EnvironmentType environment) {
            this.environment = environment;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }
    }
}

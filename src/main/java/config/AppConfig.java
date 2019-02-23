package config;

public class AppConfig {
    private Integer appPort = 3000;

    public void setAppPort(Integer appPort) {
        this.appPort = appPort;
    }

    public Integer getAppPort() {
        return appPort;
    }
}

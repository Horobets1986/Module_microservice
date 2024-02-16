package util;

import lombok.Getter;

@Getter
public enum ExternalService {

    AUTH("http://localhost:8066/validate"),
    DISCOUNT("http://localhost:8077/discount");

    private final String url;
    ExternalService(String url) {
        this.url = url;
    }
}

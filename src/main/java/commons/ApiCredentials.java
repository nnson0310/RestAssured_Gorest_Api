package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCredentials {

    @JsonProperty("imgur")
    private Imgur imgur;

    @JsonProperty("ericsson")
    private Ericsson ericsson;

    @Getter
    @Setter
    public static class Imgur {

        private String clientId;

        private String clientSecret;

        private String username;

        private String password;

        private String redirectUri;
    }

    @Getter
    @Setter
    public static class Ericsson {

        private String clientId;

        private String username;

        private String password;
    }
}


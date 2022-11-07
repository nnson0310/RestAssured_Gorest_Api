package api_models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserErrorResponse {
    private String field;

    private String message;
}

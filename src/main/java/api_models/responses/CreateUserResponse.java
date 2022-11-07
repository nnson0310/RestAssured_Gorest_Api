package api_models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {

    private int id;

    private String name;

    private String email;

    private String gender;

    private String status;
}

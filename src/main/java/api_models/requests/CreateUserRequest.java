package api_models.requests;

public class CreateUserRequest {
    public String email;

    public String name;

    public String gender;

    public String status;

    public CreateUserRequest(String email, String name, String gender, String status) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.status = status;
    }
}

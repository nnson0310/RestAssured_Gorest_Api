package api_models.requests;

public class UpdateUserRequest {

    public String email;

    public String name;

    public String gender;

    public String status;

    public UpdateUserRequest(String email, String name, String gender, String status) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.status = status;
    }
}

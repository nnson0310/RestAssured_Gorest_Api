package api_models.requests;

public class UpdateUserRequest {

    public int id;

    public String email;

    public String name;

    public String gender;

    public String status;

    public UpdateUserRequest(int id, String email, String name, String gender, String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.status = status;
    }
}

package api_models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@SuppressWarnings("ALL")
@Getter
@Setter
public class UsersResponse {
    private int code;

    private Meta meta;

    private List<Data> data;

    @Getter
    @Setter
    public class Meta {

        private Pagination pagination;

        @Getter
        @Setter
        public class Pagination {

            private int total;

            private int pages;

            private int page;

            private int limit;
        }

    }

    @Getter
    @Setter
    public class Data {

        private int id;

        private String name;

        private String email;

        private String gender;

        private String status;
    }
}

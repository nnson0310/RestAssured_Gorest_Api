package api_models.responses;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("All")
@Getter
@Setter
public class SingleUserResponse {
    private int code;

    private Meta meta;

    private Data data;

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

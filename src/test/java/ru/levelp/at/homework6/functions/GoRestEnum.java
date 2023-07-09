package ru.levelp.at.homework6.functions;

public class GoRestEnum {
    public enum ParametersUser {
        EMAIL("email"),
        GENDER("gender"),
        NAME("name"),
        STATUS("status");

        public final String value;

        ParametersUser(String value) {
            this.value = value;
        }
    }

    public enum ParametersPost {
        USER_ID("user_id"),
        TITLE("title"),
        BODY("body");

        public final String value;

        ParametersPost(String value) {
            this.value = value;
        }
    }

    public enum ParametersComment {
        POST_ID("post_id"),
        NAME("name"),
        EMAIL("email"),
        BODY("body");

        public final String value;

        ParametersComment(String value) {
            this.value = value;
        }
    }

    public enum GenderUser {
        MALE("male"),
        FEMALE("female");

        public final String value;

        GenderUser(String value) {
            this.value = value;
        }
    }

    public enum StatusUser {
        ACTIVE("active"),
        INACTIVE("inactive");

        public final String value;

        StatusUser(String value) {
            this.value = value;
        }
    }
}

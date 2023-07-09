package ru.levelp.at.homework6.model.response;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserModel {
    private Integer id;
    private String name;
    private String email;
    private String gender;
    private String status;

    public UserModel(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

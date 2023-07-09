package ru.levelp.at.homework6.model.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
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
public class CommentModel {
    private Integer id;
    @SerializedName("post_id")
    private Integer postId;
    private String name;
    private String email;
    private String body;

    public CommentModel(Integer postId, String name, String email, String body) {
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

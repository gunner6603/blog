package hello.blog.dto;

import lombok.Data;

@Data
public class PostDto {

    private String title;
    private String author;
    private String content;

    public PostDto(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }
}

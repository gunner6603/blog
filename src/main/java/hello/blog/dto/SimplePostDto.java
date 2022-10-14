package hello.blog.dto;

import hello.blog.domain.Post;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class SimplePostDto {
    private Long id;
    private String title;
    private String content;
    private String formattedCreateDate;
    private static final int TITLE_LENGTH = 50;
    private static final int CONTENT_LENGTH = 170;

    public SimplePostDto(Post post) {
        this.id = post.getId();
        if (post.getTitle().length() > TITLE_LENGTH) {
            this.title = post.getTitle().substring(0, TITLE_LENGTH) + " ...";
        } else {
            this.title = post.getTitle();
        }
        if (post.getContent().length() > CONTENT_LENGTH) {
            this.content = post.getContent().substring(0, CONTENT_LENGTH) + " ...";
        } else {
            this.content = post.getContent();
        }
        this.formattedCreateDate = post.getCreateDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }
}

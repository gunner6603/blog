package hello.blog.domain;

import hello.blog.dto.PostDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    private String title;

    private String author;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createDate;

    private int views;

    @OneToMany(mappedBy = "post", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.createDate = LocalDateTime.now();
        this.views = 0;
    }

    public void update(PostDto postDto) {
        this.title = postDto.getTitle();
        this.author = postDto.getAuthor();
        this.content = postDto.getContent();
    }
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public String getFormattedCreateDate() {
        return createDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }
}

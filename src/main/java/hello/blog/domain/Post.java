package hello.blog.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    private String title;

    private String author;

    private String content;

    private LocalDateTime createDate;

    private int views;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}

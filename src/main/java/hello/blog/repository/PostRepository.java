package hello.blog.repository;

import hello.blog.domain.Comment;
import hello.blog.domain.Post;
import hello.blog.dto.PostDto;
import hello.blog.dto.SimplePostDto;
import hello.blog.repository.paging.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class PostRepository {

    @PersistenceContext
    private EntityManager em;
    private static final int DATA_NUM_PER_PAGE = 5;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }
    @Transactional(readOnly = true)
    public Page<SimplePostDto> getPagedSimplePosts(int page) { //페이징은 나중에..!
        int postCount = (int) em.createQuery("select count(p) from Post p", Long.class).getSingleResult().longValue();
        Page<SimplePostDto> postPage = new Page<SimplePostDto>(DATA_NUM_PER_PAGE, page, postCount);
        int offset = postPage.getOffset();
        List<Post> posts = em.createQuery("select p from Post p order by p.createDate desc", Post.class)
                .setFirstResult(offset)
                .setMaxResults(DATA_NUM_PER_PAGE)
                .getResultList();
        List<SimplePostDto> simplePostDtos = posts.stream().map(p -> new SimplePostDto(p)).collect(Collectors.toList());
        postPage.setPageData(simplePostDtos);
        return postPage;
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    public Post findByIdAndIncreaseViews(Long postId) {
        Post findPost = em.find(Post.class, postId);
        findPost.setViews(findPost.getViews() + 1);
        return findPost;
    }

    public void update(Long postId, PostDto postDto) {
        Post findPost = em.find(Post.class, postId);
        findPost.update(postDto);
    }

    public void remove(Long postId) {
        Post findPost = em.find(Post.class, postId);
        em.remove(findPost);
    }

    public void addComment(Long postId, Comment comment) {
        Post findPost = em.find(Post.class, postId);
        findPost.addComment(comment);
        em.persist(comment);
    }
}

package hello.blog.repository;

import hello.blog.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

//    public List<SimplePostDto> findSimplePosts() { //페이징은 나중에..!
//        return null; //아직 구현 x
//    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    public Long update(Post post) { //postId와 updateParam(DTO)을 받도록 수정
        return post.getId(); //아직 구현 x
    }

    public void remove(Long postId) {
        Post findPost = em.find(Post.class, postId);
        em.remove(findPost);
    }
}

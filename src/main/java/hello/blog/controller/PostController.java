package hello.blog.controller;

import hello.blog.domain.Comment;
import hello.blog.domain.Post;
import hello.blog.dto.PostDto;
import hello.blog.dto.SimplePostDto;
import hello.blog.repository.PostRepository;
import hello.blog.repository.paging.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    public String posts(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<SimplePostDto> postPage = postRepository.getPagedSimplePosts(page);
        model.addAttribute("postPage", postPage);
        return "posts";
    }

    @GetMapping("/{postId}")
    public String post_detail(@PathVariable("postId") Long postId, Model model) {
        Post post = postRepository.findByIdAndIncreaseViews(postId);
        model.addAttribute("post", post);
        model.addAttribute("comments", post.getComments());
        return "post";
    }

    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute PostDto postDto, Model model, RedirectAttributes redirectAttributes) {
        Post post = new Post(postDto.getTitle(), postDto.getAuthor(), postDto.getContent());
        Long saveId = postRepository.save(post);
        redirectAttributes.addAttribute("saveId", saveId);
        return "redirect:/posts/{saveId}"; //게시물 상세 페이지
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable("postId") Long postId, Model model) {
        Post post = postRepository.findById(postId);
        model.addAttribute("post", post);
        return "editForm";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, @ModelAttribute PostDto postDto) {
        postRepository.update(postId, postDto);
        return "redirect:/posts/{postId}";
    }

    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postRepository.remove(postId);
        return "redirect:/posts";
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable("postId") Long postId, @RequestParam String author, @RequestParam String content) {
        Comment comment = new Comment(author, content);
        postRepository.addComment(postId, comment);
        return "redirect:/posts/{postId}";
    }

    @RequestMapping("/init")
    public String init() {
        String title, author, content;
        for (int i = 1; i <= 50; i++) {
            title = "테스트 게시물 " + i;
            author = "테스트 게시물 " + i + " 작성자";
            content = "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 " + "테스트 게시물 " + i + " 내용 ";
            Post post = new Post(title, author, content);
            postRepository.save(post);
        }
        return "redirect:/posts";
    }
}

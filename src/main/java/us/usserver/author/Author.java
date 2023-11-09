package us.usserver.author;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import us.usserver.authority.Authority;
import us.usserver.comment.ChapterComment;
import us.usserver.comment.NovelComment;
import us.usserver.like.ChapterCommentLike;
import us.usserver.like.NovelLike;
import us.usserver.like.ParagraphLike;
import us.usserver.paragraph.Paragraph;
import us.usserver.score.Score;
import us.usserver.stake.Stake;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @NotBlank
    private String nickname;

    @Max(100)
    private String introduction;

    //프로필 사진을 설정하지 않았을 때 default 이미지 값을 Input 예정
    private String profileImg;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Paragraph> paragraphs = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Stake> stakes = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<NovelLike> novelLikes = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<ParagraphLike> paragraphLikes = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<NovelComment> novelComments = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<ChapterComment> chapterComments = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<ChapterCommentLike> chapterCommentLikeList = new ArrayList<>();
}

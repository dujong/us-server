package us.usserver.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import us.usserver.author.Author;
import us.usserver.base.BaseEntity;
import us.usserver.chapter.Chapter;
import us.usserver.like.ChapterCommentLike;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "chapter_comment_id", cascade = CascadeType.ALL)
    private List<ChapterCommentLike> chapterCommentLikeList = new ArrayList<>();
}
package us.usserver.stake;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import us.usserver.author.Author;
import us.usserver.author.AuthorMother;
import us.usserver.author.AuthorRepository;
import us.usserver.authority.Authority;
import us.usserver.authority.AuthorityRepository;
import us.usserver.chapter.Chapter;
import us.usserver.chapter.ChapterMother;
import us.usserver.chapter.ChapterRepository;
import us.usserver.member.Member;
import us.usserver.member.MemberMother;
import us.usserver.member.MemberRepository;
import us.usserver.novel.Novel;
import us.usserver.novel.NovelMother;
import us.usserver.novel.NovelRepository;
import us.usserver.paragraph.Paragraph;
import us.usserver.paragraph.ParagraphMother;
import us.usserver.paragraph.ParagraphRepository;
import us.usserver.paragraph.paragraphEnum.ParagraphStatus;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@AutoConfigureMockMvc
@SpringBootTest
class StakeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StakeService stakeService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ParagraphRepository paragraphRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;
    private Author author5;
    private Novel novel;
    private Chapter chapter1;
    private Chapter chapter2;
    private Paragraph paragraph1;
    private Paragraph paragraph2;
    private Paragraph paragraph3;
    private Paragraph paragraph4;
    private Paragraph paragraph5;
    private Paragraph paragraph6;
    private Paragraph paragraph7;
    private Paragraph paragraph8;

    @BeforeEach
    void setUp() {
        Member member1 = MemberMother.generateMember();
        Member member2 = MemberMother.generateMember();
        Member member3 = MemberMother.generateMember();
        Member member4 = MemberMother.generateMember();
        Member member5 = MemberMother.generateMember();
        author1 = AuthorMother.generateAuthor();
        author2 = AuthorMother.generateAuthor();
        author3 = AuthorMother.generateAuthor();
        author4 = AuthorMother.generateAuthor();
        author5 = AuthorMother.generateAuthor();
        author1.setMember(member1);
        author2.setMember(member2);
        author3.setMember(member3);
        author4.setMember(member4);
        author5.setMember(member5);

        novel = NovelMother.generateNovel(author1);
        chapter1 = ChapterMother.generateChapter(novel);
        chapter1.setPartForTest(1);
        chapter2 = ChapterMother.generateChapter(novel);
        chapter2.setPartForTest(2);
        novel.getChapters().add(chapter1);
        novel.getChapters().add(chapter2);

        paragraph1 = ParagraphMother.generateParagraph(author1, chapter1);
        paragraph2 = ParagraphMother.generateParagraph(author2, chapter1);
        paragraph3 = ParagraphMother.generateParagraph(author3, chapter1);
        paragraph1.setSequenceForTest(1);
        paragraph1.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        paragraph2.setSequenceForTest(2);
        paragraph2.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        paragraph3.setSequenceForTest(3);
        paragraph3.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        chapter1.getParagraphs().add(paragraph1);
        chapter1.getParagraphs().add(paragraph2);
        chapter1.getParagraphs().add(paragraph3);

        paragraph4 = ParagraphMother.generateParagraph(author4, chapter2);
        paragraph5 = ParagraphMother.generateParagraph(author4, chapter2);
        paragraph6 = ParagraphMother.generateParagraph(author4, chapter2);
        paragraph7 = ParagraphMother.generateParagraph(author5, chapter2);
        paragraph8 = ParagraphMother.generateParagraph(author5, chapter2);
        paragraph4.setSequenceForTest(1);
        paragraph4.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        paragraph5.setSequenceForTest(2);
        paragraph5.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        paragraph6.setSequenceForTest(3);
        paragraph6.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        paragraph7.setSequenceForTest(4);
        paragraph7.setParagraphStatusForTest(ParagraphStatus.SELECTED);
        paragraph8.setSequenceForTest(4);
        paragraph8.setParagraphStatusForTest(ParagraphStatus.UNSELECTED);
        chapter2.getParagraphs().add(paragraph4);
        chapter2.getParagraphs().add(paragraph5);
        chapter2.getParagraphs().add(paragraph6);
        chapter2.getParagraphs().add(paragraph7);
        chapter2.getParagraphs().add(paragraph8);

        Authority authority1 = Authority.builder().author(author1).novel(novel).build();
        Authority authority2 = Authority.builder().author(author2).novel(novel).build();
        Authority authority3 = Authority.builder().author(author3).novel(novel).build();
        Authority authority4 = Authority.builder().author(author4).novel(novel).build();
        Authority authority5 = Authority.builder().author(author5).novel(novel).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);
        authorRepository.save(author5);
        novelRepository.save(novel);
        chapterRepository.save(chapter1);
        chapterRepository.save(chapter2);
        paragraphRepository.save(paragraph1);
        paragraphRepository.save(paragraph2);
        paragraphRepository.save(paragraph3);
        paragraphRepository.save(paragraph4);
        paragraphRepository.save(paragraph5);
        paragraphRepository.save(paragraph6);
        paragraphRepository.save(paragraph7);
        paragraphRepository.save(paragraph8);
        authorityRepository.save(authority1);
        authorityRepository.save(authority2);
        authorityRepository.save(authority3);
        authorityRepository.save(authority4);
        authorityRepository.save(authority5);
    }

    @Test
    @DisplayName("지분 조회 api test")
    void getStakes() throws Exception {
        // given

        // when
        stakeService.setStakeInfoOfNovel(novel);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/stake/" + novel.getId())
                .contentType(MediaType.APPLICATION_JSON));
        String resultString = resultActions.andReturn().getResponse().getContentAsString();

        // then
        resultActions.andExpect(status().isOk());
        Assertions.assertThat(resultString).contains(author1.getNickname());
        Assertions.assertThat(resultString).contains(author2.getNickname());
        Assertions.assertThat(resultString).contains(author3.getNickname());
        Assertions.assertThat(resultString).contains(author4.getNickname());
        Assertions.assertThat(resultString).contains(author5.getNickname());
    }
}
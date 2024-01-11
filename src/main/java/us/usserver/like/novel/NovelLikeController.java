package us.usserver.like.novel;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.usserver.global.ApiCsResponse;

@ResponseBody
@RestController
@RequestMapping("/like/novel")
@RequiredArgsConstructor
public class NovelLikeController {
    private final NovelLikeService novelLikeService;

    @PostMapping("/{novelId}")
    public ResponseEntity<ApiCsResponse<?>> setLike(@PathVariable Long novelId) {
        Long authorId = 1L; // TODO: 유저 정보는 토큰 에서 가져올 예정

        novelLikeService.setNovelLike(novelId, authorId);

        ApiCsResponse<Object> response = ApiCsResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{novelId}")
    public ResponseEntity<ApiCsResponse<?>> deleteLike(@PathVariable Long novelId) {
        Long authorId = 1L; // TODO: 유저 정보는 토큰 에서 가져올 예정
        novelLikeService.deleteNovelLike(novelId, authorId);

        ApiCsResponse<Object> response = ApiCsResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}

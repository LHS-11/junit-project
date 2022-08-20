package site.metacoding.junitproject.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

@Getter
@NoArgsConstructor
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    public  BookRespDto toDto(Book bookPS) { // static을 붙이면 외부에서 객체를 만들지 않아도 됨
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
}

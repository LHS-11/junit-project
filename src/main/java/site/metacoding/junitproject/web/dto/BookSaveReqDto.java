package site.metacoding.junitproject.web.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.junitproject.domain.Book;

@Getter
@Setter
public class BookSaveReqDto { // Controller에서 Setter가 호출되면서 Dto에 값이 채워짐
    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}

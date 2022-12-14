package site.metacoding.junitproject.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock // mockito 환경에다 넣어둠 -> 익명 클래스
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    //문제점 -> 서비스만 테스트하고 싶은데, 레포지토리 레이어가 함께 테스트가 된다는 점
    @Test
    public void 책등록하기_test(){
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        //stub (가설)
        when(bookRepository.save(ArgumentMatchers.any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);
        // 가짜로 repository 만들기!!

        //when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        //then
//        assertEquals(dto.getTitle(), bookRespDto.getTitle());
//        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }

    @Test
    public void 책목록보기_test(){
        //given (파라미터로 들어올 데이터)

        //stub (가정)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L,"junit강의","메타코딩"));
        books.add(new Book(2L,"spring강의","겟인데어"));

        when(bookRepository.findAll()).thenReturn(books);

        //when (실행)
        List<BookRespDto> bookRespDtoList = bookService.책목록보기();

        //then (검증)
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit강의"); //앞쪽이 실제로 나올 코드, 뒤쪽이 내가 넣은 데이터 값
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("메타코딩");
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring강의");
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("겟인데어");

    }

    @Test
    public void 책한건보기_test(){

        //given
        Long id = 1L;
        Book book = new Book(1L,"junit강의","메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        //stub
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.책한건보기(id);

        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기_test(){
        //given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("spring강의"); // -> spring강의
        dto.setAuthor("겟인데어"); // 겟인데어

        //stub
        Book book = new Book(1L,"junit강의","메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.책수정하기(id, dto);

        //then
        assertThat(bookOP.get().getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookOP.get().getAuthor()).isEqualTo(dto.getAuthor());

    }
}

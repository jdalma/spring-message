package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void 메세지(){
        // locale 정보가 없으니 `message.properties`를 참조한다
        String result = ms.getMessage("hello", null, null);
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    void 없는_메세지_예외(){
        // `message.properties`에 없는 메세지를 불러올 때 예외 테스트
        Assertions.assertThatThrownBy(() -> ms.getMessage("Exception Test" , null , null))
                    .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void 기본_메세지(){
        String result = ms.getMessage("Exception Test" , null , "기본 메세지" , null);
        Assertions.assertThat(result).isEqualTo("기본 메세지");
    }

    @Test
    void 인자_메세지(){
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        Assertions.assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void 국제화_기본언어(){
        Assertions.assertThat(ms.getMessage("hello" , null , null)).isEqualTo("안녕");
        Assertions.assertThat(ms.getMessage("hello" , null , Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void 국제화_영어(){
        Assertions.assertThat(ms.getMessage("hello" , null , Locale.ENGLISH)).isEqualTo("hello");
    }
}

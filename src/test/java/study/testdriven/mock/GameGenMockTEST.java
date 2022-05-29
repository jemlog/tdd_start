package study.testdriven.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

// 확장 기능 사용하면 그냥 이렇게 genMock 만들 수도 있다!!
@ExtendWith(MockitoExtension.class)
public class GameGenMockTEST {

   @Mock
   private GameNumGen genMock;


    @Test
    void mockTest()
    {
        List<String> mockList = mock(List.class);
        GameNumGen genMock = mock(GameNumGen.class); // Mockito.mock으로 목 객체 만듬
        given(genMock.generate(GameLevel.EASY)).willReturn("123"); // BDDMockito.given 사용해서 특정 값 들어올시 어떤값을 반환할지 설정
        String num = genMock.generate(GameLevel.EASY);
        Assertions.assertEquals("123",num);
        BDDMockito.willThrow(UnsupportedOperationException.class) // 반환값이 없을때는 먼저 willThrow 메서드 안에 예외를 넣고
                .given(mockList)   // 내가 원하는 객체를 넣고
                .clear();          // 메서드를 호출해준다!!
        given(mockList.set(ArgumentMatchers.anyInt(),ArgumentMatchers.eq("123"))).willReturn("456");
        String old = mockList.set(3, "123");
        Assertions.assertEquals(old,"456");
        BDDMockito.then(genMock).should().generate(GameLevel.EASY);

    }
}

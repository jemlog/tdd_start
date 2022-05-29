package study.testdriven.chap07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class UserRegisterTest {

    // 중요! 지금 이렇게 컴파일 되는 요소가 하나도 없는 상태가 TDD 기법!
    // 정상적인 과정이다.

    private UserRegister userRegister;
   // private StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();
    // mockito를 사용하면 인터페이스만 사용해서 mock 객체 생성 가능하다!
    private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
  //  private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();
    private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

    @BeforeEach
    void setUp()
    {
        userRegister = new UserRegister(mockPasswordChecker,fakeRepository,mockEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword()
    {
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

        Assertions.assertThrows(WeakPasswordException.class,() -> {
            userRegister.register("id","pw","email");
        });
    }

    @DisplayName("회원 가입시 암호 검사 수행함")
    @Test
    void checkPassword()
    {
        userRegister.register("id","pw","email"); // 이때 mockPasswordChecker가 호출됐는지

        BDDMockito.then(mockPasswordChecker)   // 여기서 확인한다. 데이터가 이미 들어있음
                .should()
                .checkPasswordWeak(BDDMockito.anyString());
    }
//    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
//    @Test
//    void dupIdExists()
//    {
//        fakeRepository.save(new User("id","pw1","email@email.com"));
//        Assertions.assertThrows(DupIdException.class,() -> {
//            userRegister.register("id","pw2","email");
//        });
//    }

//    @DisplayName("가입하면 이메일 전송함")
//    @Test
//    void sendEmail()
//    {
//        userRegister.register("id","pw","email@email.com");
//        Assertions.assertTrue(spyEmailNotifier.isCalled());
//        Assertions.assertEquals("email@email.com",spyEmailNotifier.getEmail());
//    }

    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail()
    {
        userRegister.register("id","pw","email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class); // String 인자를 잡는 캡쳐이다.
        BDDMockito.then(mockEmailNotifier)
                .should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        Assertions.assertEquals("email@email.com",realEmail);
    }


}

package study.testdriven.chap07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

public class UserRegisterTest {

    // 중요! 지금 이렇게 컴파일 되는 요소가 하나도 없는 상태가 TDD 기법!
    // 정상적인 과정이다.

    private UserRegister userRegister;
    private StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();

    @BeforeEach
    void setUp()
    {
        userRegister = new UserRegister(stubPasswordChecker,fakeRepository,spyEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword()
    {
        stubPasswordChecker.setWeak(true);

        Assertions.assertThrows(WeakPasswordException.class,() -> {
            userRegister.register("id","pw","email");
        });
    }

    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    @Test
    void dupIdExists()
    {
        fakeRepository.save(new User("id","pw1","email@email.com"));
        Assertions.assertThrows(DupIdException.class,() -> {
            userRegister.register("id","pw2","email");
        });
    }

    @DisplayName("가입하면 이메일 전송함")
    @Test
    void sendEmail()
    {
        userRegister.register("id","pw","email@email.com");
        Assertions.assertTrue(spyEmailNotifier.isCalled());
        Assertions.assertEquals("email@email.com",spyEmailNotifier.getEmail());
    }


}

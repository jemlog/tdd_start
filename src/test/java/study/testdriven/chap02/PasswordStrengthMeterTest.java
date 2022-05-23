package study.testdriven.chap02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
1. 테스트할 기능을 이름 정하기
2. 첫번째 테스트는 가장 쉽거나 가장 예외적인 상황 선택
3. 만약 모두 충족해야 하는것과 모두 틀려야 하는게 있다면 구현이 쉬운건 모두 충족하는거!
 */
public class PasswordStrengthMeterTest {
    PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr)
    {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr,result);
    }
    @Test
    void meetsAllCriteria_Then_Strong()
    {
        assertStrength("ab12!@AB",PasswordStrength.STRONG);
        assertStrength("abc1!Add",PasswordStrength.STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal()
    {
        assertStrength("ab12!@A",PasswordStrength.NORMAL);
        assertStrength("Ab12!c",PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal()
    {
        assertStrength("ab!@ABqwer",PasswordStrength.NORMAL);
    }

    @Test
    void nullInput_Then_Invalid()
    {
        // 먼저 테스트 코드에서 기능을 구상한다.
        assertStrength(null,PasswordStrength.INVALID);
    }

    @Test
    void emptyInput_Then_Invalid()
    {
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal()
    {
        assertStrength("ab12!@df",PasswordStrength.NORMAL);
    }

    @Test
    void meetsOnlyLengthCriteria_Then_Weak()
    {
        assertStrength("abdefghi",PasswordStrength.WEAK);
    }

    @Test
    void meetsOnlyNumCriteria_Then_Weak()
    {
        assertStrength("12345",PasswordStrength.WEAK);
    }

    @Test
    void meetsOnlyUpperCriteria_Then_Weak()
    {
        assertStrength("ABZEF",PasswordStrength.WEAK);
    }

    @Test
    void meetsNoCriteria_Then_Weak()
    {
        assertStrength("abc",PasswordStrength.WEAK);
    }
}

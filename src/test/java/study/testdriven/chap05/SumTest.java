package study.testdriven.chap05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SumTest {

    @Test
    void sum()
    {
        Assertions.assertAll(

                () -> Assertions.assertEquals(1,1),
                () -> Assertions.assertEquals(2,2)

        );
    }
}

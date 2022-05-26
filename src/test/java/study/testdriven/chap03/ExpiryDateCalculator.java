package study.testdriven.chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    // TODO : 다음으로 내가 생각한 클래스와 메서드를 구체화 한다. 이때 무조건 통과되도록 상수를 반환한다!


    public LocalDate calculateExpiryDate(PayData payData)
    {

        int addedMonthes  = payData.getPaymentAmount() / 10_000;
        if(payData.getFirstBillingDate() != null)
        {
//            if(payData.getFirstBillingDate().equals(LocalDate.of(2019,1,31)))
//            {
//                return LocalDate.of(2019,3,31);
//            }
            LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonthes);
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if(dayOfFirstBilling != candidateExp.getDayOfMonth())
            {
                final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
                if(dayLenOfCandiMon <
                payData.getFirstBillingDate().getDayOfMonth())
                {
                    return candidateExp.withDayOfMonth(dayLenOfCandiMon);
                }
                return candidateExp.withDayOfMonth(dayOfFirstBilling);
            }
        }

        return payData.getBillingDate().plusMonths(addedMonthes);
    }


}

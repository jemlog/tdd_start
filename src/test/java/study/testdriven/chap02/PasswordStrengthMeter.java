package study.testdriven.chap02;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s)
    {
        if(s.length() < 8)
        {
            return PasswordStrength.NORMAL;
        }
        boolean containsNum = meetsContainingNumberCriteria(s);
        if(!containsNum) return PasswordStrength.NORMAL;
        return PasswordStrength.STRONG;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        boolean containsNum = false;
        for(char ch : s.toCharArray())
        {
            if(ch >= '0' && ch <= '9')
            {
                containsNum = true;
                break;
            }
        }
        return containsNum;
    }
}

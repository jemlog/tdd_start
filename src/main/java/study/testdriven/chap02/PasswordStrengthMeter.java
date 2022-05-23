package study.testdriven.chap02;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s)
    {
        if(s == null || s.isEmpty()) return PasswordStrength.INVALID;
        // 개별 규칙을 검사하는 로직들
        int metCounts = getMetCriteriaCounts(s);

        // 규칙 검사 결과에 따른 암호 강도 계산
        if(metCounts <=1) return PasswordStrength.WEAK;
        if(metCounts == 2) return PasswordStrength.NORMAL;

        return PasswordStrength.STRONG;
    }

    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;
        if(s.length() >=8) metCounts++;
        if(meetsContainingNumberCriteria(s)) metCounts++;
        if(meetsContainingUppercaseCriteria(s)) metCounts++;
        return metCounts;
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        boolean containsUpp = false;
        for(char ch : s.toCharArray())
        {
            if(Character.isUpperCase(ch))
            {
                containsUpp = true;
                break;
            }
        }
        return containsUpp;
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

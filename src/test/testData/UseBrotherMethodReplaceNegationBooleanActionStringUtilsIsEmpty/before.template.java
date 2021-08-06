public class X {
    void f(boolean isMale) {
        String x = "sss";
        if (!<caret>StringUtils.isEmpty(x)) {
            System.out.println(x);
        }
    }
}
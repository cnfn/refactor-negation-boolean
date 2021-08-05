public class X {
    void f(boolean isMale) {
        boolean x = false;
        if (!<caret>x) {
            System.out.println(x);
        }
    }
}
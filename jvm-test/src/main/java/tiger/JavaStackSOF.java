package tiger;

public class JavaStackSOF {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaStackSOF javaStackSOF = new JavaStackSOF();
        try {
            javaStackSOF.stackLeak();
        } catch (Throwable e) {
            System.out.println(javaStackSOF.stackLength);
            throw e;
        }
    }
}

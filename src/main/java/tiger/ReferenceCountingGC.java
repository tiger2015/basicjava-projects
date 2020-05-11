package tiger;

public class ReferenceCountingGC {
    private static final int _1MB = 1024 * 1024;
    private Object instance = null;
    private byte[] bigSize = new byte[_1MB];

    public static void tesyGC(){
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
        System.gc();
    }

    public static void main(String[] args) {
        tesyGC();
    }
}

package tiger;

public class ParentClass {
    static {
        System.out.println("init parent's static code block");
    }
    {
        System.out.println("init parent code block");
    }

    public ParentClass() {
        System.out.println("call parent constructor");
    }
}

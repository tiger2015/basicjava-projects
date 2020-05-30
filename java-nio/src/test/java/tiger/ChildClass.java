package tiger;

public class ChildClass extends ParentClass {

    static {
        System.out.println("init child's static code block");
    }

    {
        System.out.println("inti child's code block");
    }

    public ChildClass() {
        System.out.println("call child constructor");
    }
}

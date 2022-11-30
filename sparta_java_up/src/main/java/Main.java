public class Sample public class Practice {
    public static String STATIC_MESSAGE = "응애";

    static {
        System.out.println("I'm Loading");
        System.out.println(STATIC_MESSAGE + "2");
    }
}

public class Main {
    public static void main(String[] args) {
        String staticMessage = Practice.STATIC_MESSAGE;

        System.out.println("Hello");

        System.out.println(staticMessage);
    }
}{
}

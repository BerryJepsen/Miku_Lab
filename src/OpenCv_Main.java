
import org.opencv.core.Core;

public class OpenCv_Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Hello.main(args);
    }
}

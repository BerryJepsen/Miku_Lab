import javax.swing.*;
import java.awt.*;

//文件打开类
public class File_open extends JFrame {
    private String dirPath = null;
    private String fileName = null;
    public FileDialog openDia;

    //构造函数
    File_open()
    {
        super();
        openDia = new FileDialog(this, "打开文件", FileDialog.LOAD);
        //Windows 并不支持FileDialog进行文件格式过滤，没办法
        openDia.setFilenameFilter((file, filename) -> file.isDirectory() || filename.endsWith(".jpg")|| filename.endsWith(".png")|| filename.endsWith(".NEF"));
    }
}

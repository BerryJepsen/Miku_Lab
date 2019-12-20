import java.awt.*;
import javax.swing.*;

//文件保存类
public class File_Save extends JFrame {
    public FileDialog saveDia;

    //构造函数
    File_Save()
    {
        super();
        saveDia = new FileDialog(this, "另存文件", FileDialog.SAVE);
    }
}

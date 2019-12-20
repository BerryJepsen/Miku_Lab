import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//欢迎界面
public class Hello {
    static JFrame frame = new JFrame("Hello");

    //构造函数
    public Hello(String[] str) {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JP_root.setVisible(false);
                frame.setVisible(false);
                WorkSpace.main(str);
            }
        });
    }

    public static void main(String[] args) {
        frame.setContentPane(new Hello(args).JP_root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel JP_root;
    private JLabel Label_1;
    private JLabel Label_2;
    private JButton startButton;
}

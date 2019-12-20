import javax.swing.*;
import java.awt.event.*;

//帮助菜单窗口
public class Help extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea welcome0OpenSaveTextArea;

    //构造函数
    public Help() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });



        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // 直接关闭窗体
        dispose();
    }

    private void onCancel() {
        // 直接关闭窗体
        dispose();
    }

    public static void main(String[] args) {
        Help dialog = new Help();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

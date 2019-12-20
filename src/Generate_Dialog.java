import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

//自动图像生成窗口
public class Generate_Dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton saveButton;
    private JTextPane textPane1;
    private JSlider slider_circle;
    private JSlider slider_triangle;
    private JSlider slider_rec;
    private JButton generateButton;
    private JSlider slider1;
    private Mat mat;

    //构造函数
    public Generate_Dialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File_Save save=new File_Save();
                save.saveDia.setVisible(true);
                String newdirPath = null;
                String newfileName = null;
                while (true) {
                    newdirPath = save.saveDia.getDirectory();
                    newfileName = save.saveDia.getFile();
                    System.out.println(newdirPath + newfileName);
                    System.out.println(WorkSpace.dirPath + WorkSpace.fileName);
                    if ((newdirPath != WorkSpace.dirPath) || (newfileName != WorkSpace.fileName)) {
                        break;
                    }
                }
                if(newdirPath==null||newfileName==null)
                {
                    return;
                }
                Imgcodecs.imwrite(newdirPath+newfileName, mat);
                return;
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mat= Imgcodecs.imread(WorkSpace.dirPath+ WorkSpace.fileName);
                mat=Generate.generate(slider_circle.getValue(), slider_triangle.getValue(),slider_rec.getValue(),slider1.getValue());
                BufferedImage image= (BufferedImage) new MatToBufImg(mat,".png").toBufferedImage(mat);
                ImageIcon imageIcon=new ImageIcon(image);
                //图片自适应窗口大小
                if(imageIcon.getImage().getHeight(textPane1)>imageIcon.getImage().getWidth(textPane1))
                {
                    WorkSpace.is_horizental=false;
                }
                if(WorkSpace.is_horizental)
                {
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getWidth(),textPane1.getHeight(), Image.SCALE_DEFAULT));
                }
                else
                {
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getHeight(),textPane1.getWidth(),Image.SCALE_DEFAULT));
                }
                textPane1.setText("");
                textPane1.insertIcon(imageIcon);
            }
        });
    }

    private void onOK() {
        // 关闭窗口
        dispose();
    }

    private void onCancel() {
        // 关闭窗口
        dispose();
    }

    public static void main(String[] args) {
        Generate_Dialog dialog = new Generate_Dialog();
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}

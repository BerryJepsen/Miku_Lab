import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

//边缘提取窗口
public class Canny_Dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane textPane1;
    private JSlider slider1;
    private JSlider slider2;
    private JButton saveButton;
    private Mat mat;

    //构造函数
    public Canny_Dialog() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //OK退出
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        slider1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(WorkSpace.dirPath==null)
                {
                    dispose();
                    return;
                }
                String suffix = WorkSpace.fileName.substring(WorkSpace.fileName.lastIndexOf(".") + 1);
                mat= Imgcodecs.imread(WorkSpace.dirPath+ WorkSpace.fileName);
                mat=Image_Operation.canny(mat,slider1.getValue(),slider2.getValue());
                BufferedImage image= (BufferedImage) new MatToBufImg(mat,suffix).toBufferedImage(mat);
                ImageIcon imageIcon=new ImageIcon(image);
                //imageIcon.setImage(image.getScaledInstance(400,580,Image.SCALE_SMOOTH));
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

        slider2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(WorkSpace.dirPath==null)
                {
                    dispose();
                    return;
                }
                String suffix = WorkSpace.fileName.substring(WorkSpace.fileName.lastIndexOf(".") + 1);
                mat= Imgcodecs.imread(WorkSpace.dirPath+ WorkSpace.fileName);
                mat=Image_Operation.canny(mat,slider1.getValue(),slider2.getValue());
                BufferedImage image= (BufferedImage) new MatToBufImg(mat,suffix).toBufferedImage(mat);
                ImageIcon imageIcon=new ImageIcon(image);
                //imageIcon.setImage(image.getScaledInstance(400,580,Image.SCALE_SMOOTH));
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

        //调整阈值1
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if(WorkSpace.dirPath==null)
                {
                    dispose();
                    return;
                }
                String suffix = WorkSpace.fileName.substring(WorkSpace.fileName.lastIndexOf(".") + 1);
                mat= Imgcodecs.imread(WorkSpace.dirPath+ WorkSpace.fileName);
                mat=Image_Operation.canny(mat,slider1.getValue(),slider2.getValue());
                BufferedImage image= (BufferedImage) new MatToBufImg(mat,suffix).toBufferedImage(mat);
                ImageIcon imageIcon=new ImageIcon(image);
                //imageIcon.setImage(image.getScaledInstance(400,580,Image.SCALE_SMOOTH));
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

        //调整阈值2
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if(WorkSpace.dirPath==null)
                {
                    dispose();
                    return;
                }
                String suffix = WorkSpace.fileName.substring(WorkSpace.fileName.lastIndexOf(".") + 1);
                mat= Imgcodecs.imread(WorkSpace.dirPath+ WorkSpace.fileName);
                mat=Image_Operation.canny(mat,slider1.getValue(),slider2.getValue());
                BufferedImage image= (BufferedImage) new MatToBufImg(mat,suffix).toBufferedImage(mat);
                ImageIcon imageIcon=new ImageIcon(image);
                //imageIcon.setImage(image.getScaledInstance(400,580,Image.SCALE_SMOOTH));
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

        //保存目标
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

        // ESCAPE 快捷键退出
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
        Canny_Dialog dialog = new Canny_Dialog();
        dialog.pack();
        dialog.setVisible(true);
        //System.exit(0);
    }
}

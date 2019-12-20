import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

//主工作界面窗口
public class WorkSpace {
    //源路径
    static String dirPath = null;
    //源文件名
    static String fileName = null;
    //是否水平放置
    static public boolean is_horizental = true;
    //图片取向
    static int orientation = 0;
    //缩放比例
    static double size_rate = 1.0;

    //构造函数
    public WorkSpace(String[] args) {

        //Help菜单
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Help dialog = new Help();
                dialog.setLocation(Panel_root.getX() + 300, Panel_root.getY() + 150);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        //为保证多次点击不出现错误
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                comboBox1.setSelectedIndex(-1);
            }
        });

        //操作选择
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                switch (comboBox1.getSelectedIndex()) {
                    //显示图片
                    case 0:
                        File_open open = new File_open();
                        open.openDia.setVisible(true);
                        dirPath = open.openDia.getDirectory();//获取文件路径
                        fileName = open.openDia.getFile(); //获取文件名称
                        //如果打开路径 或 目录为空 则返回空
                        if (dirPath == null || fileName == null) {
                            return;
                        }
                        textPane2.setText("                " + dirPath + fileName);
                        ImageIcon imageIcon = new ImageIcon(dirPath + fileName);
                        //图片自适应窗口大小
                        if (imageIcon.getImage().getHeight(textPane1) > imageIcon.getImage().getWidth(textPane1)) {
                            is_horizental = false;
                        }
                        if (is_horizental) {
                            imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getWidth(), textPane1.getHeight(), Image.SCALE_DEFAULT));
                        } else {
                            imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getHeight(), textPane1.getWidth(), Image.SCALE_DEFAULT));
                        }
                        textPane1.setText("");
                        textPane1.insertIcon(imageIcon);
                        break;
                    case 1:
                        File_Save save=new File_Save();
                        save.saveDia.setVisible(true);
                        String newdirPath = null;
                        String newfileName = null;
                        while (true) {
                            newdirPath = save.saveDia.getDirectory();
                            newfileName = save.saveDia.getFile();
                            System.out.println(newdirPath + newfileName);
                            System.out.println(dirPath + fileName);
                            if ((newdirPath != dirPath) || (newfileName != fileName)) {
                                break;
                            }
                        }
                        if(newdirPath==null||newfileName==null)
                        {
                            return;
                        }
                        Mat image = Imgcodecs.imread(dirPath+fileName);
                        Imgcodecs.imwrite(newdirPath+newfileName, image);
                        image.release();
                        break;
                    case 2:
                        Generate_Dialog.main(args);
                        break;
                }
            }
        });

        //快捷键
        comboBox1.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                //同时按下ctrl+o
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O) {
                    File_open open = new File_open();
                    open.openDia.setVisible(true);
                    dirPath = open.openDia.getDirectory();//获取文件路径
                    fileName = open.openDia.getFile(); //获取文件名称
                    //如果打开路径 或 目录为空 则返回空
                    if (dirPath == null || fileName == null) {
                        return;
                    }
                    File file = new File(dirPath + fileName);
                    textPane2.setText("                " + dirPath + fileName);
                    ImageIcon imageIcon = new ImageIcon(dirPath + fileName);
                    //图片自适应窗口大小
                    if (imageIcon.getImage().getHeight(textPane1) > imageIcon.getImage().getWidth(textPane1)) {
                        is_horizental = false;
                    }
                    if (is_horizental) {
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getWidth(), textPane1.getHeight(), Image.SCALE_DEFAULT));
                    } else {
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getHeight(), textPane1.getWidth(), Image.SCALE_DEFAULT));
                    }
                    textPane1.setText("");
                    textPane1.insertIcon(imageIcon);
                }
                if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_S)
                {
                    File_Save save=new File_Save();
                    save.saveDia.setVisible(true);
                    String newdirPath = null;
                    String newfileName = null;
                    while (true) {
                        newdirPath = save.saveDia.getDirectory();
                        newfileName = save.saveDia.getFile();
                        System.out.println(newdirPath + newfileName);
                        System.out.println(dirPath + fileName);
                        if ((newdirPath != dirPath) || (newfileName != fileName)) {
                            break;
                        }
                    }
                    if(newdirPath==null||newfileName==null)
                    {
                        return;
                    }
                    Mat image = Imgcodecs.imread(dirPath+fileName);
                    Imgcodecs.imwrite(newdirPath+newfileName, image);
                    image.release();
                }
                if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_G)
                {
                    Generate_Dialog.main(args);
                }
            }
        });

        //每次点击放大10%
        button_zoom_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (size_rate > 2) {
                    return;
                }
                size_rate += 0.1;
                ImageIcon imageIcon = new ImageIcon(dirPath + fileName);
                //图片自适应窗口大小
                if (imageIcon.getImage().getHeight(textPane1) > imageIcon.getImage().getWidth(textPane1)) {
                    is_horizental = false;
                }
                if (is_horizental) {
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (textPane1.getWidth() * size_rate), (int) (textPane1.getHeight() * size_rate), Image.SCALE_DEFAULT));
                } else {
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (textPane1.getHeight() * size_rate), (int) (textPane1.getWidth() * size_rate), Image.SCALE_DEFAULT));
                }
                textPane1.setText("");
                textPane1.insertIcon(imageIcon);
            }
        });
        //每次点击缩小10%
        button_zoom_out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (size_rate < 0.1) {
                    return;
                }
                size_rate -= 0.1;
                ImageIcon imageIcon = new ImageIcon(dirPath + fileName);
                //图片自适应窗口大小
                if (imageIcon.getImage().getHeight(textPane1) > imageIcon.getImage().getWidth(textPane1)) {
                    is_horizental = false;
                }
                if (is_horizental) {
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (textPane1.getWidth() * size_rate), (int) (textPane1.getHeight() * size_rate), Image.SCALE_DEFAULT));
                } else {
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) (textPane1.getHeight() * size_rate), (int) (textPane1.getWidth() * size_rate), Image.SCALE_DEFAULT));
                }
                textPane1.setText("");
                textPane1.insertIcon(imageIcon);
            }
        });
        //顺时针90度旋转
        button_right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (dirPath == null) {
                    return;
                }
                if (orientation == 1) {
                    return;
                }
                if (orientation == -1) {
                    ImageIcon imageIcon = new ImageIcon(dirPath + fileName);
                    textPane1.setText("");
                    if (imageIcon.getImage().getHeight(textPane1) > imageIcon.getImage().getWidth(textPane1)) {
                        is_horizental = false;
                    }
                    if (is_horizental) {
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getWidth(), textPane1.getHeight(), Image.SCALE_SMOOTH));
                    } else {
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getHeight(), textPane1.getWidth(), Image.SCALE_SMOOTH));
                    }
                    textPane1.insertIcon(imageIcon);
                    orientation = 0;
                } else {
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    Mat mat = Imgcodecs.imread(dirPath + fileName);
                    mat = Image_Operation.rotateRight(mat);
                    //BufferedImage image=new MatToBufImg(mat,suffix).getImage();
                    BufferedImage image = (BufferedImage) new MatToBufImg(mat, suffix).toBufferedImage(mat);
                    //Image image =new MatToBufImg(mat,".jpg").toBufferedImage(mat);
                    ImageIcon imageIcon = new ImageIcon();
                    //自适应图片横竖放置
                    imageIcon.setImage(image.getScaledInstance(400, 580, Image.SCALE_SMOOTH));
                    textPane1.setText("");
                    textPane1.insertIcon(imageIcon);
                    orientation = 1;
                }
            }
        });
        //逆时针90度旋转
        button_left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (dirPath == null) {
                    return;
                }
                if (orientation == -1) {
                    return;
                }
                if (orientation == 1) {
                    ImageIcon imageIcon = new ImageIcon(dirPath + fileName);
                    textPane1.setText("");
                    if (imageIcon.getImage().getHeight(textPane1) > imageIcon.getImage().getWidth(textPane1)) {
                        is_horizental = false;
                    }
                    if (is_horizental) {
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getWidth(), textPane1.getHeight(), Image.SCALE_DEFAULT));
                    } else {
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(textPane1.getHeight(), textPane1.getWidth(), Image.SCALE_DEFAULT));
                    }
                    textPane1.insertIcon(imageIcon);
                    orientation = 0;
                } else {
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    Mat mat = Imgcodecs.imread(dirPath + fileName);
                    mat = Image_Operation.rotateLeft(mat);
                    //BufferedImage image=new MatToBufImg(mat,suffix).getImage();
                    BufferedImage image = (BufferedImage) new MatToBufImg(mat, suffix).toBufferedImage(mat);
                    //Image image =new MatToBufImg(mat,".jpg").toBufferedImage(mat);
                    ImageIcon imageIcon = new ImageIcon();

                    //自适应图片横竖放置
                    imageIcon.setImage(image.getScaledInstance(400, 580, Image.SCALE_REPLICATE));
                    textPane1.setText("");
                    textPane1.insertIcon(imageIcon);
                    orientation = -1;
                }
            }
        });
        //边缘检测
        button_canny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (dirPath == null) {
                    return;
                }
                Canny_Dialog.main(args);
            }
        });
        //亮度调节
        botton_brightness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (dirPath == null) {
                    return;
                }
                Brightness_Dialog.main(args);
            }
        });
        //面部识别
        botton_facedetec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (dirPath == null) {
                    return;
                }
                Face_Detect_Dialog.main(args);
            }
        });
        //猫脸识别
        botton_smiledetec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (dirPath == null) {
                    return;
                }
                Cat_Detect_Dialog.main(args);
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("WorkSpace");
        frame.setLocation(200, 40);
        frame.setContentPane(new WorkSpace(args).Panel_root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton helpButton;
    private JComboBox comboBox1;
    private JPanel Panel_root;
    private JTextPane textPane1;
    private JPanel Panel_tool;
    private JPanel Panel_operation;
    private JTextPane textPane2;
    private JButton button_zoom_in;
    private JButton button_zoom_out;
    private JButton button_right;
    private JButton button_left;
    private JButton button_canny;
    private JButton botton_brightness;
    private JButton botton_facedetec;
    private JButton botton_smiledetec;
}

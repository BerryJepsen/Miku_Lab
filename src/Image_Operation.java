import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import static java.lang.System.nanoTime;
import static org.opencv.core.CvType.CV_8UC3;

//基本图形操作类
public class Image_Operation {

    //向左旋转
    public static Mat rotateLeft(Mat src) {
        Mat tmp = new Mat();
        // 此函数是转置、（即将图像逆时针旋转90度，然后再关于x轴对称）
        Core.transpose(src, tmp);
        Mat result = new Mat();
        // flipCode = 0 绕x轴旋转180， 也就是关于x轴对称
        // flipCode = 1 绕y轴旋转180， 也就是关于y轴对称
        // flipCode = -1 此函数关于原点对称
        Core.flip(tmp, result, 0);
        return result;
    }

    //向右旋转
    public static Mat rotateRight(Mat src) {
        Mat tmp = new Mat();
        // 此函数是转置、（即将图像逆时针旋转90度，然后再关于x轴对称）
        Core.transpose(src, tmp);
        Mat result = new Mat();
        // flipCode = 0 绕x轴旋转180， 也就是关于x轴对称
        // flipCode = 1 绕y轴旋转180， 也就是关于y轴对称
        // flipCode = -1 此函数关于原点对称
        Core.flip(tmp, result, 1);
        return result;
    }

    //边缘检测类
    public static Mat canny(Mat src, int threshold1, int threshold2) {
        Mat dst = new Mat();
        Mat gray = new Mat();
        Mat image = new Mat();
        //高斯降噪
        Imgproc.GaussianBlur(src, dst, new Size(5, 5), 10, 10);
        //转灰度图片
        Imgproc.cvtColor(dst, gray, Imgproc.COLOR_BGR2GRAY);
        //描绘边缘
        Imgproc.Canny(gray, image, threshold1, threshold2, 3, false);
        //Imgproc.Canny(src, image, threshold1, threshold2, 3, false);
        Mat NewImage = new Mat(image.size(), image.type());
        src.copyTo(NewImage, image);
        return image;
    }

    //亮度调整类
    public static Mat Brightness(Mat src, double alpha) {
        Mat dst = new Mat(src.size(), src.type());
        int channels = src.channels();//获取图像通道数
        double[] pixel = new double[3];
        //float alpha=1.2f;
        float bate = 30f;
        for (int i = 0, rlen = src.rows(); i < rlen; i++) {
            for (int j = 0, clen = src.cols(); j < clen; j++) {
                if (channels == 3) {//1 图片为3通道即平常的(R,G,B)
                    pixel = src.get(i, j).clone();
                    pixel[0] = pixel[0] * alpha + bate;//R
                    pixel[1] = pixel[1] * alpha + bate;//G
                    pixel[2] = pixel[2] * alpha + bate;//B
                    dst.put(i, j, pixel);
                } else {//2 图片为单通道即灰度图
                    pixel = src.get(i, j).clone();
                    dst.put(i, j, pixel[0] * alpha + bate);
                }
            }
        }
        return dst;
    }

}

//将OpenCV矩阵转换为转换为BufferedImage的类
class MatToBufImg {
    Mat matrix;
    MatOfByte mob;
    String fileExten;

    // 扩展名应该为 ".jpg", ".png", etc
    public MatToBufImg(Mat amatrix, String fileExtension) {
        matrix = amatrix;
        fileExten = fileExtension;
        mob = new MatOfByte();
    }

    //转换为BufferedImage，不含参数
    public BufferedImage getImage() {

        // 根据扩展名生成对应的矩阵
        Imgcodecs.imencode(fileExten, matrix, mob);

        // 将矩阵转化为byte流
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    //转换为Image，带参数
    public Image toBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
        byte[] buffer = new byte[bufferSize];
        matrix.get(0, 0, buffer); // 获取所有的像素点
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }

}

//面部检测类
class Face_Detect {
    //返回识别后的图像
    static public Mat run(Mat image) {
        CascadeClassifier faceDetector = new CascadeClassifier("src\\haarcascade_frontalface_alt.xml");
        //监测人脸
        // MatOfRect是一种特殊容器
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        // 给识别到的人脸画出标记方框
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
        //Face_Detect_Dialog.textPane2.setText(String.format("Detected %s faces", faceDetections.toArray().length));
        return image;
    }

    //返回识别到的人脸个数
    static public int num(Mat image) {
        CascadeClassifier faceDetector = new CascadeClassifier("src\\haarcascade_frontalface_alt.xml");
        //监测人脸
        // MatOfRect是一种特殊容器
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        return faceDetections.toArray().length;
    }
}

//猫脸监测类
class Cat_Detect {
    //返回识别后的图像
    static public Mat run(Mat image) {
        CascadeClassifier catDetector = new CascadeClassifier("src\\haarcascade_frontalcatface.xml");
        //监测猫脸
        // MatOfRect是一种特殊容器
        MatOfRect catDetections = new MatOfRect();
        catDetector.detectMultiScale(image, catDetections);
        // 给识别到的猫脸画出标记方框
        for (Rect rect : catDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
        //Face_Detect_Dialog.textPane2.setText(String.format("Detected %s faces", faceDetections.toArray().length));
        return image;
    }

    //返回识别到的猫脸个数
    static public int num(Mat image) {
        CascadeClassifier catDetector = new CascadeClassifier("src\\haarcascade_frontalcatface.xml");
        //监测猫脸
        // MatOfRect是一种特殊容器
        MatOfRect catDetections = new MatOfRect();
        catDetector.detectMultiScale(image, catDetections);
        return catDetections.toArray().length;
    }
}

//图片生成类
class Generate {
    private static final int W = 800;
    private static final int H = 600;

    //生成随机图像
    static public Mat generate(int circle, int triangle, int square, int style) {
        Mat image = Mat.zeros(H, W, CV_8UC3);
        Imgproc.rectangle(image,
                new Point(0, 0),
                new Point(W, H),
                RNG(nanoTime(), style),
                -1,
                8,
                0);
        for (int i = 0; i < circle; i++) {
            MyCircle(image, Ram_Point(), style);
        }
        for (int i = 0; i < triangle; i++) {
            //MyLine(image, Ram_Point(), Ram_Point(), style);
            MyTriangle(image, style);
        }
        for (int i = 0; i < square; i++) {
            MyRectangle(image, style);
        }
        return image;
    }

    //风格化颜色
    static public Scalar RNG(long seed, int style) {
        int r = 0, g = 0, b = 0;
        Random R = new Random(seed * seed / 3 + 27);
        Random G = new Random(seed);
        Random B = new Random(seed * 17 + 91);
        if (style >= 0 && style <= 255) {
            r = R.nextInt(style);
            g = G.nextInt(255);
            b = B.nextInt(255);
        }
        if (style > 255 && style <= 511) {
            r = R.nextInt(255);
            g = G.nextInt(style - 256);
            b = B.nextInt(255);
        }
        if (style > 511 && style <= 766) {
            r = R.nextInt(255);
            g = G.nextInt(255);
            b = B.nextInt(style - 511);
        }
        Scalar scalar = new Scalar(r, g, b);
        return scalar;
    }

    //随机生成点
    static private Point Ram_Point() {
        Random x = new Random(nanoTime() % 2671);
        Random y = new Random(nanoTime() % 997);
        Point point = new Point(x.nextInt(W), y.nextInt(H));
        return point;
    }

    //风格化线条
    static private void MyLine(Mat img, Point start, Point end, int style) {
        if ((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y) < 256) {
            return;
        }
        Random r = new Random(nanoTime());
        int thickness = r.nextInt(3) + 1;
        int lineType = 8;
        int shift = 0;
        Imgproc.line(img,
                start,
                end,
                RNG(nanoTime(), style),
                thickness,
                lineType,
                shift);
    }

    //风格化椭圆
    static private void MyEllipse(Mat img, double angle, int style) {
        int thickness = 2;
        int lineType = 8;
        int shift = 0;
        Imgproc.ellipse(img,
                new Point(W / 2, W / 2),
                new Size(W / 4, W / 16),
                angle,
                0.0,
                360.0,
                RNG(nanoTime(), style),
                thickness,
                lineType,
                shift);
    }

    //风格化实心圆
    static private void MyFilledCircle(Mat img, Point center, int style) {
        Random r = new Random();
        int thickness = -1;
        int lineType = 8;
        int shift = 0;
        Imgproc.circle(img,
                center,
                r.nextInt(H / 5),
                RNG(nanoTime(), style),
                thickness,
                lineType,
                shift);
    }

    //风格化空心圆
    static private void MyCircle(Mat img, Point center, int style) {
        Random r = new Random(nanoTime());
        int thickness = r.nextInt(6) + 1;
        int lineType = 8;
        int shift = 0;
        Imgproc.circle(img,
                center,
                r.nextInt(H / 4),
                RNG(nanoTime(), style),
                thickness,
                lineType,
                shift);
    }

    //风格化矩形
    static private void MyRectangle(Mat img, int style) {
        Random r = new Random(nanoTime());
        int thickness = r.nextInt(3) + 1;
        int lineType = 8;
        int shift = 0;
        Point p1 = Ram_Point();
        Point p2 = Ram_Point();
        if ((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y) < 256) {
            return;
        }
        Imgproc.rectangle(img,
                p1,
                p2,
                RNG(nanoTime(), style),
                thickness,
                lineType,
                shift);
    }

    //风格化三角形
    static private void MyTriangle(Mat img, int style) {
        Point p1 = Ram_Point();
        Point p2 = Ram_Point();
        Point p3 = Ram_Point();
        if ((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y) < 256) {
            return;
        }
        if ((p3.x - p2.x) * (p3.x - p2.x) + (p3.y - p2.y) * (p3.y - p2.y) < 256) {
            return;
        }
        if ((p1.x - p3.x) * (p1.x - p3.x) + (p1.y - p3.y) * (p1.y - p3.y) < 256) {
            return;
        }
        MyLine(img, p1, p2, style);
        MyLine(img, p1, p3, style);
        MyLine(img, p3, p2, style);
    }
}
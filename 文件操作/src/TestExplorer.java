import java.io.File;
import java.io.FileOutputStream;

public class TestExplorer {
    public static void main(String[] args) {
        ExplorerClass a = new ExplorerClass();
        File file1 = new File("E:\\Java程序\\123.txt");
        File file2 = new File("E:\\Java程序\\1234.txt");
        File file3 = new File("E:\\Java程序\\测试文件夹");
        File file4 = new File("E:\\Java程序\\12.txt");
        File file5 = new File("E:\\Java程序\\123 -加密.txt");
        File file6 = new File("E:\\Java程序\\The.mp3");
        File file7 = new File("E:\\Java程序\\The -加密.mp3");
        File file8 = new File("E:\\Java程序\\12345.png");
        File file9 = new File("E:\\Java程序\\12345 -加密.png");
        File file10 = new File("E:\\Java程序\\测试");
        a.decode(file1);
//        a.setFile(file1);
//        a.copyFile(file1,file3);
//        a.delFile(file2);
//        a.delFile(file3);
//        a.renameFile(file2);
//        a.shearFile(file2,file3);
//        a.showFiles(file3);
//        a.encode(file8);
//        a.decode(file9);

    }
}

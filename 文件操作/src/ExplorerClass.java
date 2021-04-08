import java.io.*;
import java.util.Scanner;

public class ExplorerClass implements Explorer{
    private File file = null;
    ExplorerClass(File file){
        this.file = file;
    }
    ExplorerClass(){

    }
    public void setFile(File file){
        this.file = file;
    }
    public String getFilePathWithoutSuffix(File file){
        String fileName = file.getPath();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    public String getFileSuffix(File file){
        String fileName = file.getPath();
        return fileName.substring(fileName.lastIndexOf("."));
    }
    public String addAsciiOfString(String a,int n){
        StringBuilder buffer = new StringBuilder(a);
        for (int i = 0; i < a.length(); i++) {
            buffer.setCharAt(i,(char)(a.charAt(i)+n));
        }
        return buffer.toString();
    }
    @Override
    public void copyFile(File sourceFile, File desFile) {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        if(desFile.isDirectory()) {
            desFile = new File(desFile+"\\"+sourceFile.getName());
        }
        try {
            fin = new FileInputStream(sourceFile);
            fout = new FileOutputStream(desFile);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fin.read(bytes))!= -1){
                fout.write(bytes,0,len);
            }
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fin != null) fin.close();
                if(fout != null) fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!desFile.exists()){
            System.out.println("复制失败\n");
        }
    }

    @Override
    public void delFile(File file) {
        if (file == null) System.out.println("地址为空\n");
        else if (file.isDirectory()){
            if(!file.delete()){
                File[] files = file.listFiles();
                if (files!=null){
                    for (File a:files) {
                        delFile(a);
                    }
                }
            }
            if(!file.delete()) {
                System.out.println(file+"删除失败\n");
            }
        }
        else if(file.isFile()){
            if(!file.delete()) {
                System.out.println(file+"删除失败\n");
            }
        }else{
            System.out.println("文件不存在\n");
        }
    }

    @Override
    public void shearFile(File sourceFile, File desFile) {
        this.copyFile(sourceFile,desFile);
        if(desFile.exists()){
            this.delFile(sourceFile);
        }else{
            System.out.println("剪切失败\n");
        }
    }

    @Override
    public void renameFile(File desFile) {
        if(file==null||!file.exists()){
            System.out.println("源文件不存在\n");
        }
        else if(!file.renameTo(desFile)){
            System.out.println("重命名失败\n");
        }
    }

    public void renameFile2(File desFile){
        Scanner in = new Scanner(System.in);
        String name;
        if(desFile==null||!desFile.exists()){
            System.out.println("源文件不存在\n");
        }else{
            System.out.println("请输入你修改后的名字\n");
            name = in.next();
            if(!desFile.renameTo(new File(desFile.getParent()+"\\"+name))){
                System.out.println("重命名失败");
            }
        }
    }

    @Override
    public void showFiles(File filePath) {
        String[] fileNames = filePath.list();
        if(fileNames!=null){
            for (String fileName : fileNames) {
                System.out.println(fileName + "\n");
            }
        }else {
            System.out.println("目录打开失败\n");
        }

    }

    @Override
    public void creatFile(File desFile, String suffix, String name) {
        File newFile = new File(desFile.getPath()+"\\"+name+"."+suffix);
        if(newFile.exists()){
            System.out.println("创建失败，文件已存在\n");
        }else{
            if(!newFile.mkdirs()){
                System.out.println("该目录不存在且创建失败\n");
            }
            try {
                if(!newFile.createNewFile()) {
                    System.out.println("创建失败，文件已存在\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void encode(File desFile) {                                  //随机生成随机长度冗杂字符串插入文件
        System.out.println("请输入密码(请不要超过20位，不含特殊字符* )\n");
        Scanner in = new Scanner(System.in);
        String password;
        StringBuffer code = new StringBuffer();
        int[] a = new int[6];
        for(int i = 0;i<a.length;i++){
            a[i]=(int)(Math.random()*10);                   //生成随机种子
            code.append(a[i]);
        }
        while(true){
            password = in.next();
            if (password.length()>20){
                System.out.println("长度不合要求，请重新输入\n");
            }else if(password.contains("*")){
                System.out.println("包含特殊字符'*'，请重新输入\n");
            }else break;
        }
        File newFile = new File(getFilePathWithoutSuffix(desFile)+" -加密"+getFileSuffix(desFile));
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(desFile);
            fout= new FileOutputStream(newFile);
            fout.write(code.toString().getBytes());                      //向文件加入随机种子
            fout.write((password+"*").getBytes());                      //向文件写入密码
            byte[] bytes = new byte[64];
            int len,j=0;
            code = new StringBuffer();
            while ((len = fin.read(bytes))!= -1){
                for (int i = 0; i < a[j%6]; i++) {
                    code.append((char)((int)(Math.random()*100)));   //产生随机字符
                }
                fout.write(code.toString().getBytes());     //向文件加入冗杂字符串
                j++;
                code =new StringBuffer();
                fout.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fin != null) fin.close();
                if(fout != null) fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void decode(File desFile) {
        String password,key,inputPassword;
        Scanner in = new Scanner(System.in);
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new FileInputStream(desFile);
            byte[] bytes = new byte[1];
            byte[] bytes2 = new byte[6];
            fin.read(bytes2);                           //读取随机种子
            key = new String(bytes2);
            int[] array = new int[key.length()];
            try {
                for (int i = 0; i < key.length(); i++) {
                array[i] = Integer.parseInt(key.substring(i, i + 1));//随机种子 从字符串变成数组
            }
            }catch (Exception e){
                System.out.println("文件未加密");
                return;
            }
            fin.read(bytes);                                           //读取密码
            password = new String(bytes);
            while (true){
                fin.read(bytes);
                if(new String(bytes).equals("*")) break;
                password = password+new String(bytes);
            }
            System.out.println("请输入密码\n");
            while(true){
                inputPassword = in.next();
                if(inputPassword.equals(password)){
                    System.out.println("密码正确，开始解密\n");
                    break;
                }else{
                    System.out.println("密码错误，是否重新输入密码？（Y/N）\n");
                    String a;
                    while(true) {
                        a = in.next();
                        if(a.equals("N")) return;
                        else if(a.equals("Y")) break;
                        else System.out.println("请输入Y或N\n");
                    }
                }
            }
            File newFile = new File(getFilePathWithoutSuffix(desFile)+"- 解密"+getFileSuffix(desFile));
            fout= new FileOutputStream(newFile);
            int len,i=0;
            bytes = new byte[64];           //文件真实类容
            bytes2 = new byte[array[i%6]];      //冗杂字符串
            while (fin.read(bytes2)!= -1&&(len = fin.read(bytes))!= -1){
                i++;
                bytes2 = new byte[array[i%6]];
                fout.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fin != null) fin.close();
                if(fout != null) fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printFile(File file) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] bytes = new byte[128];
            while (fin.read(bytes)!= -1){
                System.out.println(new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fin != null) fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeData(File file, String data) {
        try {
            FileOutputStream fout = new FileOutputStream(file,true);
            fout.write(data.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String myPath(File root,File file){
        String a= file.getAbsolutePath().substring(root.getAbsolutePath().length()+1);
        return  a;
    }
    private int listFilesMatchRegex(File root, File root2, String regex) {
        File[] files = root2.listFiles();
        int num=0;
        if(files!=null){
            for (File file : files) {
                if (myPath(root,file).matches(regex)) {
                    System.out.println(file.getAbsolutePath() + "\n");
                    num++;
                }
                if (file.isDirectory()) {
                    num+=listFilesMatchRegex(root,file,regex);
                }
            }
        }
        return num;
    }
    @Override
    public void listFilesMatchRegex(File root, String regex) {
        File[] files = root.listFiles();
        int num=0;
        System.out.println("搜索结果如下：\n");
        if(files!=null){
            for (File file : files) {
                if (myPath(root,file).matches(regex)) {
                    System.out.println(file.getAbsolutePath() + "\n");
                    num++;
                }
                if (file.isDirectory()) {
                    num+=listFilesMatchRegex(root,file,regex);
                }
            }
        }
        if(num==0){
            System.out.println("未找到匹配文件（夹）\n");
        }else{
            System.out.println("共找到"+num+"条搜索结果\n");
        }
    }

    @Override
    public void listFilesMatchString(File root, String name) {
        File[] files = root.listFiles();
        int num=0;
        System.out.println("搜索结果如下：\n");
        if(files!=null){
            for (File value : files) {
                if (value.getName().toLowerCase().contains(name.toLowerCase())) {
                    System.out.println(value.getAbsolutePath() + "\n");
                    num++;
                }
                if (value.isDirectory()) {
                    listFilesMatchString(root, name);   //好像num没加上去
                }
            }
        }
        if(num==0){
            System.out.println("未找到匹配文件（夹）\n");
        }else{
            System.out.println("共找到"+num+"条搜索结果\n");
        }
    }

}

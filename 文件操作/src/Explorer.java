import java.io.File;

public interface Explorer
{
	/**
	 * 将文件(夹)从源文件夹复制到目的文件夹
	 * @param sourceFile
	 * @param desFile
	 */
	public void copyFile(File sourceFile,File desFile);
	
	/**
	 * 删除文件或文件夹
	 * @param file
	 */
	public void delFile(File file);

	/**
	 *将文件(夹)从源文件夹剪切到目的文件夹
	 * @param sourceFile
	 * @param desFile
	 */
	public void shearFile(File sourceFile,File desFile);

	/**
	 * 重命名文件
	 * @param desFile
	 */
	public void renameFile(File desFile);

	/**
	 * 打印文件夹中的所有文件的文件名
	 * @param file 传入的是目标文件夹的File
	 */
	public void showFiles(File filePath);
	
	/**
	 * 创建在指定文件夹中创建文件
	 * @param desFile 目标文件夹
	 * @param suffix 文件后缀
	 * @param name 文件名
	 */
	public void creatFile(File desFile,String suffix,String name);
	
	/**
	 * 将目标文件以特定方式加密
	 * @param desFile
	 */
	public void encode(File desFile);
	
	/**
	 * 将目标文件以特定方式解密
	 * @param desFile
	 */
	public void decode(File desFile);
	
	/**
	 * 将文件内容打印出来
	 * @param file
	 */
	public void printFile(File file);
	
	/**
	 * 将数据写入文件
	 * @param file
	 * @param data
	 */
	public void writeData(File file,String data);
	
	/**
	 * 根据正则表达式在查找对应文件
	 * @param root
	 * @param regex
	 */
	public void listFilesMatchRegex(File root,String regex);
	
	/**
	 * 模糊查找
	 * @param root
	 * @param name
	 */
	public void listFilesMatchString(File root,String name);
}

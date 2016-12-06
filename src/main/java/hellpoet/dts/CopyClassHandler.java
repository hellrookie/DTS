package hellpoet.dts;

import java.io.*;

public class CopyClassHandler {
	public static CopyClassHandler instance;
	
	private CopyClassHandler(){
		
	}
	
	public static CopyClassHandler getInstance(){
		if(instance == null){
			instance = new CopyClassHandler();
		}
		return instance;
	}
	
	public void copyClasses(String sourcePath, String destPath, Boolean overwrite) throws Exception{
		copyFolder(new File(sourcePath), new File(destPath), overwrite);
	}
	
	private void copyFile(File sourceFile, File destFile, Boolean overwrite) throws Exception {
		if (checkBeforeCopy(sourceFile, destFile, overwrite)) {
			FileInputStream inputStream = new FileInputStream(sourceFile);
			FileOutputStream outputStream = new FileOutputStream(destFile);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
		}
	}
	
	private void copyFolder(File sourceFolder, File destFolder, Boolean overwrite) throws Exception{
		if(checkBeforeCopy(sourceFolder, destFolder, overwrite)){
			File parentFolder = destFolder.getParentFile();
			if(parentFolder == null || !parentFolder.isDirectory()){
				throw new Exception(String.format("Cannot create folder: %s because parent folder not exist.", destFolder.getAbsolutePath()));
			}
		}
		for(String childFileName : sourceFolder.list()){
			copyFile(new File(sourceFolder, childFileName),
					new File(destFolder, childFileName),
					overwrite);
		}
	}
	
	/*
	 * Check source file exist.
	 * If option overwrite is true, try to delete destination file. If the option is false, check if need copy file from source to destination.
	 * 
	 * @return return true means need copy, false means not.
	 */
	private boolean checkBeforeCopy(File sourceFile, File destFile, Boolean overwrite) throws Exception{
		if (overwrite) {
			delete(destFile);
		}else{
			return !destFile.exists();
		}
		
		if(!sourceFile.exists()){
			throw new Exception(String.format("Ths source file: %s is not found.", sourceFile.getAbsolutePath()));
		}
		return true;
	}
	
	private void delete(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}else if(file.isDirectory()){
				String[] files = file.list();
				for(String name : files){
					delete(new File(getChildPath(file, name)));
				}
				file.delete();
			}
		}
	}
	
	private String getChildPath(File parent, String childName){
		return String.format("%s/%s", parent.getAbsolutePath(), childName);
	}
}

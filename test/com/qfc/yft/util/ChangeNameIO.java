package com.qfc.yft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import android.content.Context;

public class ChangeNameIO {
	/*public static void main(String[] args) throws IOException {
//		readFile();
//		changeName();
//		getNames();
	}*/

	/**
	 * @param c
	 * @return
	 */
	public static String readFromSomeWhere(Context c){
		
		
		return JackUtils.readFromFile(c, "fromSomewhere.xml");
	}
	public static String readFile(final String filepath){
//		final String filePath = "C:/Users/taotao/Desktop/log.txt";
		try {
            String encoding="UTF-8";
            File file=new File(filepath);
            if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//���ǵ������ʽ
                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = "";
                StringBuffer lineTxt = new StringBuffer();
                String line = "";
                while((line = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
                    lineTxt.append(line);
                }
                read.close();
                return lineTxt.toString();
    }else{
        System.out.println("�Ҳ���ָ�����ļ�");
    }
    } catch (Exception e) {
        System.out.println("��ȡ�ļ����ݳ���");
        e.printStackTrace();
    }
		return filepath;
		
	}
	private static void getNames() {
		final String dirPath = "C:/Users/taotao/Desktop/temp/person";
		File dir = new File(dirPath);
		  if(!dir.exists())
		  {
//			  oldFile.createNewFile();
			  print("no file");
			  return ;
		  }
		
		  File[] files = dir.listFiles();
		  for(File file : files){
			 String name = file.getName();
			 if(!name.startsWith("category")){//����
				 
				 System.out.println("R.id."+file.getName().replace(".png", ","));//����
			 }
		  }
	}

	private static void changeName() throws IOException {

		final String dirPath = "C:/Users/taotao/Desktop/pl_svc";//·��
		 
		  File dir = new File(dirPath);
		  if(!dir.exists())
		  {
//			  oldFile.createNewFile();
			  print("no file");
			  return ;
		  }
		  print ("�ļ��д��ڣ�"+dir.getName());
		  
		  File[] files = dir.listFiles();//��������ļ��������ļ�
		  for(File file : files){
			  System.out.println("�޸�ǰ��"+file.getName());
			  Scanner scan = new Scanner(System.in); //�ȴ�����̨����ָ��
			  if(scan.nextInt()<1) continue;
			  String newFilename = file.getName().replace("websvc", "mobile");//�����Զ�����������ļ���
			  File newFile = new File(dirPath + File.separator + newFilename);
			  boolean result = file.renameTo(newFile);
			  print((result?"�ɹ�����":"ʧ�ܸ���")+":"+newFilename);
		  }
		  /*
		   * 
		   */
		  /*String rootPath = oldFile.getParent();
		  System.out.println("��·���ǣ�"+rootPath);
		  File newFile = new File(rootPath + File.separator + "PMSTmp");
		  System.out.println("�޸ĺ��ļ������ǣ�"+newFile.getName());
		  if (oldFile.renameTo(newFile)) 
		  {
			  print("�޸ĳɹ�!");
		  } 
		  else 
		  {
			  print("�޸�ʧ��");
		  }*/
	}
	
	private static void print(String str){
		System.out.println(str);
	}
}

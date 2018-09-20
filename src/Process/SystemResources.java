package Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SystemResources {
	private static int[] Resource_max = new int[8];
	private static int[] Resource_remain= new int[8];
	private static 	String filename = "setting/sys_resource.txt";
	
	public static void init() {
		//读取文件夹中的文件名
		File file = new File(filename);
		ArrayList<String> al = new ArrayList<String>();
		String[] arr = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			temp = reader.readLine();
			al.addAll(Arrays.asList(temp.split(",")));
			arr = new String[al.size()];
			al.toArray(arr);
			for(int i = 0; i<al.size();i++) {
				Resource_max[i] = Integer.parseInt(arr[i]);
				Resource_remain[i] = Integer.parseInt(arr[i]);
			}
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(reader!=null) {
				try {
					reader.close();
				}catch(IOException e1) {
					
				}
			}
		}
			    
			   
	}
	
	public static void printResource_max() {
		System.out.println("系统资源最大数组：");
		for(int i = 0 ; i < Resource_max.length;i++) {
			System.out.println(Resource_max[i]);
		}
	}
	
	public static void printResource_remain() {
		System.out.println("系统资源余留数组：");
		for(int i = 0 ; i < Resource_remain.length;i++) {
			System.out.println(Resource_remain[i]);
		}
	}
	
	//设置设备的最大数组
	public static void setDevmax(int[] max) {
		for(int i = 3,j = 0;j < max.length;i++,j++) {
			Resource_max[i] = max[j];
		}
	}
	//设置设备的余留数组
	public static void setDevremain(int[] remain) {
		for(int i = 3,j = 0;j < remain.length;i++,j++) {
			Resource_remain[i] = remain[j];
		}
	}
	
	//获得系统资源最大值数组
	public int[] getResourcemax(){
		int [] res = new int[8];
		for(int i = 0; i< res.length;i++) {
			res[i] = Resource_max[i];
		}
		return res;
	}
	
	//初始时，设置最大资源和余留资源
	public static void setResource(int[] max) {
		for(int i = 0;i < max.length;i++) {
			Resource_max[i] = max[i];
			Resource_remain[i] = max[i];
		}
	}
	
	//获得系统余下资源数组
	public int[] getResource_remain() {
		int [] res = new int[8];
		for(int i = 0; i< res.length;i++) {
			res[i] = Resource_remain[i];
		}
		return res;
	}
	
	//某类资源减少一定数量 
	public static int reqResource(int[] max_list,int[] need_list) {
		//int res = -1;
		for(int i = 0;i < Resource_max.length;i++) {
			if(Resource_max[i] < max_list[i]) {
				return -1; //代表需要杀死这个进程
			}
		}
		for(int i = 3;i < Resource_max.length;i++) {//判断设备资源请求是否合法
			if(max_list[i] > 1 ) {
				return -1; //代表需要杀死这个进程
			}
		}
		for(int i = 0;i < Resource_remain.length;i++) {
			if(Resource_remain[i] < need_list[i]) {
				return  0;//代表资源不够
			}
		}
		return 1;//代表资源充足
	}
	
	
	public static void decResource(int[] need_list) {
		for(int i = 0;i < Resource_remain.length;i++) {
			Resource_remain[i] -= need_list[i];
		}
	}
	
	//某类资源增加一定数量
	public static void addResource(int[] give_list) {
		for(int i = 0;i < Resource_remain.length;i++) {
			Resource_remain[i] += give_list[i];
		}
	}
	
	public static int convertResourceType(ResourceType source) {
		int res = source.ordinal();
		return res;
	}
	
	
	
}

package memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import Process.Process;


public class Memory {
	static Page[] pages=new Page[30];
	static int pageUse;//已使用数据页数
	static int insNum;//指令条数
	static int pageNum;//初始数据页数
	static int insPage;//代码段对应物理页号
	private static int[] sourceList=new int[8];
	
	public static double getPageUseRate() {//获取内存使用率(实时刷新)
		return (double)pageUse/30.0*1.00;
	}
	
	public Memory() {}
	
	public static int[] getSourceList() {//获取资源数组
		return sourceList;
	}
	
	public static void InitPage() {//初始化内存界面
		try {
			
			for(int i=0;i<pages.length;i++) {
				pages[i]=new Page();
			}
			for(int j=0;j<pages.length;j++) {
		         for(int i=0;i<30;i++) {
		        	 pages[j].insList=new ArrayList<String>();
		         }
			}
			System.out.println("内存页面初始化成功！");
		}catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void getTxt(File file){//读取指令条数，初始数据页数以及资源数组
	    try{
	         BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	         String s ="";
	         int lines=0;
	         while((s=br.readLine())!=null){
	        	 lines++;
	        	 if(lines==1) {
	        		 	insNum=Integer.parseInt(s);
	        		 	
		        	 }
	        	 if(lines==2) {
	        		 	pageNum=Integer.parseInt(s);
	        	 }
		         if(lines==4) {
		        	 	String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
		            	char[] c=str.toCharArray();
		        	 	for(int i=0;i<str.length();i++) {
		        	 		sourceList[i]=c[i]-48;
		            	}	
		        	 }
	          }
	         System.out.println("成功读取程序txt！");
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	}	
	
	

	public static void InitMemory(File file,int pid) {//实际给初始数据页以及代码段分配内存
		 try{
	         BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	         String s ="";
	         int lines=0;
	         for(int i=0;i<pages.length;i++) {    //分配代码段页面
	        	 if(pages[i].getState()==0) {
	        		 insPage=i;
	        		 pages[i].setState(1);
	        		 pages[i].setPid(pid);
	        		 pages[i].setVirPage(0);
	        		 pages[i].setTime(System.currentTimeMillis());
	        		 pageUse++;
	        		 break;
	        	 }
	         
	         }
	         
	         while((s=br.readLine())!=null ){
	        	 lines++;
		         if(lines==3) {
		        	 	String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
		            	char[] c=str.toCharArray();
		            	int[] m=new int[str.length()];
		        	 	for(int i=0;i<str.length();i++) {
		        	 		m[i]=c[i]-48;
		            	}	
		        	 	int j=0;
		        	 	for(int i=0;i<pages.length;i++) {
		        	 		if(pages[i].getState()==0) {
		        	 			pages[i].setVirPage(m[j]);
		        	 			pages[i].setState(1);
		        	 			pages[i].setPid(pid);
		        	 			pages[i].setTime(System.currentTimeMillis());
		        	 			j++;				        	 			
		        	 			pageUse++;
		        	 			if(j==m.length) {
		        	 				break;
		        	 			}
		        	 			
		        	 		}
		        	 	}
		         } 
		         if(lines>4) {
		        	 	//pages[insPage].insList=new ArrayList<String>(insNum);
		        		pages[insPage].insList.add(s);
		        		System.out.println("成功加入"+s);
		        		//System.out.println(s);
		         }
		     }
	         System.out.println("数据页初始分配成功！");
		     br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
		 
}	
	
	public static boolean isFree() {//判断进程是否可创建
		try {
			/*for(int i=0;i<pages.length;i++) {
				pages[i]=new Page();
			}*/
			int j=0;
			int total=pageNum+1;
			for(int i=0;i<pages.length;i++) {
				if(pages[i].getState()==0) {
					j++;
					if(j==total) {
						return true;
					}
				}
			}
			
		}catch(Exception e){
            e.printStackTrace();
        }
		return false;
	}
	
	
	public static boolean seekPage(int pageNO,int pid) {
		try {
			for(int i=0;i<pages.length;i++) {
				if(pages[i].getPid()==pid && pages[i].getVirPage()==pageNO) {
					return true;
				}
			}
			for(int i=0;i<pages.length;i++) {
				if(pages[i].getState()==0) {
					pages[i].setPid(pid);
					pages[i].setState(1);
					pages[i].setVirPage(pageNO);
					pages[i].setTime(System.currentTimeMillis());
					pageUse++;
					return true;
				}
			}
		}catch(Exception e){
            e.printStackTrace();
        }
		return false;
	}
	
	public static boolean setPCB(Process m) {//set PCB
		try {
			m.setActivemm(insPage);//代码物理页号
			m.setLimit(insNum);//指令条数
			System.out.println("成功set PCB！");
			return true;
		}catch(Exception e){
            e.printStackTrace();
        }
		return false;
	}
	
	public static void releasePro(int pid) {//进程结束释放内存
		for(int i=0;i<pages.length;i++) {
			if(pages[i].getPid()==pid) {
				pages[i].setPid(-1);
				pages[i].setState(0);
				pages[i].setVirPage(-1);
				pages[i].setTime(0);
				pageUse--;
				pages[i].insList=null;
				System.out.println("释放该内存页面");
			}
		}
	}
	
	
	public static String getIns(int pageNo,int offset) {//获得指定指令
		String str = null;
		try {
			//pages[pageNo].insList=new ArrayList<String>(insNum);
			str=(String) pages[pageNo].insList.get(offset);
		}catch(Exception e){
            e.printStackTrace();
        }
		return str;
		
	}
	
public static void replacePage(int pid,int pageNO){//中断置换页面
		long endTime=System.currentTimeMillis();
		long[] dur=new long[pages.length];
		int replace_page=0;
		long maxTime=endTime-pages[0].getTime();
		for(int i=1;i<pages.length;i++) {
			dur[i]=endTime-pages[i].getTime();
			if(dur[i]>maxTime) {
				replace_page=i;
				maxTime=dur[i];
			}
		}
		pages[replace_page].setState(1);
		pages[replace_page].setPid(pid);
		pages[replace_page].setVirPage(pageNO);
		pages[replace_page].setTime(System.currentTimeMillis());
		pages[replace_page].insList=null;
		System.out.println("页面置换成功！");
	}
}

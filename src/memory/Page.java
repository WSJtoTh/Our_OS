package memory;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Page {
		private int state;//页状态
		private int virPage;//对应的虚拟页号 ，代码段虚拟页面为0
		private int pid;//进程号
		private long time;
		public Page() {
			insList=new ArrayList();
		}
		public ArrayList<String> insList;//指令集
		public long getTime() {
			return this.time;
		}
		public void setTime(long time) {
			this.time=time;
		}
		public int getState() {
			return this.state;
		}
		public void setState(int state) {
			this.state=state;
		}
		
		public int getVirPage() {
			return this.virPage;
		}
		public void setVirPage(int virPage) {
			this.virPage=virPage;
		}
		public int getPid() {
			return this.pid;
		}
		public void setPid(int pid) {
			this.pid=pid;
		}
		/*public ArrayList getInstrct(File file){
		    try{
		         BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
		         String s ="";
		         int lines=0;
		         while((s=br.readLine())!=null){
		        	 lines++;
			         if(lines>3) {
			        		insList.add(s);
			        	 }
		          }
		            br.close();    
		        }catch(Exception e){
		            e.printStackTrace();
		        }
			return insList;
		}*/
	
		
}

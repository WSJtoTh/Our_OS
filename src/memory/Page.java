package memory;

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
		public void setTime(long time) {//分配时间
			this.time=time;
		}
		public int getState() {
			return this.state;
		}
		public void setState(int state) {//页面状态
			this.state=state;
		}
		
		public int getVirPage() {//虚拟页面号
			return this.virPage;
		}
		public void setVirPage(int virPage) {
			this.virPage=virPage;
		}
		public int getPid() {//进程pid
			return this.pid;
		}
		public void setPid(int pid) {
			this.pid=pid;
		}
		
}

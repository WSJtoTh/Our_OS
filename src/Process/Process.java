package Process;
import Device.DevType;
import Device.DevController;

public class Process {
	private static int id_avl = 0;
	private int pid; //进程id
	//private int prio; //进程优先级
	private DevType signal;//阻塞进程的设备类型
	private PState state;//进程状态
	private int active_mm;//存储代码的页号
	private int pc;//pc当前值
	private int limit;//指令界限寄存器
	private int[] resource_max = new int[8];//资源最大需求数组
	private int[] resource_hold = new int[8];//持有资源数组
	private int[] resource_need = new int[8];//下一时间片资源需求数组
	private int waitingtime;//进程等待时间片
	private int runningtime;//进程执行时间片
	
	//传入：资源类型 功能：外设资源加一
	public void addResource(DevType dev) {
		int index = dev.ordinal() + 3;
		resource_need[index] += 1; 
	}
	
	public void bindDev() {
		for(int i = 3;i < resource_hold.length;i++) {
			int num = resource_hold[i];
			for(int j = 0;j < num;j++) {
				System.out.println(num);
				DevController.wait(DevType.values()[i-3], pid);
				System.out.println("dev:"+DevType.values()[i-3]+"pid:"+pid);
			}
		}
	}
	
	public void releaseDev(DevType dev) {
		int index = dev.ordinal() + 3;
		resource_hold[index] -= 1;
	}
	
	
	public void addCommonResource(int[] need) {
		for(int i = 0;i < need.length;i++) {
			resource_need[i] += need[i];
		}
	}
	
	public void setSignal(DevType signal) {
		this.signal = signal;
	}
	
	public DevType getSignal() {
		return this.signal;
	}
	
	public String convertState() {//返回枚举类型对应的字符串
		return this.state.name();
	}
	
	public String printNeed () {
		String s = null;
		for(int i = 0;i < resource_need.length;i++) {
			s += resource_need[i];
			s += "\t";
					
		}
		return s;
	}
	
	public String toString() {
		String s;
		s = "pid = " + pid + " state = " + state + "\n";
		s += "resource_max : ";
		for(int i = 0;i < resource_max.length;i++) {
			s += resource_max[i];
			s += "\t";
					
		}
		s += "\n";
		s += "resource_need: ";
		for(int i = 0;i < resource_need.length;i++) {
			s += resource_need[i];
			s += "\t";
					
		}
		s += "\n";
		s += "resource_hold: ";
		for(int i = 0;i < resource_hold.length;i++) {
			s += resource_hold[i];
			s += "\t";
					
		}
		s += "\nwait_signal: " + signal + "\nactive_mm: "+active_mm+"\npc: "+pc+"\nlimit: "+limit+"\nwaitingtime: "+waitingtime+"\nrunningtime: "+runningtime;
		
		return s;
	}
	
	
	public void alloc_pid() {//分配进程id
		id_avl += 1;
		this.pid = id_avl;
	} 
	
	public int getPid() {
		return this.pid;
	}
	
	public void setState(PState state) {//设置状态
		this.state = state;
	}
	
	public PState getState() {
		return this.state;
	}
	
	public void setActivemm(int pagenum) {
		this.active_mm = pagenum;
	}
	
	public int getActivemm() {
		return this.active_mm;
	}
	
	public void setPC(int pc) {
		this.pc = pc;
	}
	
	public int getPC() {
		return this.pc;
	}
	
	public boolean incPC() {
		if(this.pc + 1 < this.limit) {
			this.pc += 1;
			return true;
		}
		else {
			System.out.println("pid:"+pid+" process finished!");
			return false;
		}
		
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getLimit() {
		return this.limit;
	}
	
	public void setResource_max(int[] max) {
		for(int i = 0; i<max.length;i++) {
			resource_max[i] = max[i];
		}
	}
	
	public int[] getResource_max() {
		int [] res = new int[8];
		for(int i = 0; i< res.length;i++) {
			res[i] = resource_max[i];
		}
		return res;
	}
	
	public void setResource_hold(int[] hold) {
		for(int i = 0; i<hold.length;i++) {
			resource_hold[i] = hold[i];
		}
	}
	
	public int[] getResource_hold() {
		int [] res = new int[8];
		for(int i = 0; i< res.length;i++) {
			res[i] = resource_hold[i];
		}
		return res;
	}
	
	public void setResource_need(int[] need) {
		for(int i = 0; i<need.length;i++) {
			resource_need[i] = need[i];
		}
	}
	
	public int[] getResource_need() {
		int [] res = new int[8];
		for(int i = 0; i< res.length;i++) {
			res[i] = resource_need[i];
		}
		return res;
	}
	
	public void incWaitingtime() {
		this.waitingtime += 1;
	}
	
	public int getWaitingtime() {
		return this.waitingtime;
	}
	
	public void incRunningtime() {
		this.runningtime += 1;
	}
	
	public int getRunningtime() {
		return this.runningtime;
	}
	
	public void resetWaitingtime() {
		this.waitingtime = 0;
	}
	
}

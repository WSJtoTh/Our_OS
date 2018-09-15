package Process;
import Device.DevType;

public class Process {
	private static int id_avl = 0;
	private int pid; //进程id
	//private int prio; //进程优先级
	private DevType signal;//阻塞进程的设备类型
	private PState state;//进程状态
	private int active_mm;//存储代码的页号
	private int pc;//pc当前值
	private int limit;//指令界限寄存器
	private int[] resource_max;//资源最大需求数组
	private int[] resource_hold;//持有资源数组
	private int[] resource_need;//下一时间片资源需求数组
	private int waitingtime;//进程等待时间片
	private int runningtime;//进程执行时间片
	
	//传入：资源类型 功能：外设资源加一
	public void addResource(DevType dev) {
		int index = dev.ordinal() + 3;
		resource_need[index] += 1; 
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
	
	public String toString() {
		String s;
		s = "pid = " + pid + " state = " + convertState();
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
		this.resource_max = max;
	}
	
	public int[] getResource_max() {
		return this.resource_max;
	}
	
	public void setResource_hold(int[] hold) {
		this.resource_hold = hold;
	}
	
	public int[] getResource_hold() {
		return this.resource_hold;
	}
	
	public void setResource_need(int[] need) {
		this.resource_need = need;
	}
	
	public int[] getResource_need() {
		return this.resource_need;
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

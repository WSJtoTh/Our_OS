package Process;
import Device.*;
import java.util.*;
import memory.*;
import pro.*;

public class ProcessMGT {
	static LinkedList<Process> allProcess = new LinkedList<>();//全部进程
	static LinkedList<Process> ready = new LinkedList<>();//准备队列
	static LinkedList<Process> running = new LinkedList<>();//运行队列
	static LinkedList<Process> terminated = new LinkedList<>();//结束队列
	static LinkedList<Process> waiting = new LinkedList<>();//等待队列
	static LinkedList<Process> blocking = new LinkedList<>();//I/O阻塞队列
	
	//销毁terminated的进程
	public static void killProcess() {
		while(!terminated.isEmpty()) {
			Process process = terminated.pop();
			allProcess.remove(process);
			//调用释放内存空间的函数
			Memory.releasePro(process.getPid());
		}
	}
	
	//移出running队列
	public static Process popRunning() {
		Process process = null;
		try {
			process = running.pop();
		}
		catch(NoSuchElementException e) {
			process = null;
			System.out.println("没有running的进程！");
		}
		return process;
	}
	
	//根据进程id获得进程
	public Process getPCBBypid(int pid) {
		for(Process process: allProcess) {
			if(process.getPid() == pid) {
				return process;
			}
		}
		return null;
	}
	
	//获取所有进程
	public LinkedList<Process> getAllProcess(){
		return allProcess;
	}
	
	//打印当前运行进程状态
	public void printRunning() {
		for(Process process: running) {
			System.out.println(process.toString());
		}
	}
	
	//打印所有进程状态
	public void printAll() {
		for(Process process: allProcess) {
			System.out.println(process.toString());
		}
	}
	
	//创建进程
	public static Process createProcess() {
		Process p = new Process();
		p.alloc_pid();
		p.setPC(0);
		return p;
	}
	
	
	//将创建的进程加入队列
	public static void addProcess(Process process) {	
		allProcess.add(process);
		ready.add(process);
	}
	
	
	//等待队列的进程等待时间加1
	public static void incWaitTimes() {
		for(Process process:waiting) {
			process.incWaitingtime();
		}
	}
	
	//释放资源
	public static void releaseResource(Process process) {
		int[] re = new int[9];
		for(int i = 0;i < re.length;i++) {
			re[i] = 0;
		}
		SystemResources.addResource(process.getResource_hold());
		process.setResource_hold(re);
	}
	
	
	//进程进入等待队列，释放资源
	public static void waitProcess(Process process) {
		process.setState(PState.WAITING);
		releaseResource(process);
		waiting.add(process);
	}
	
	
	//进程进入running队列
	public static void runProcess(Process process) {
		process.setState(PState.RUNNING);
		process.incRunningtime();
		running.add(process);
	}
	
	//进程进入ready队列
	public static void readyProcess(Process process) {
		process.setState(PState.READY);
		ready.add(process);
	}
	
	//进程进入terminated队列,释放资源
	public static void terminateProcess(Process process) {
		process.setState(PState.TERMINATED);
		releaseResource(process);
		terminated.add(process);
	}
	
	//进程进入阻塞队列 被外设阻塞
	public void blockProcess(Process process,DevType dev) {
		process.setState(PState.INTERRUPTIBLE);
		process.setSignal(dev);
		blocking.add(process);
	}
	
	//唤醒进程
	public static void wakeUpProcess(int pid,DevType dev) {
		Process p = null;
		for(Process process:blocking) {
			if(process.getPid() == pid && process.getSignal() == dev) {
				p = process;
				break;
			}
		}
		if(p != null) {
			blocking.remove(p);
			readyProcess(p);
			p.setSignal(null);
		}
		
	}
	
	//改变waiting队列的顺序
	public void changeWatingSequence() {
		Process p = waiting.pop();
		waiting.add(p);
	}
	
	public static void culNextNeed(Process p) {
		int[] need = new int[8];
		for(int i=0;i < need.length;i++) {//置0
			need[i] = 0;
		}
		p.setResource_need(need);
		
		//往后取五条指令，计算下一个时间片资源
		int temppc = p.getPid();
		for(int i = 0; temppc<p.getLimit() &&i < 5 ;i++,temppc++) {
			ins.ExeInstruction(Memory.getIns(p.getActivemm(),temppc), p, 0);			
		}
		
	}
	

	//时间片结束调度
	public static void timeoutSchedule() {
		LinkedList<Process> move_to_ready = new LinkedList<>();//存放到ready队列的process
		LinkedList<Process> move_to_kill = new LinkedList<>();//存放到terminated队列的process
		
		incWaitTimes();//waiting队列中的设备等待时间加1
		//更新一次设备资源表
		SystemResources.setDevremain(DevController.getAvailDevTable());
		SystemResources.setDevmax(DevController.getEntireDevTable());
		
		try {
			Process process = running.pop();
			//先判断当前运行的process是否能进ready
			culNextNeed(process);//先计算下一个时间片的资源 更新need 
			int req = SystemResources.reqResource(process.getResource_max(),process.getResource_need());
			if(req == 1) {
				SystemResources.decResource(process.getResource_need());
				process.setResource_hold(process.getResource_need());
				move_to_ready.add(process);
			}
			else if(req == -1) {
				move_to_kill.add(process);
			}
			else {
				releaseResource(process);
				waiting.add(process);
			}
			
		}catch(NoSuchElementException e) {
			System.out.println("running队列为空，上一个进程执行完毕！");
		}
		
		
		//遍历waiting中的process 是否能进ready ？？？？？？等待过多时间片该如何处理
		for(Process wp:waiting) {
			int res = SystemResources.reqResource(wp.getResource_max(),wp.getResource_need());
			if( res == 1) {
				SystemResources.decResource(wp.getResource_need());
				wp.setResource_hold(wp.getResource_need());
				move_to_ready.add(wp);
				wp.resetWaitingtime();//重置等待时间
			}
			else if(res == -1) {
				move_to_kill.add(wp);
			}
		}
		//移动可以到ready队列的进程
		for(Process p:move_to_ready) {
			readyProcess(p);
			waiting.remove(p);
		}
		//移动可以到terminated队列的进程
		for(Process p:move_to_kill) {
			terminateProcess(p);
			waiting.remove(p);
		}
				
		//清一次terminated
		killProcess();
		
		//取ready到running
		running.add(ready.pop());
	}

	
}

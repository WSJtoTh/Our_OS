package Process;
import Device.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import memory.*;
import pro.*;

public class ProcessMGT {
	static List<Process> allProcess = new CopyOnWriteArrayList<>();;//全部进程
	static List<Process> ready = new CopyOnWriteArrayList<>();//准备队列
	static LinkedList<Process> running = new LinkedList<>();//运行队列
	static LinkedList<Process> terminated = new LinkedList<>();//结束队列
	static LinkedList<Process> waiting = new LinkedList<>();//等待队列
	static LinkedList<Process> blocking = new LinkedList<>();//I/O阻塞队列
	static final int threshold = 4;
	
	//打印terminated的情况
	public static void printTerminated() {
		System.out.println("终结进程情况:");
		for(Process process: terminated) {
			System.out.println(process.toString());
		}
	}
	
	//打印blocking的情况
	public static void printBlocking() {
		System.out.println("阻塞进程情况:");
		for(Process process: blocking) {
			System.out.println(process.toString());
		}
	}
	
	//打印ready的情况
	public static void printReady() {
		System.out.println("进程准备情况:");
		for(Process process: ready) {
			System.out.println(process.toString());
		}
	}
	
	//打印当前运行进程状态
	public static void printRunning() {
		System.out.println("运行进程情况:");
		for(Process process: running) {
			System.out.println(process.toString());
		}
	}
	
	//打印所有进程状态
	public static void printAll() {
		printReady();
		printRunning();
		printWaiting();
		printTerminated();
		printBlocking();
	}
	
	//打印所有等待状态
		public static void printWaiting() {
			System.out.println("等待进程情况:");
			for(Process process: waiting) {
				System.out.println(process.toString());
			}
		}
	
	
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
	public static Process getRunning() {
		Process process = null;
		try {
			process = running.getFirst();
		}
		catch(NoSuchElementException e) {
			process = null;
			System.out.println("没有running的进程！");
		}
		return process;
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
	public static Process getPCBBypid(int pid) {
		for(Process process: allProcess) {
			if(process.getPid() == pid) {
				return process;
			}
		}
		return null;
	}
	
	//获取所有进程
	public static List<Process> getAllProcess(){
		return allProcess;
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
		process.setState(PState.READY);
		allProcess.add(process);
		ready.add(process);
	}
	
	
	//等待队列的进程等待时间加1
	public static void incWaitTimes() {
		for(Process process:waiting) {
			process.incWaitingtime();
		}
	}
	
	//等待队列的进程等待时间加1
	public static void incBlockingTimes() {
		for(Process process:blocking) {
			process.incBlockingtime();
		}
	}
	
	//释放资源
	public static void releaseResource(Process process) {
		int[] re = new int[8];
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
	public static void blockProcess(Process process,DevType dev) {
		process.setState(PState.INTERRUPTIBLE);
		process.setSignal(dev);
		running.pop();
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
			p.setSignal(null);
			p.releaseDev(dev);
			p.resetBlockingtime();
			if(p.getPC() % 5 == 0) {//如果刚好是一组的最后一条指令且是io指令，先计算下5组指令
				culNextNeed(p);
				waitProcess(p);
				blocking.remove(p);
				System.out.println("五条指令末尾，唤醒到waiting队列！！！！！！！！！");
			}else {
				readyProcess(p);
				blocking.remove(p);
			}
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
		int temppc = p.getPC();
		for(int i = 0; temppc<p.getLimit() &&i < 5 ;i++,temppc++) {
			ins.ExeInstruction(Memory.getIns(p.getPid(),p.getActivemm(),temppc), p, 0);			
		}
		
	}
	

	//时间片结束调度
	public static void timeoutSchedule() {
		LinkedList<Process> move_to_ready = new LinkedList<>();//存放到ready队列的process
		LinkedList<Process> move_to_kill = new LinkedList<>();//存放到terminated队列的process
		LinkedList<Process> move_to_kill_block = new LinkedList<>();//存放到terminated队列的process
		
		incWaitTimes();//waiting队列中的设备等待时间加1
		incBlockingTimes();//阻塞队列中的设备等待时间加1
		//更新一次设备资源表
		int[] max = DevController.getEntireDevTable();
		int[] remain = DevController.getAvailDevTable();
		SystemResources.setDevremain(remain);
		SystemResources.setDevmax(max);
		
		try {
			Process process = running.pop();
			//释放现有的资源
			releaseResource(process);
			//先判断当前运行的process是否能进ready
			culNextNeed(process);//先计算下一个时间片的资源 更新need 
			System.out.println("pid:"+process.getPid()+"进程下一时间片请求的资源：");
			System.out.println(process.printNeed());
			int req = SystemResources.reqResource(process.getResource_max(),process.getResource_need());
			if(req == 1) {
				int[] need = new int[8];
				SystemResources.printResource_remain();
				SystemResources.decResource(process.getResource_need());
				process.setResource_hold(process.getResource_need());
				process.setResource_need(need);
				process.bindDev();
				SystemResources.printResource_remain();
				move_to_ready.add(process);
			}
			else if(req == -1) {
				move_to_kill.add(process);
			}
			else {
				releaseResource(process);
				waitProcess(process);
			}
			
		}catch(NoSuchElementException e) {
			System.out.println("running队列为空！");
		}
		
		SystemResources.printResource_max();
		SystemResources.printResource_remain();
		//遍历waiting中的process 是否能进ready ？？？？？？等待过多时间片该如何处理
		for(Process wp:waiting) {
			int res = SystemResources.reqResource(wp.getResource_max(),wp.getResource_need());
			if( res == 1) {
				int[] need = new int[8];
				SystemResources.printResource_remain();
				SystemResources.decResource(wp.getResource_need());
				wp.setResource_hold(wp.getResource_need());
				wp.setResource_need(need);
				wp.bindDev();
				SystemResources.printResource_remain();
				move_to_ready.add(wp);
				wp.resetWaitingtime();//重置等待时间
			}
			else if(res == -1) {
				move_to_kill.add(wp);
			}
		}
		
		//遍历blocking的队列，blocking时间片大于3，移到move_to_kill
		for(Process bp:blocking) {
			int blockingtime = bp.getBlockingtime();
			if(blockingtime > threshold) {
				bp.releaseDevResource();//释放设备资源
				move_to_kill_block.add(bp);
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
		//移动可以到terminated队列的进程
		for(Process p:move_to_kill_block) {
			terminateProcess(p);
			blocking.remove(p);
		}
		
		printAll();
		
		//清一次terminated
		killProcess();
		
		
		
		//取ready到running
		try {
			Process addrun = ready.get(0);
			running.add(addrun);
			ready.remove(addrun);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("当前ready队列没有进程！");
		}
		
	}

	
}

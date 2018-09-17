package Process;
import pro.ins;
import memory.Memory;
import Interrupt.InterService;
import Interrupt.InterType;
import timer.timer;
import Device.*;

public class Run {
	final static long timeInterval = 1000;
	static Thread producerthread = new ProducerThread();
	
	public static void main(String args[]) throws InterruptedException {
		boolean runflag = false;//取值标志
		Process process = null;
		boolean fetchflag = true;//是否取进程
		InterType intertype = InterType.NULL;
		
		//初始化资源
		SystemResources.init();//初始化系统资源
		Memory.InitPage();//初始化内存资源
		
		producerthread.start();//开启生产者消费者模型
		
		timer t = new timer();//计时器启动
		t.start();
		
		//设备管理器初始化
		DevController devc = new DevController();
		devc.startDevController();
		
		System.out.println("resource_available");
		int[] a = DevController.getAvailDevTable();
		for(int i =0;i<a.length;i++) {
			System.out.print(a[i]);
		}
		System.out.println("");
		
		System.out.println("resource_all");
		int[] b = DevController.getEntireDevTable();
		for(int i =0;i<b.length;i++) {
			System.out.print(b[i]);
		}
		System.out.println("");
		
		while(true) {//power
			if(fetchflag) {
				process = ProcessMGT.getRunning();//取出running队列中的线程
				if(process != null){//判断当前队列中是否有进程
					runflag = true;
				}
				else {
					runflag = false;
				}
			}
			
			if(runflag) {
				fetchflag = false;
				System.out.println("pid:"+process.getPid()+" pc:"+process.getPC());
				//取指令
				String instruction = Memory.getIns(process.getActivemm(), process.getPC());
				if(!process.incPC()) {
					runflag = false;
				}
				//执行分析
				ins.ExeInstruction(instruction,process,1);
				//执行打印
				System.out.println(instruction);
			}
			//睡眠1s
			Thread.sleep(timeInterval);
			
			//中断处理
			intertype = InterService.DealInterrupt();
			if(intertype == InterType.TIMEOUT) {
				fetchflag = true;
			}
			else if(intertype == InterType.IOINTR && process.getPid()%5 == 0) {
				runflag = false;
				fetchflag = true;
			}
			else if(intertype == InterType.IOINTR && process.getPid()%5 != 0) {
				runflag = false;
				fetchflag = false;
			}
			else if(intertype == InterType.NEEDPAGE && process.getPid()%5 == 0) {
				runflag = false;
				fetchflag = false;
			}
				
			
			
		}
			
	}
	

}

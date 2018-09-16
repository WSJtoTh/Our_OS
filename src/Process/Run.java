package Process;
import pro.ins;
import memory.Memory;
import Interrupt.InterService;

public class Run {
	final static long timeInterval = 1000;
	static Thread producerthread = new ProducerThread();
	
	public static void main(String args[]) throws InterruptedException {
		boolean runflag = false;//取值标志
		Process process = null;
		producerthread.start();//开启生产者消费者模型
		
		while(true) {//power
			process = ProcessMGT.popRunning();//取出running队列中的线程
			if(process != null){//判断当前队列中是否有进程
				runflag = true;
			}
			else {
				runflag = false;
			}
			
			for(int i = 0; i < 5; i++) {
				if(runflag) {
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
				//中断处理
				InterService.DealInterrupt();
				//睡眠1s
				Thread.sleep(timeInterval);
			}
			
		}
			
	}
	

}

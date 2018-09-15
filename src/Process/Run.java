package Process;
public class Run {
	final static long timeInterval = 1000;
	
	
	public static void main(String args[]) throws InterruptedException {
		boolean lastinstruction = false;
		while(true) {//power
			Process process = new Process();
			
			//取指令
			
			//执行分析
			
			//执行打印
			
			//中断判断
			
			
			//休息一会
			Thread.sleep(timeInterval);
			if(process.getPC() % 5 == 0 || lastinstruction == true) {
				Thread.sleep(timeInterval);
				//中断判断
			}
			
		}
			
	}
	

}

package Process;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import memory.Memory;

public class ProducerThread extends Thread{
	//存放文件名
	private static ArrayList<String> filelist = new ArrayList<String>();
	private static String path = "txt";
	private final static int timeinterval = 1000;
	
	public void init() {
		//读取文件夹中的文件名
		File file = new File(path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	              //System.out.println("文     件：" + tempList[i]);
	            filelist.add(tempList[i].toString());  
	        }
	    }
	    
	    printFile();
	}
	
	public File choice_a_file() {
		int len = filelist.size();
		Random rand = new Random();
		//获得随机数
		int index = rand.nextInt(len);
		System.out.println(filelist.get(index));
		//返回选取的文件
		File file = new File(filelist.get(index));
		return file;
	}
	
	public void printFile() {
		System.out.println("装入文件名:");
		for(String s:filelist) {
			System.out.println(s);
		}
	}
	
	public void p_wait(File file) {
		boolean flag = true;
		while(flag) {
			//先判断是否可用
			Memory.getTxt(file);
			//若可用，跳出循环
			if(Memory.isFree()) {//内存空闲
				//创建进程pcb
				Process p = ProcessMGT.createProcess();
				//分配资源
				Memory.InitMemory(file,p.getPid());
				//设置物理页号, 指令条数
				Memory.setPCB(p);
				//加入ready队列
				ProcessMGT.addProcess(p);
				flag = false;
			}
			
			//若不可用，继续循环
			try {
				Thread.sleep(timeinterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void run() {
		init();
		while(true) {//电源
			File file = choice_a_file();
			p_wait(file);
		}
	}
	
	
}


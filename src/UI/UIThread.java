package UI;

import timer.timer;

public class UIThread extends Thread{
	final static int timeinterval = 800;
	@Override
	public void run() {
		
		while(true) {//power 是否修改dsdas
			

			MainController.setMessage();
			//System.out.println("更新了！！！！！！！！！！！！！！");
			MainController.setTimer();	
			MainController.reloadTable1();
			MainController.reloadTable2();

			try {
				Thread.sleep(timeinterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

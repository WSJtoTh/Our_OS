package UI;

public class UIThread extends Thread{
	final static int timeinterval = 100;
	@Override
	public void run() {
		
		while(true) {//power 是否修改dsdas
			
			MainController.reloadjTable2();
			try {
				Thread.sleep(timeinterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

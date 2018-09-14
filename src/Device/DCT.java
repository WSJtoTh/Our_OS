/**
 * 
 */
package Device;

import java.util.concurrent.CountDownLatch;

/**
 * @author 45044
 *
 */
public class DCT {
	private final int MAX_SIZE = 10;
	private DevType devType;
	private int devCount;
	private int[] deviceArray;	//存储当前种类设备的ID号
	public DCT(DevType devType) {
		// TODO Auto-generated constructor stub
		this.devType = devType;
		this.devCount = 0;
		this.deviceArray = new int[MAX_SIZE];
	}
	
	public void addDev(int devID) {	//挂载设备
		this.deviceArray[devCount] = devID;
		this.devCount++;
	}
	
	public int getFreeDevice() {	//获取空闲设备的ID号
		
	}
}

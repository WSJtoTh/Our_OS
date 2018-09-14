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
	private int[] deviceArray;	//�洢��ǰ�����豸��ID��
	public DCT(DevType devType) {
		// TODO Auto-generated constructor stub
		this.devType = devType;
		this.devCount = 0;
		this.deviceArray = new int[MAX_SIZE];
	}
	
	public void addDev(int devID) {	//�����豸
		this.deviceArray[devCount] = devID;
		this.devCount++;
	}
	
	public int getFreeDevice() {	//��ȡ�����豸��ID��
		
	}
}

/**
 * 
 */
package Device;

/**
 * @author 45044
 *
 */
public class SDT {
	//private DevType devType;
	//private int devID;
	private final int MAX_SIZE = 20;
	private int devCount = 0;	//系统当前的设备总数<=20
	private DevTb[]	sysDevTable;
	
	public SDT() {//初始化一个系统设备表
		// TODO Auto-generated constructor stub
		this.sysDevTable = new DevTb[MAX_SIZE];
	}
	
	public void addDeviceIntoSystem(DevTb devTb) {
		this.sysDevTable[devCount] = devTb;
		this.devCount++;
	}
	
	public  Boolean getFreeDev(DevType devType, int devID) {
		for(int i = 0;i<this.devCount;i++) {
			if(sysDevTable[i].getDevID() == devID && sysDevTable[i].getDevState() == DevState.FREE ) {
				sysDevTable[i].setDevSate(DevState.BUSY);
				return true;
			}//如果找到了符合条件的设备就分配给它
		}
		return false;
	}
	
	
}

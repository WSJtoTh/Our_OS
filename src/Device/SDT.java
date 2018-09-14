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
	private int devCount = 0;	//ϵͳ��ǰ���豸����<=20
	private DevTb[]	sysDevTable;
	
	public SDT() {//��ʼ��һ��ϵͳ�豸��
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
			}//����ҵ��˷����������豸�ͷ������
		}
		return false;
	}
	
	
}

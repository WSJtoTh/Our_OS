package Process;
public class SystemResources {
	private static int[] Resource_max = new int[8];
	private static int[] Resource_remain= new int[8];
	
	//设置设备的最大数组
	public static void setDevmax(int[] max) {
		for(int i = 3,j = 0;j < max.length;i++,j++) {
			Resource_max[i] = max[j];
		}
	}
	//设置设备的余留数组
	public static void setDevremain(int[] remain) {
		for(int i = 3,j = 0;j < remain.length;i++,j++) {
			Resource_max[i] = remain[j];
		}
	}
	
	//获得系统资源最大值数组
	public int[] getResourcemax(){
		return Resource_max;
	}
	
	//初始时，设置最大资源和余留资源
	public void setResource(int[] max) {
		Resource_max = max;
		Resource_remain = max;
	}
	
	//获得系统余下资源数组
	public int[] getResource_remain() {
		return Resource_remain;
	}
	
	//某类资源减少一定数量 
	public static int reqResource(int[] max_list,int[] need_list) {
		for(int i = 0;i < Resource_max.length;i++) {
			if(Resource_max[i] < max_list[i]) {
				return -1; //代表需要杀死这个进程
			}
		}
		for(int i = 0;i < Resource_remain.length;i++) {
			if(Resource_remain[i] < need_list[i]) {
				return 0;//代表资源不够
			}
		}
		return 1;//代表资源充足
	}
	
	
	public static void decResource(int[] need_list) {
		for(int i = 0;i < Resource_remain.length;i++) {
			Resource_remain[i] -= need_list[i];
		}
	}
	
	//某类资源增加一定数量
	public static void addResource(int[] give_list) {
		for(int i = 0;i < Resource_remain.length;i++) {
			Resource_remain[i] += give_list[i];
		}
	}
	
	public static int convertResourceType(ResourceType source) {
		int res = source.ordinal();
		return res;
	}
	
	
	
}

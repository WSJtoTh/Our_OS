package pro;
import Device.*;
import Interrupt.*;


public class ins {

	private static InterHandler intrHandler=new InterHandler();
	
	private int[] ExeInstruction(String ins,Process PCB) {//传进来Process PCB
		String []content=ins.split(" ");
		int[] resource= {0};
		for(int i=0;i<content.length;i++)
		{
			System.out.print(content[i]+" ");
		}
		switch(content[0]) {
		case "C"://计算
			int pagenumber=Integer.parseInt(content[1]);//逻辑页号
			int j=1;
			while(j<content.length)
			{	
				ResourceType reTypeC=ResourceType.valueOf(content[j]);//所需资源
				j=j+1;
				int ResourceNumberC=Integer.parseInt(content[j]);
				switch(reTypeC) {//生成资源数组
				case E:
					resource[0]=ResourceNumberC;
					break;
				case F:
					resource[1]=ResourceNumberC;
					break;
				case G:
					resource[2]=ResourceNumberC;
					break;
				case K:
					resource[3]=ResourceNumberC;
					break;
				case P:
					resource[4]=ResourceNumberC;
					break;
				case R:
					resource[5]=ResourceNumberC;
					break;
				case W:
					resource[6]=ResourceNumberC;
					break;
				case M:
					resource[7]=ResourceNumberC;
					break;
				case A:
					resource[8]=ResourceNumberC;
					break;	
					
				}
				j=j+1;
			}
			//使用函数
			break;		
		case "K"://键盘输入
			
			SignalType rOrwK=SignalType.READ;
			//使用函数
			//addResource(DevType.KEYBOARD);
			intrHandler.ioINTR(InterType.IOINTR,PCB,rOrwK,DevType.KEYBOARD);
			
			break;
		case "P"://打印
			int FileSizeP=Integer.parseInt(content[1]);
			//addResource(DevType.PRINTER);
			SignalType rOrwP=SignalType.WRITE;
			intrHandler.ioINTR(InterType.IOINTR,PCB,rOrwP,DevType.PRINTER);
			//使用函数
			break;
		case "R"://读磁盘
			int FileSizeR=Integer.parseInt(content[1]);
			
			//addResource(DevType.DISK);
			SignalType rOrwR=SignalType.READ;
			intrHandler.ioINTR(InterType.IOINTR,PCB,rOrwR,DevType.DISK);
			break;
			
		case "W"://写磁盘
			int FileSizeW=Integer.parseInt(content[1]);
			//addResource(DevType.DISK);
			SignalType rOrwW=SignalType.WRITE;
			intrHandler.ioINTR(InterType.IOINTR,PCB,rOrwW,DevType.DISK);
			break;
			
		case "M"://麦克风输入
			
			//addResource(DevType.MICROPHONE);
			SignalType rOrwM=SignalType.READ;
			intrHandler.ioINTR(InterType.IOINTR,PCB,rOrwM,DevType.MICROPHONE);
			break;
			
		case "A"://音响输出
			int FileSizeA=Integer.parseInt(content[1]);
			//addResource(DevType.DISK);
			SignalType rOrwA=SignalType.WRITE;
			intrHandler.ioINTR(InterType.IOINTR,PCB,rOrwA,DevType.AUDIO);
			break;
			
		case "Q"://结束运行
			//调函数
			break;
			
		}
		
		return resource;
	}
	
	
}

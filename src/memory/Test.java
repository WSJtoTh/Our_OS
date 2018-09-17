package memory;

import java.io.File;
import java.util.ArrayList;

public class Test {
	public static void main(String [] args) {
		File file = new File("D:\\操作系统课程设计\\A.txt");
		Memory.InitPage();
		Memory.getTxt(file);
		if(Memory.isFree()) {
			Memory.InitMemory(file, 1);
			for(int i=0;i<Memory.pages.length;i++) {
				if(Memory.pages[i].getState()!=0)
					System.out.println("进程"+Memory.pages[i].getPid()+"\t"+"虚拟页面"+Memory.pages[i].getVirPage()+"\t"+"页面状态："+Memory.pages[i].getState()+"内存页面"+i);
			}
		}
		System.out.println("代码内存页："+Memory.insPage);
		System.out.println(Memory.getPageUseRate());
		/*for(int i=0;i<Memory.getSourceList().length;i++)
		System.out.println(Memory.getSourceList()[i]);*/
		//System.out.println(Memory.getIns(0, 0));
		Memory.getTxt(file);
		if(Memory.isFree()) {
			Memory.InitMemory(file, 2);
			for(int i=0;i<Memory.pages.length;i++) {
				if(Memory.pages[i].getState()!=0)
					System.out.println("进程"+Memory.pages[i].getPid()+"\t"+"虚拟页面"+Memory.pages[i].getVirPage()+"\t"+"页面状态："+Memory.pages[i].getState()+"内存页面"+i);
			}
		}
		System.out.println("代码内存页："+Memory.insPage);
		System.out.println(Memory.getPageUseRate());
		System.out.println(Memory.seekPage(3, 2));
		Memory.replacePage(2, 3);
		for(int i=0;i<Memory.pages.length;i++) {
			System.out.println("进程"+Memory.pages[i].getPid()+"\t"+"虚拟页面"+Memory.pages[i].getVirPage()+"\t"+"页面状态："+Memory.pages[i].getState()+"内存页面"+i);
		}
		Memory.replacePage(2, 4);
		for(int i=0;i<Memory.pages.length;i++) {
			System.out.println("进程"+Memory.pages[i].getPid()+"\t"+"虚拟页面"+Memory.pages[i].getVirPage()+"\t"+"页面状态："+Memory.pages[i].getState()+"内存页面"+i);
		}
		//System.out.println(Memory.pages[4].insList.get(0));
		/*for(int i=0;i<Memory.insNum;i++) {
			System.out.println(Memory.pages[4].insList);
		}*/
		/*System.out.println(Memory.getIns(0, 3));
		System.out.println(Memory.getIns(0, 4));
		System.out.println(Memory.getIns(4, 0));
		System.out.println(Memory.getIns(4, 1));*/
		//Memory.getSourceList()
		/*Memory.releasePro(1);
		for(int i=0;i<Memory.pages.length;i++) {
			System.out.println("进程"+Memory.pages[i].getPid()+"\t"+"虚拟页面"+Memory.pages[i].getVirPage()+"\t"+"页面状态："+Memory.pages[i].getState()+"内存页面"+i);
		}*/
	}
}

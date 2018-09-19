package UI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
	 import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
	 import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import javafx.scene.control.TableColumn; 
	
	 public class MainController extends JFrame {
	     
		 public JFrame frame;
		 
		 public JLabel systemtimel;	//ϵͳʱ��
		 public JTextField systemtimet;//
		 public JLabel timeslicel;	//ʱ��Ƭ
		 public JPasswordField timeslicet;
		 public JLabel timerl;//��ʱ��
		 public  JPasswordField timert;
		 public JButton loginButton;
		 
		 public JLabel processStatusl;//����ִ�����
		 public JTable table0;
		 public  JScrollPane scrollPane0;
		 
		 public  JLabel promptl;//��ʾ��
		 public JPasswordField prompt;
		 
		 public  JLabel addDevicel;//����豸
		 public	JComboBox addDevicet;
		 
		 public  JLabel Devicenamel;//�豸�Լ�ռ�õĽ���
		 public JLabel ocupyProcessl;
		 public JTable table1;
		 public JScrollPane scrollPane1;
		 public DefaultTableCellRenderer tcr1;
		 
		 
		 public JLabel Deviceidl;//����ID��״̬
		 public  JLabel statusl;
		 public JTable table2;
		 public JScrollPane scrollPane2;
		 public DefaultTableCellRenderer tcr2;
		 
		 public static MainControllerUI UI;
		 
		 private static Our_os os;
		 public MainController panel1 = new MainController(os); 
		 
		 
		 public MainController(Our_OS) {
				this.os=os;
			//	initialize();
			}
		 
		public void newMainController(Our_OS os)
		{	
			UI=new MainControllerUI(os);
			UI.start();
		}
		 
		 
	     public static void main(String[] args) {    
	         // ���� JFrame ʵ��
	         JFrame frame = new JFrame("ģ�����ϵͳ");
	         // Setting the width and height of frame
	         frame.setSize(970, 800);
	         frame.setLocationRelativeTo(null);//���������ʾ
	         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	         /* ������壬��������� HTML �� div ��ǩ
	          * ���ǿ��Դ��������岢�� JFrame ��ָ��λ��
	          * ��������ǿ�������ı��ֶΣ���ť�����������
	          */
	         JPanel panel1 = new JPanel(); // ������
	         
	         frame.add(panel1);//  * �����û�����ķ����������������
	          
	         MainController(panel1);

	         // ���ý���ɼ�
	         frame.setVisible(true);
	     }

	     private static void initialize()
	     {

	         /* ���ֲ���������߲���������
	          * ������ò���Ϊ null
	          */
	    	 os.getContentPane().setForeground(Color.BLACK);
	        // Label��ɫ��������        
	         Font font = new Font("����",Font.BOLD, 18);//���塢�Ӵ֡�18����
	         
	         Font font1 = new Font("����",Font.BOLD, 24);//���塢�Ӵ֡�24����	         
	         
	         // ���� JLabel
	         JLabel systemtimel = new JLabel("ϵͳʱ��:");
	         systemtimel.setFont(font); 
	         /* ������������������λ�á�
	          * setBounds(x, y, width, height)
	          * x �� y ָ�����Ͻǵ���λ�ã��� width �� height ָ���µĴ�С��
	          */
	         systemtimel.setBounds(10,20,100,40);
	         os.add(systemtimel);

	         /* 
	          * �����ı��������û�����
	          */
	         JTextField systemtimet = new JTextField(40);
	         systemtimet.setBounds(100,20,100,40);
	         os.add(systemtimet);

	         // ����������ı���
	         JLabel timeslicel =new JLabel("ʱ��Ƭ:");
	         timeslicel.setFont(font);
	         timeslicel.setBounds(270,20,100,40);
	         os.add(timeslicel);

	         /* 
	          *�����������������ı���
	          * �����������Ϣ���Ե�Ŵ��棬���ڰ�������İ�ȫ��
	          */
	         JPasswordField timeslicet = new JPasswordField(40);
	         timeslicet.setBounds(350,20,100,40);
	         os.add(timeslicet);

	         
	         // ����������ı���
	         JLabel timerl = new JLabel("��ʱ��:");
	         timerl.setFont(font);
	         timerl.setBounds(520,20,100,40);
	         os.add(timerl);

	         /* 
	          *�����������������ı���
	          * �����������Ϣ���Ե�Ŵ��棬���ڰ�������İ�ȫ��
	          */
	         JPasswordField timert = new JPasswordField(40);
	         timert.setBounds(590,20,100,40);
	        os.add(timert);
	         
	         // ������¼��ť
	         JButton loginButton = new JButton("login");
	         loginButton.setFont(font);
	         loginButton.setBounds(800, 20, 100, 40);
	         os.add(loginButton);
	          
	        ////////////////////�ڴ�ʹ����
	         
	         
	         
	         
	         
	         
	         
	         
	         ///////////////////////////����ִ�����
	         JLabel processStatusl = new JLabel("�豸����");
	         processStatusl.setFont(font1);
	         processStatusl.setBounds(415,150,100,40);
	         os.getContentPane().add(processStatusl);	

	       
	          String[] columnNames0 = {"A"};// ��������������
	         
	         // ��������������
	         String[][] tableValues0 = {{"A1"},{"A2"},{"A3"},{"A4"},{"A5"}};
	         // ����ָ�����������ݵı��
	         JTable table0 = new JTable(tableValues0,columnNames0);
	         // ������ʾ���Ĺ������
	         JScrollPane scrollPane0 = new JScrollPane(table0);
	         // �����������ӵ��߽粼�ֵ��м�
	        // getContentPane().add(scrollPane, BorderLayout.CENTER);
	         table0.setFont(font1);
	         table0.setRowHeight(30);//���ñ����и�
	         //���ݾ���
	         DefaultTableCellRenderer tcr0= new DefaultTableCellRenderer();
	         tcr0.setHorizontalAlignment(JLabel.CENTER);
	         table0.setDefaultRenderer(Object.class,tcr0);
	        // table.setBackground(null);
	         table0.setBounds(320, 190, 300, 200);
	         os.getContentPane().add(table0);
	         
	         
	         
	         
	         //////////////////////��ʾ��
	         JLabel promptl = new JLabel("��ʾ��");
	         promptl.setFont(font1);
	         promptl.setBounds(750,150,100,40);
	         os.add(promptl);	        
	         
	         JPasswordField prompt = new JPasswordField(40);
	         prompt.setBounds(640, 190, 300, 200);
	         os.getContentPane().add(prompt);
	         
	         
	         
	         
	        ////////////////////// //����豸
	         JLabel addDevicel = new JLabel("����豸");
	         addDevicel.setFont(font1);
	         addDevicel.setBounds(100,470,100,40);
	         os.add(addDevicel);	         
	         
	         JComboBox addDevicet= new JComboBox();
	         addDevicet.setFont(font);
	         addDevicet.setBounds(20, 520, 280, 50);
	         os.getContentPane().add(addDevicet);
	         addDevicet.addItem(null);
	         addDevicet.addItem("PRINTER");
	         addDevicet.addItem("KEYBOARD");
	         addDevicet.addItem("DISK");
	         addDevicet.addItem("MICROPHONE");
	         addDevicet.addItem("AUDIO");
	         
	         
	         
	         ////////////////////////////�豸�Լ�ռ�õĽ���
	         JLabel Devicenamel = new JLabel("�豸����");
	         Devicenamel.setFont(font1);
	         Devicenamel.setBounds(340,470,100,40);
	         os.add(Devicenamel);	
	         
	         JLabel ocupyProcessl = new JLabel("ռ�õĽ���");
	         ocupyProcessl.setFont(font1);
	         ocupyProcessl.setBounds(480,470,140,40);
	         os.getContentPane().add(ocupyProcessl);	
	         
	       
	          String[] columnNames1 = {"A","B"};// ��������������
	         
	         
	         String[][] tableValues1 = {{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}};// ��������������
	         
	         JTable table1 = new JTable(tableValues1,columnNames1);// ����ָ�����������ݵı��
	         
	         JScrollPane scrollPane1 = new JScrollPane(table1);// ������ʾ���Ĺ������
	         // �����������ӵ��߽粼�ֵ��м�
	        // getContentPane().add(scrollPane, BorderLayout.CENTER);
	         table1.setFont(font1);
	         table1.setRowHeight(30);//���ñ����и�
	        
	         DefaultTableCellRenderer tcr1= new DefaultTableCellRenderer(); //���ݾ���
	         tcr1.setHorizontalAlignment(JLabel.CENTER);
	         table1.setDefaultRenderer(Object.class,tcr1);
	        // table.setBackground(null);
	         table1.setBounds(320, 520, 300, 200);
	         os.getContentPane().add(table1);
	         

	         /////////////////////// //����ID��״̬
	         JLabel Deviceidl = new JLabel("����ID");
	         Deviceidl.setFont(font1);
	         Deviceidl.setBounds(680,470,100,40);
	         os.add(Deviceidl);	
	         
	         JLabel statusl = new JLabel("״̬");
	         statusl.setFont(font1);
	         statusl.setBounds(840,470,140,40);
	         os.getContentPane().add(statusl);	
	          
	       
	          String[] columnNames2 = {"A","B"};// ��������������
	         
	         String[][] tableValues2 = {{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}}; // ��������������
	         // ����ָ�����������ݵı��
	         JTable table2 = new JTable(tableValues2,columnNames2);
	         // ������ʾ���Ĺ������
	         JScrollPane scrollPane2 = new JScrollPane(table2);
	         // �����������ӵ��߽粼�ֵ��м�
	        // getContentPane().add(scrollPane, BorderLayout.CENTER);
	         table2.setFont(font1);
	         table2.setRowHeight(30);//���ñ����и�
	         //���ݾ���
	         DefaultTableCellRenderer tcr2= new DefaultTableCellRenderer();
	         tcr2.setHorizontalAlignment(JLabel.CENTER);
	         table2.setDefaultRenderer(Object.class,tcr2);
	        // table.setBackground(null);
	         table2.setBounds(640, 520, 300, 200);
	         os.getContentPane().add(table2);
	         
	         
	         os.setSize(970, 800);
	         os.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         client.getContentPane().setLayout(null);

	         // ���ý���ɼ�
	         frame.setVisible(true);
	     }
	     

	 }
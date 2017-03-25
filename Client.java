import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
class Note extends JFrame
{
	String msg=null;
	public Note(String title,String msg)
	{
		super(title);
		this.msg=msg;
		Container c2=getContentPane();
		c2.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		UI5();
	}
	void UI5()
	{
		JLabel l=new JLabel(msg,JLabel.RIGHT);
		add(l);
	}
}

class Sendmessage extends JFrame implements ActionListener
{
	JButton btsend2;
	Choice chclientlist2;
	JTextArea txtmessage;
	// DataInputStream instream3;
	DataOutputStream outstream3;
	String cliname=null;
	boolean sstatus=false;
	public Sendmessage(String frameTitle2)
	{
		super(frameTitle2);
		Container c2=getContentPane();
		c2.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		// System.out.println("error point4");
	}
	public void UI2(String cl[],Socket clientsocket1,String clientname)
	{
		cliname=clientname;
		JLabel mlbl=new JLabel("Select Client:",Label.RIGHT);
		JLabel msglbl=new JLabel("Message:",Label.RIGHT);
		btsend2=new JButton("Send");
		txtmessage=new JTextArea(5,30);
		chclientlist2=new Choice();
		chclientlist2.add("Select Client");
		//System.out.println("error point5");
		for(int k=0;k<cl.length;k++)
		{
		   if(cl[k].compareTo(cliname)!=0)
				chclientlist2.add(cl[k]);
		}
		add(mlbl);
		add(chclientlist2);
		add(msglbl);
		add(txtmessage);
		add(btsend2);
		//System.out.println("error point6");
		try
		{
			//instream3=new DataInputStream(clientsocket1.getInputStream());
			outstream3=new DataOutputStream(clientsocket1.getOutputStream());
			//System.out.println("error point7");
		}
		catch(IOException q)
		{
		}
		//System.out.println("error point10");
		btsend2.addActionListener(this);
		addWindowListener(new WindowAdapter() /* ananymous inner class */
		{
			public void windowClosing(WindowEvent we)
			{ 
				if(sstatus==true)
				{
					Note nnmopb=new Note("Notification","Message Sent successfully!!!!");
						nnmopb.setVisible(true);
						nnmopb.setSize(50,100);
						setVisible(true);
						
				}
				else
				{
					try
					{
					outstream3.writeUTF("exitcommand");
					}
					catch(IOException io){}
					setVisible(false);
				}
			}
		}); 
		
	} //End of UI2
	public void actionPerformed(ActionEvent aae)
	{
		String m=null,n=null;
		//System.out.println("error point8");
		n=chclientlist2.getSelectedItem(); //n is used to hold destination clientname
		m=txtmessage.getText(); //m is used to hold message that is neede to be sent
		try
		{
			outstream3.writeUTF(cliname); // cliname is name of sender
			outstream3.writeUTF(n);
			outstream3.writeUTF(m);
			//System.out.println("error point9");
			sstatus=true;
			setVisible(false);
		}
		catch(IOException q)
		{
		}
	} //End of action performed
} //End of class send message


class Sendfile extends JFrame implements ActionListener
{
    JButton btsend1,btselectfile;
	Choice chclientlist1;
	FileDialog fdtemp;
	//Client obclient;
	String cliname1=null;
	// DataInputStream instream5;
	DataOutputStream outstream5;
	FileInputStream fis;
	BufferedReader fbr=null;
	String wholefile=null;
	Socket clientsocket2=null;
	boolean fstatus=false;
	String sfname=null;
	public Sendfile(String frameTitle1)
	{
		
		super(frameTitle1);
		Container c1=getContentPane();
		c1.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	}
	public void UI1(String cl1[],Socket clientsocket3,String clientname1,FileDialog fd)
	{
		cliname1=clientname1;
		fdtemp=fd;
		clientsocket2=clientsocket3;
		JLabel flbl=new JLabel("Select Client:",Label.RIGHT);
		btsend1=new JButton("Send");
		btselectfile=new JButton("Select File");
		chclientlist1=new Choice();
		chclientlist1.add("Select Client");
		for(int k=0;k<cl1.length;k++)
		{
		   if(cl1[k].compareTo(cliname1)!=0)
				chclientlist1.add(cl1[k]);
		}
		add(flbl);
		add(chclientlist1);
		add(btselectfile);
		add(btsend1);
		try
		{
			//instream5=new DataInputStream(clientsocket2.getInputStream());
			outstream5=new DataOutputStream(clientsocket2.getOutputStream());
			
		}
		catch(IOException q)
		{
		}
		btsend1.addActionListener(this);
		btselectfile.addActionListener(this);	
		addWindowListener(new WindowAdapter() /* ananymous inner class */
		{
			public void windowClosing(WindowEvent we)
			{ 
				if(fstatus==true)
				{
					Note nnfopb=new Note("Notification","File sent successfully!!!!");
						nnfopb.setVisible(true);
						nnfopb.setSize(50,200);
						setVisible(true);
				}
				else
				{
					try
					{
					outstream5.writeUTF("exitcommand");
					}
					catch(IOException ioe){}
					setVisible(false);
				}
			}
		}); 
		
	}
	public void actionPerformed(ActionEvent aee)
	{
	    if(aee.getSource()==btselectfile)
		{
			//fdtemp.setSize(500,500); 
			fdtemp.setVisible(true);
		    String b=fdtemp.getDirectory()+fdtemp.getFile();
			sfname=fdtemp.getFile();
			try
			{
			try{
			FileReader fr=new FileReader(b);
				 fbr=new BufferedReader(fr);
				 String strfile;
				 wholefile=null;
		while((strfile=fbr.readLine())!=null)
		{
			wholefile=wholefile+strfile+"\n";
		}
		
		fr.close();
		}
		catch(FileNotFoundException fnf){}
		}
		catch(IOException iooo){}
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<File contents>>>>>>>>>>>>>");
		System.out.println(wholefile);
		System.out.println("------------------------------------");
		
			} //End of if btselectfile
		if(aee.getSource()==btsend1)
		{
			ObjectOutputStream oos=null;
			try
			{
			 //System.out.println("file Point 2");
			outstream5.writeUTF(cliname1); // sender name
			String a=chclientlist1.getSelectedItem();
			outstream5.writeUTF(a); //destination
			
			outstream5.writeUTF(sfname);
			
			//System.out.println("file Point 5");
			outstream5.writeUTF(wholefile);
			//outstream5.flush();
			//System.out.println("file Point 6");
			fstatus=true;
			setVisible(false);
			}
			catch(IOException ie){}
		
		}
	} //End of action performed
} //End of class send file


class Receivedmessage extends JFrame 
{
	String mmsg=null;
	public Receivedmessage(String frameTitle3,String mmsg)
	{
		super("Received from "+frameTitle3);
		this.mmsg=mmsg;
		Container c3=getContentPane();
		//c3.setLayout(new FlowLayout(FlowLayout.CENTER,20,100));
		c3.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		UII();
		
	}
	public void UII()
	{
		JLabel m=new JLabel(mmsg,Label.RIGHT);
		add(m);
	}
}

class ReceivedFile extends JFrame implements ActionListener
{
	//Object o; //small o(character)
	String o=null;
	FileDialog fd;
	String rfname=null;
	JButton save,cncl;
	public ReceivedFile(String title,String o,String rfname)
	{
		super("File Received From "+title);
		this.o=o;
		this.rfname=rfname;
		Container c3=getContentPane();
		c3.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		//UIE();
	}
	public void UIE(FileDialog f)
	{
		fd=f;
		JLabel l=new JLabel("File not saved.Save?",Label.RIGHT);
		save=new JButton("Save");
		cncl=new JButton("No");
		add(l);
		add(save);
		add(cncl);
		save.addActionListener(this);
		cncl.addActionListener(this);
	}
	public void actionPerformed(ActionEvent aevnt)
	{
		if(aevnt.getSource()==save)
		{
			
			fd.setFile(rfname);
			fd.setVisible(true);
			//fd.readObject(o);
			
			String place=fd.getDirectory()+rfname;
			try
			{
			FileWriter fw=new FileWriter(place);
			fw.write(o);
			fw.close();
			}
			catch(IOException io){}
			System.out.println(place);
			//fd.open();
			setVisible(false);
		}
		else
		{
			o=null;
			setVisible(false);
			
		}	
	}
}
		
public class Client extends JFrame implements ActionListener
{
	JButton btfile,btmsg,btconnect,btdisconnect;
	JTextField txtserverport,txtclientname;
	Socket clientsocket=null;
	DataInputStream instream=null;
	DataOutputStream outstream=null;
	String clientname;
	int serverport;
	String sender;
	String ms;
	Thread t;
	Dimension dd=null;
	FileDialog fd;
	String clientlist[];
	String clientlist1[];
	boolean cstatus=false;
	String ss="Local Messaging System";
	Thread tt;
	Label ll;
	Color cll=null;
	int aa=100,bb=180,cc=230;
	public Client(String frameTitle)
	{
		super(frameTitle);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		hi();
		UI();
	}
	 void hi()
   { 
   //System.out.println("IN hi");
   Container c=getContentPane();
   Rectangle bounds=this.getBounds();
   dd=this.getSize();
   //System.out.println(dd);
   c.setFont(new Font("Ariel",50,50));
   //c.setLayout(new GridLayout(1,1));
  Graphics g=getGraphics();
    double sd=c.getWidth();
	//FontMetrics fm=g.getFontMetrics();
	
	
	//Dimension windowSize = frame.getContentPane().getSize();
	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//c.setBackground("back.jpg");
	
	int d=screenSize.width;
	int lw=(screenSize.width/2)-11;
	int rw=(screenSize.width/2)+11;
	int x=(screenSize.width-22)/2;
	//System.out.println(""+x+"  "+d+" "+sd+" :::"+bounds.width);
	//System.out.println(""+screenSize+" "+lw+" "+rw);
   setForeground(new Color(100,200,250));
   c.setLayout(null);
   ll=new Label("",Label.CENTER);
      ll.setBounds(50,20,500,90); // hrsntla dstnce,vrtcl dstnce,length of component, height of component
   //l.setVerticalAlignment(LEADING);
   add(ll);
   JLabel image=new JLabel();
	image.setIcon(new ImageIcon("./back.jpg"));
	image.setBounds(0,0,screenSize.width,screenSize.height);
	
	new Thread(new printt()).start();
	
   }
   class printt implements Runnable
   {
       public void run()
	   {
			while(true)
			{
			
			try
			{
			tt.sleep(100);
			}
			catch(InterruptedException ie){}
			if(aa>225)
			{
				aa=0;
			}
			if(bb>225)
			{
				bb=0;
			}
			if(cc>225)
			{
				cc=0;
			}
			
			aa=aa+20;
			bb=bb+20;
			cc=cc+20;
			cll=new Color(aa,bb,cc);
			ll.setForeground(cll);
			ll.setText(ss);
			repaint();
			//System.out.println(""+mm+mf);
			//s=l.getText();
	      }
		}
    }
	public void UI()
	{
	    Container c=getContentPane();
		
		c.setLayout(null);
		c.setFont(new Font("Ariel",40,40));
		JLabel portlbl=new JLabel("Server Port:",Label.RIGHT);
		JLabel namelbl=new JLabel("Client Name:",Label.RIGHT);
		portlbl.setFont(new Font("Ariel",20,20));
		portlbl.setForeground(new Color(255,0,0));
		namelbl.setForeground(new Color(255,0,0));
		namelbl.setFont(new Font("Ariel",20,20));
        btconnect=new JButton("Connect");
		btdisconnect=new JButton("Disconnect");
		btfile=new JButton("Send File");
		btmsg=new JButton("Send Message");
		txtserverport=new JTextField(4);
		txtclientname=new JTextField(10);
		
		add(portlbl);
		portlbl.setBounds(170,145,120,30);
		
		add(txtserverport);
		txtserverport.setBounds(284,150,100,25);
		
		add(namelbl);
		namelbl.setBounds(170,190,120,30);
		
		add(txtclientname);
		txtclientname.setBounds(284,192,100,25);
		
		add(btconnect);
		btconnect.setBounds(120,250,100,30);
		
		//add(btdisconnect);
		btdisconnect.setBounds(120,250,100,30);
		
		add(btfile);
		btfile.setBounds(240,250,100,30);
		add(btmsg);
		btmsg.setBounds(360,250,120,30);
		btconnect.addActionListener(this);
		btdisconnect.addActionListener(this);
		btfile.addActionListener(this);
		btmsg.addActionListener(this);
		addWindowListener(new WindowAdapter() /* ananymous inner class */
		{
			public void windowClosing(WindowEvent we)
			{ 
				if(cstatus==true)
				{
					Note nnopb=new Note("Notification","Stop the connection before closing");
						nnopb.setVisible(true);
						nnopb.setSize(500,100);
						setVisible(true);
				}
				else
				{
					System.exit(0);
					setVisible(false);
				}
			}
		}); 
	}	
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==btdisconnect)
		{
			try
			{
				outstream.writeUTF("3");
				outstream.writeUTF(txtclientname.getText());
				clientsocket.close();
				cstatus=false;
				//System.out.println("disconnecting...");
				Note npb=new Note("Notification","Disconnected");
						npb.setVisible(true);
						npb.setSize(500,100);	
						add(btconnect);
						remove(btdisconnect);
						
						
			}
			catch(IOException e)
			{
			}
		} //End of if
		if(ae.getSource()==btconnect)
		{
			try
			{
			
				try
				{
					
					serverport=Integer.parseInt(txtserverport.getText());
					clientsocket=new Socket("localhost",serverport);
					clientname=txtclientname.getText();
					instream=new DataInputStream(clientsocket.getInputStream());
					outstream=new DataOutputStream(clientsocket.getOutputStream());
					outstream.writeUTF(clientname);
					
					//DataInputStream tinstream=new DataInputStream(clientsocket.getInputStream());
					String tt=instream.readUTF();
					if(tt.equals("done"))
					{
					remove(btconnect);
					add(btdisconnect);
					//DataOutputStream clientsocket=new DataOutputStream(clientsocket.getOutputStream());
					//System.out.println("Connected");
					cstatus=true;
					Note nopb=new Note("Notification","Connection established successfully!!!");
						nopb.setVisible(true);
						nopb.setSize(500,100);	
						t=new Thread(new waiting(clientsocket));
				t.start();
					}
					else
					{
						instream.close();
						outstream.close();
						clientsocket.close();
						Note nopb=new Note("Notification","Name alredy Found..Provide different name!!!");
						nopb.setVisible(true);
						nopb.setSize(500,100);	
					}
				}
				catch(UnknownHostException uhe)
				{
				}
				
				
				
			}
			catch(IOException e)
			{
			}
		} //End of if
		if(ae.getSource()==btfile)
		{ 
			// Sending request for client list
			
			try
			{
				try
				{
					t.sleep(100);
				}
				catch(InterruptedException ie)
				{
				}
				outstream.writeUTF("2");
			}
			catch(IOException io)
			{
			}
		} // end of if
		if(ae.getSource()==btmsg)
		{
		    // Sending request for client list
			try
			{
				try
				{
					t.sleep(100);
				}
				catch(InterruptedException ie)
				{
				}
				outstream.writeUTF("1");
			} 
			catch(IOException ez)
			{
			} 
		    
		} //End of if
	} //End of action performed	
	
	
	
	class waiting implements Runnable 
	{
		Socket s;
		public waiting(Socket s)
		{
			this.s=s;
		}
		public void run()
		{
			while(true)
			{
				try
				{
					DataInputStream instream=new DataInputStream(s.getInputStream());	
					String po=null;
					po=instream.readUTF();
					//System.out.println("thread received"+po);
					if(po.equals("11"))  // message received
					{				
						String sender=instream.readUTF();
						String ms=instream.readUTF();
						Receivedmessage pb=new Receivedmessage(sender,ms);
						pb.setVisible(true);
						pb.setSize(500,100);					
					}
					if(po.equals("11111")) // to receive file
					{
						//System.out.println("file received");
						String sender=instream.readUTF();
						//ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
						String rfname=instream.readUTF(); //file name
						String o=instream.readUTF(); // o holds content of file
						
						ReceivedFile fr=new ReceivedFile(sender,o,rfname);
						
						FileDialog fdfile=new FileDialog(fr,"Select Folder to Save",FileDialog.SAVE);
						fr.UIE(fdfile);
						fr.setVisible(true);
						fr.setSize(500,100);	
						
					}
					if(po.equals("111")) // to receive client list to send message
					{
						//System.out.println("BT message");
						//System.out.println("BT message 2");
						DataInputStream dis=new DataInputStream(clientsocket.getInputStream());
						String u=dis.readUTF();
						//System.out.println("length"+u);
						int l=Integer.parseInt(u);
						clientlist=new String[l];			
						for(int i=0;i<l;i++)
						{			
							String y=dis.readUTF();
							clientlist[i]=y;
							//System.out.println("BT message"+y);
						}
						//System.out.println("error point1");
						Sendmessage sm=new Sendmessage("Send Message");
						sm.UI2(clientlist,clientsocket,clientname);
						//System.out.println("error point2");
						sm.setVisible(true);
						sm.setSize(500,200); 
						//System.out.println("error point3");
					}
					if(po.equals("1111"))
					{	   
						DataInputStream disn=new DataInputStream(clientsocket.getInputStream());
						String p=disn.readUTF();
						int l=Integer.parseInt(p);	
						clientlist1=new String[l];
						for(int i=0;i<l;i++)
						{	 
							String q=disn.readUTF();
							clientlist1[i]=q;
						}
						Sendfile sf=new Sendfile("Send File");
						fd=new FileDialog(sf,"Select File",FileDialog.LOAD);
						sf.UI1(clientlist1,clientsocket,clientname,fd);
						sf.setVisible(true);
						sf.setSize(500,100);
					}
				}
				catch(IOException ioe)
				{
				}
			}
		}
	}	
	
	
	public static void main(String arg[])
	{
		
		Client ob=new Client("Client");
		ob.setVisible(true);
		ob.setSize(600,400);
		
	}
} //End of class Client






   

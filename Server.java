
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class Server
{

    // Need away to stop the program...
    private static boolean acceptMore = true;
	
	// database declaration
// 	Class cl;
// 	Connection cn=null;
// 	ResultSet rs;
// 	Statement st=null;
	InetAddress a;
	private static String mr=null;
	private static String des="";
	private static String sender=null;
	private static Boolean code=true; // true means message
	private static Boolean ackn=true; // used to assure that if the message that mr contains has been sent or not
	private static String fsender=null;
	private static String fdes="";
	private static String clist[]=new String[10];
	private static int ii=0;
	private static int num=0;
	private static String receivedfname=null;
	
	 String tfilestring=null;
	private static Boolean fackn=true;
	String clientname=null,clientaddress=null;
	String filestring=null;
	
    public static void main(String[] args)
	{
        Server ob=new Server(); // or it can be write as "new Server();"
    }
    public Server() //constructor
	{

        ServerSocket serverSocket = null;
        try {
		       System.out.println("Enter the Port number\n");
			   BufferedReader ob=new BufferedReader(new InputStreamReader(System.in));
			   int n=Integer.parseInt(ob.readLine());
			   if(n<1024)
			   {
				   System.out.println("Port number should be greater than 1024");
				   System.exit(1);
				   
			   }
			   
               serverSocket = new ServerSocket(n,100);
			   System.out.println("server is listining at port number "+serverSocket.getLocalPort()); 
               
			   //batabase
// 			    try
// 				{
// 					cl=Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
// 				}
// 				catch(ClassNotFoundException cnf)
// 				{
// 				 //  System.out.println("error occured1");
// 				}
					
					while(acceptMore) 
					{
						System.out.println("-------------------------------------------");
						Socket socket = serverSocket.accept();    
						a=InetAddress.getLocalHost();
						System.out.println("Connected to >> "+a);
						System.out.println("-------------------------------------------");
						DataInputStream instream=new DataInputStream(socket.getInputStream());
						clientname=instream.readUTF();
						
						DataOutputStream otstream=new DataOutputStream(socket.getOutputStream());
						//clientname=instream.readUTF();
						int tflag;
						tflag=0;
						for(int i=0;i<ii;i++)
						{
							if(clist[i].equals(clientname))
							{
									tflag=1;
									//System.out.println(clist[i]);
							}
						}
						if(tflag==1)
						{
							otstream.writeUTF("namefound");
							otstream.flush();
							instream.close();
							otstream.close();
							socket.close();
						}	
						else
							{
								otstream.writeUTF("done");
								otstream.flush();
								InetAddress adn=InetAddress.getLocalHost();
								clientaddress=adn.toString();
								System.out.println(clientname+" : "+clientaddress);
							//	rs=null;
								
								
								Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
								
								//num=clist.length;
								clist[ii]=clientname;
								ii++;
								
								new Thread(new SocketThread(socket),clientname).start();  // This is a dirty statement,which took whole 1 night to build
								new Thread(new received(socket),clientname+"1").start();
							}	
					}
				
               
			} 
			catch(IOException exp) 
			{
			        //System.out.println("error occured3");
					exp.printStackTrace();
			} 
			finally 
			{
				try 
				{
				   
				} 
				catch(Exception e) 
				{
				}
			} //end of finally
	} //end of constructer

    public class SocketThread implements Runnable // or extends Thread 
	{

        private Socket socket;
		boolean reading= true;
        public SocketThread(Socket socket) 
		{
            this.socket = socket;
			
		}
        public void run() 
		{
			
			    
				
					try
					{
						DataInputStream instream1=new DataInputStream(socket.getInputStream());
						DataOutputStream outstream1=new DataOutputStream(socket.getOutputStream());
						//static boolean reading= true;
						while(reading) // This while is because,to make server read a command from clients continuously until he exit
						{
							//System.out.println("<<<<<<<<<<<<<<<<Message Recieived>>>>>>>>>>>>>"+Thread.currentThread().getName());
								//System.out.println(mr);
							
							String msg=instream1.readUTF();
						if(msg.equals("1")) // for message
						{
							outstream1.writeUTF("111");  // identifies client that message is receiveing
 							outstream1.writeUTF(""+ii); // it sends the size for loop at client side
							
							String m1=null;
							for(int j=0;j<ii;j++)
							{
							    m1=null;
								m1=clist[j];
								
								
								outstream1.writeUTF(m1);
							}
							/*rs.close();
							st.close();
							cn.close();*/
							// to read destination client name,source clientname and message
							String sclient=instream1.readUTF();
							if((sclient.equals("exitcommand"))==false)
							{
							String cname=instream1.readUTF();
							String mesg=instream1.readUTF();
							System.out.println("Message received!!!!!From "+sclient+" \n Destined to client "+cname+" \n \n message: "+mesg);
							
							 while(ackn==false)
							 {
								
							 }
							ackn=false;
							des="";
							mr="";
							sender=sclient;  // assaign des or designation at last 
							
							mr=mesg;
							des=cname+"1";
							}
							
						}
						if(msg.equals("2")) // for file
						{
							
							outstream1.writeUTF("1111"); // iddentifies client that file receiving
							outstream1.writeUTF(""+ii); // it sends the size for loop at client side
							String m1=null;
							for(int j=0;j<ii;j++)
							{
							    m1=null;
								m1=clist[j];
								outstream1.writeUTF(m1);
							}
							
							
							DataInputStream instream222=null;
							try
							{
							instream222=new DataInputStream(socket.getInputStream());
							}
							catch(IOException ioex){}
							//System.out.println("file Point 1");
							//try
							//{
							//System.out.println("file Point 2");
							String tfsender=instream222.readUTF();
							if((tfsender.equals("exitcommand"))==false)
							{
							//System.out.println("file Point 3");
							String tfdes=instream222.readUTF();
							//System.out.println("file Point 4");
							
							
							  receivedfname=instream222.readUTF();
							 tfilestring=instream222.readUTF();
							 //System.out.println("file Point 5");
							
							//System.out.println("file Received");
							System.out.println("File received from "+tfsender+" \n destined to " +tfdes+" \n File name is : "+receivedfname);
							while(fackn==false) // false means some files are neede to be sent
							{	
							}
							fackn=false;
							fsender=tfsender;
							fdes=tfdes+"1"; // 1 because sub thread of server which pushes the message and file to client is named with 1 atlast
							filestring=null;
							filestring=tfilestring;
							}
							//}
							/*catch(ClassNotFoundException cnf)
							{ 
								System.out.println("Object class Not found"); 
								}
							*/
						}
						if(msg.equals("3"))  // 3 for disconnect
						{
						   // System.out.println("disconnection section");
							DataInputStream instream2=new DataInputStream(socket.getInputStream());
							String cname=instream2.readUTF();
							
							for(int kk=0;kk<ii;kk++)
							{
								if(clist[kk].equals(cname))
								{
									if(kk==0)
									{
										clist[kk]=clist[kk+1];
									}
									else
									{
										for(int kl=kk;kl<(ii-1);kl++)
										{
											clist[kk]=clist[kk+1];
										}
									}
									ii--;
									break;
								}
							}
							
							
						}
						
						} //End of while
						try
						{
							InetAddress ad=InetAddress.getLocalHost();
							System.out.println("Host address:"+ad);
						}
						catch(UnknownHostException e)
						{
							//System.out.println("Error occured 4");
						}
					}	
					catch(IOException ieo)
					{
					}
				
			
			}
			
		} // End of socketThread class
		class received implements Runnable
		{
			Socket ss;
			public received(Socket s)
			{
			   this.ss=s;
			 }
			 public void run()
			 {
				while(true)
				{
				if(des.equals(Thread.currentThread().getName()))
				{
						
				
					try
				   {
					DataOutputStream outstream1=new DataOutputStream(ss.getOutputStream());
				   
				   outstream1.writeUTF("11");
				   outstream1.writeUTF(sender);
				   outstream1.writeUTF(mr);
				   des="";
				   mr="";
				   ackn=true;
					}
					catch(IOException io){}
				
				/*try
				{
				Thread.sleep(300);
				}
				catch(InterruptedException i){}*/
				} //end of if (equals)
				if(fdes.equals(Thread.currentThread().getName()))
				{
					try
					{
					//System.out.println("Server is pushing file");
					DataOutputStream outstream11=new DataOutputStream(ss.getOutputStream());
					outstream11.writeUTF("11111");
				   outstream11.writeUTF(fsender);
				
				    outstream11.writeUTF(receivedfname);
				   outstream11.writeUTF(filestring);
				   //System.out.println("-----------------------------------");
				   //System.out.println(filestring);
				   }
				   catch(IOException io){}
				   fdes="";
				   fsender="";
				   receivedfname="";
				   fackn=true;
				}
				} //end of while
				}
			 }
    }  // End of server class

package globalclasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Putty {
	
	private String userHMF = System.getProperty("user.name"); //Automatically gets user from system properties (i.e. HMF05046)
	private String pl;
	
	public void createSession() throws IOException, InterruptedException{
		
		
		
		ImportPassword IP = new ImportPassword();
		IP.importPassword();
		String user = IP.getUser("Putty").toLowerCase();
		String pw = IP.getPassword("Putty");
		
//		Runtime.getRuntime().exec("C:\\Users\\hmf05046\\Desktop\\putty.exe -load \"hcaipsasapp01\" -l "+ user +" -pw " + pw);
		Runtime.getRuntime().exec("C:\\Users\\" + userHMF + "\\Java_Files\\putty.exe -load \"hcaipsasapp01\" -l "+ user +" -pw " + pw);

//		C:\path\to\putty.exe -load "hcaipsasapp01" -l [user] -pw [password] -m C:\path\to\commands.txt
		
//		putty.exe somewhere.com -l mylogin -pw mypassword
		
	}
	
		public void runFile(String FileLocation) throws IOException, InterruptedException{
		
		
				
				
		
		
		ImportPassword IP = new ImportPassword();
		IP.importPassword();
		String user = IP.getUser("Putty").toLowerCase();
		String pw = IP.getPassword("Putty");
//		System.out.println("C:\\Users\\hmf05046\\Desktop\\putty.exe -load \"hcaipsasapp01\" -l "+ user +" -pw " + pw + " -m \"" + FileLocation + "\"&");
		Runtime.getRuntime().exec("C:\\Users\\" + userHMF + "\\Java_Files\\putty.exe -load \"hcaipsasapp01\" -l "+ user +" -pw " + pw + " -t -m \"" + FileLocation );

		}
		
		
		public void jschRunCommand(String Command) throws JSchException, IOException, InterruptedException{
			
			ImportPassword IP = new ImportPassword();
			IP.importPassword();
			String user = IP.getUser("Putty").toLowerCase();
			String pw = IP.getPassword("Putty");
			
			JSch js = new JSch();
		    Session s = js.getSession(user, "hcaipsasapp01", 22);
		    s.setPassword(pw);
		    Properties config = new Properties();
		    s.setConfig(
		    	    "PreferredAuthentications", 
		    	    "publickey,keyboard-interactive,password");
		    config.put("StrictHostKeyChecking", "no");
		    s.setConfig(config);
		    s.connect();

		    Channel c = s.openChannel("exec");
		    ChannelExec ce = (ChannelExec) c;

		    ce.setCommand(Command);
		    ce.setErrStream(System.err);

		    ce.connect();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		    }

		    ce.disconnect();
		    s.disconnect();

		    System.out.println("Exit code: " + ce.getExitStatus());
			
			
		}
		
public void jschRunSASFile(String ProgramLocation) throws JSchException, IOException, InterruptedException{
	
			// "/sasdata/servicing/Remarketing/Analytics/Keaton/Weekly Pricing/Program_Files/Pricing_Release.sas"
			//"Z:\\servicing\\Remarketing\\ManualPricing\\Programs\\AutoIMSData.sas"
			//"Z:/servicing\\Remarketing\\ManualPricing\\Programs\\AutoIMSData.sas"
	
	        ProgramLocation = ProgramLocation.replace("\\", "/"); 
	
			pl =  ProgramLocation; 
							
			//changes windows path to unix path
			if(ProgramLocation.toUpperCase().substring(0,1).equals("Z")){
				pl = "/sasdata/" + ProgramLocation.substring(3,ProgramLocation.length()); 
		
			}
			System.out.println("Running SAS Program: " + "/opt/app/sas/sashome/SASFoundation/9.4/sas " + "'" + pl + "'");
			
			ImportPassword IP = new ImportPassword();
			IP.importPassword();
			String user = IP.getUser("Putty").toLowerCase();
			String pw = IP.getPassword("Putty");
			
			JSch js = new JSch();
		    Session s = js.getSession(user, "hcaipsasapp01", 22);
		    s.setPassword(pw);
		    Properties config = new Properties();
		    s.setConfig(
		    	    "PreferredAuthentications", 
		    	    "publickey,keyboard-interactive,password");
		    config.put("StrictHostKeyChecking", "no");
		    s.setConfig(config);
		    s.connect();

		    Channel c = s.openChannel("exec");
		    ChannelExec ce = (ChannelExec) c;

		    ce.setCommand("/opt/app/sas/sashome/SASFoundation/9.4/sas " + "'" + pl + "'");
		    ce.setErrStream(System.err);

		    ce.connect();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		    }

		    ce.disconnect();
		    s.disconnect();

		    System.out.println("Exit code: " + ce.getExitStatus());
			
			
		}
		

}

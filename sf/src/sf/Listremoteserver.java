package sf;

import java.io.File;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Listremoteserver {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String SFTPHOST = "localhost";
		int SFTPPORT = 22;
		String SFTPUSER = "sdevireddy";
		String SFTPPASS = "Laptop@9988";
		String SFTPWORKINGDIR = "/download";
		String SFTPPRIVATEKEY = "/pub/example";

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		try {
			JSch jsch = new JSch();
			// File privateKey = new File(SFTPPRIVATEKEY);
			/*
			 * if(privateKey.exists() && privateKey.isFile())
			 * jsch.addIdentity(SFTPPRIVATEKEY);
			 */
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
			
			for (int i = 0; i < filelist.size(); i++) {
				LsEntry entry = (LsEntry) filelist.get(i);
				if (entry.getFilename().contains("Client")) {
					System.out.println(entry.getFilename());
					channelSftp.get(SFTPWORKINGDIR+"/"+entry.getFilename(), "c:\\download\\");
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null)
				session.disconnect();
			if (channel != null)
				channel.disconnect();
		}
	}
	

}

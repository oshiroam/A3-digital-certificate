package br.com.javac.nfeapplet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class CertUtil {
	private static final int TIMEOUT_WS = 120;

	public static File get(String host, int port, boolean forceCreate, boolean cte) throws Exception {
		char[] passphrase = "changeit".toCharArray();
		String fileCacerts = cte ? Utils.getUserHome() + "/.cte-cacerts" : Utils.getUserHome() + "/.nfe-cacerts";

		File file = new File(fileCacerts);
		if (!file.exists()) {
			forceCreate = true;
		}
		
		if (forceCreate) {
		    if (!file.isFile()) {
		    	char SEP = File.separatorChar;
		    	File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
		    	file = new File(dir, fileCacerts);
		    	if (!file.isFile()) {
		    		file = new File(dir, "cacerts");
		    	}
		    }
	
		    InputStream in = new FileInputStream(file);
		    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		    ks.load(in, passphrase);
		    in.close();
		 
		    SSLContext context = SSLContext.getInstance("TLS");
		    TrustManagerFactory tmf = TrustManagerFactory.getInstance(
		    		TrustManagerFactory.getDefaultAlgorithm());
		    tmf.init(ks);
		    X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
		    SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
		    context.init(null, new TrustManager[] { tm }, null);
		    SSLSocketFactory factory = context.getSocketFactory();
		 
		    SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
		    socket.setSoTimeout(TIMEOUT_WS * 1000);
		    try {
		    	socket.startHandshake();
		    	socket.close();
		    } catch (SSLHandshakeException e) {
		    	/**
		    	 * PKIX path building failed: 
		    	 * sun.security.provider.certpath.SunCertPathBuilderException: 
		    	 * unable to find valid certification path to requested target
		    	 * N�o tratado, pois sempre ocorre essa exce��o quando o cacerts
		    	 * nao esta gerado.
		    	 */
		    } catch (SSLException e) {
		    	//FIXME Tratar exception;
		    	error("| " + e.toString());
		    }
		    
		    X509Certificate[] chain = tm.chain;
		    if (chain == null) {
		    	return null;
		    }
	
		    MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		    MessageDigest md5 = MessageDigest.getInstance("MD5");
		    for (int i = 0; i < chain.length; i++) {
		    	X509Certificate cert = chain[i];
		    	sha1.update(cert.getEncoded());
		    	md5.update(cert.getEncoded());
			    
		    	String alias = host + "-" + (i);
			    ks.setCertificateEntry(alias, cert);
		    }
	
		    File cafile = new File(fileCacerts);
		    OutputStream out = new FileOutputStream(cafile);
		    ks.store(out, passphrase);
		    out.close();
	
		    return cafile;
		}
		else {
			return file;
		}
	}

	private static class SavingTrustManager implements X509TrustManager {
	    private final X509TrustManager tm;
	    private X509Certificate[] chain;
	 
	    SavingTrustManager(X509TrustManager tm) {
	    	this.tm = tm;
	    }
	 
	    public X509Certificate[] getAcceptedIssuers() {
	    	throw new UnsupportedOperationException();
	    }
	 
	    public void checkClientTrusted(X509Certificate[] chain, String authType)
	        throws CertificateException {
	    	throw new UnsupportedOperationException();
	    }
	 
	    public void checkServerTrusted(X509Certificate[] chain, String authType)
	        throws CertificateException {
	    	this.chain = chain;
	    	tm.checkServerTrusted(chain, authType);
	    }
	}
	
	private static void error(String log) {
        System.out.println("ERROR: " + log);
    }

}

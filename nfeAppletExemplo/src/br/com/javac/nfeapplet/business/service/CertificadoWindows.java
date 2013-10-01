package br.com.javac.nfeapplet.business.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import br.com.javac.nfeapplet.entity.Certificado;
import br.com.javac.nfeapplet.util.CertUtil;
import br.com.javac.nfeapplet.util.Const;

public class CertificadoWindows {
	private KeyStore keyStore;

	/**
	 * Recupera a lista de certificados digitais instalados no Windows,
	 * com as informações do certificado, ver a classe Certificado.
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public List<Certificado> listaCertificadoDisponiveis() throws KeyStoreException, 
			NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException {
		List<Certificado> listCertificado = new ArrayList<Certificado>();

        Enumeration<String> al = getKeyStore().aliases();  
        while (al.hasMoreElements()) {  
            String alias = al.nextElement();  
            if (getKeyStore().containsAlias(alias)) {
            	X509Certificate cert = (X509Certificate) getKeyStore().getCertificate(alias);
            	if (cert != null) {
	            	Certificado certificado = new Certificado();
	            	certificado.setEmitidoPara(extractDN(cert.getSubjectDN()));
	            	certificado.setAlias(alias);
	            	certificado.setValidoDe(cert.getNotBefore());
	            	certificado.setValidoAte(cert.getNotAfter());

	            	listCertificado.add(certificado);
            	}
            }  
        }

		return listCertificado;
	}

	/**
	 * Carrega os certificados necessários para consumo dos Web Services.
	 * @param url
	 * @param senha
	 * @throws Exception
	 */
    public void loadWsCerticates(URL url, String alias, String senha) throws Exception {
        File cert = CertUtil.get(url.getHost(), Const.SSL_PORT, true, false);
        if (cert != null) {
        	char[] pin = (senha == null ? "" : senha).toCharArray();
        	SocketFactoryDinamico.load(getKeyStore(), alias, pin, 
        			cert.getAbsolutePath());
        }
    }

	/**
	 * Extrai o Common Name (CN) do certificado. O Common Name do certificado
	 * é utilizado para exibir o nome do proprietário do certificado, ou seja, 
	 * para qual pessoa ou empresa o certificado foi emitido.
	 * @param subjectDN
	 * @return
	 */
	private String extractDN(Principal subjectDN) {
		if (subjectDN != null) {
			String dn = subjectDN.toString();
			dn = dn.substring(dn.indexOf("CN=") + 3, dn.indexOf(","));
			return dn;
		}
		return null;
	}
	
	/**
	 * Cria o KeyStore de acesso ao Repositorio de Certificados do
	 * Windows.
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public KeyStore getKeyStore() throws KeyStoreException, NoSuchProviderException, 
			NoSuchAlgorithmException, CertificateException, IOException {
		if (keyStore == null) {
			keyStore = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
	        keyStore.load(null, null);
		}
		return keyStore;
	}

}

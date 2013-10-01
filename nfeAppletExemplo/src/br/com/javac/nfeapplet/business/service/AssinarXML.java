package br.com.javac.nfeapplet.business.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Assinatura dos XMLs do Projeto NF-e, CC-e e DPEC
 * Envio do Lote NF-e;
 * Cancelamento da NF-e;
 * Inutilização da NF-e;
 * Envio do Lote de Eventos - CC-e;
 * Envio do DPEC;
 * @author Maciel Gonçalves
 *
 */
public class AssinarXML {
	private static final String INFINUT = "infInut";
	private static final String INFCANC = "infCanc";
	private static final String INFDPEC = "infDPEC";
	private static final String EVENTO = "evento";
	private static final String NFE = "NFe";

	private PrivateKey privateKey;
	private KeyInfo keyInfo;
	private KeyStore keyStore;
	private String alias;
	private String senha;

	public AssinarXML(KeyStore keyStore, String alias, String senha) {
		this.keyStore = keyStore;
		this.alias = alias;
		this.senha = senha;
	}
	
	/**
	 * Assinatura do XML de Envio do DPEC.
	 * DPEC = Emissão da NF-e com Prévio Registro da DPEC no Ambiente Nacional.
	 * @param xml
	 * @param certificado
	 * @param senha
	 * @return Xml do Envio do DPEC Assinado.
	 * @throws Exception
	 */
	public String assinaEnvDPEC(String xml) throws Exception {
		return assinaCancInutDpec(xml, INFDPEC);
	}

	/**
	 * Assinatura do XML de Envio de Lote da NF-e utilizando Certificado
	 * Digital A1.
	 * @param xml
	 * @param certificado
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public String assinaEnviNFe(String xml) throws Exception {
		Document document = documentFactory(xml);
		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactory(signatureFactory);
		loadCertificates(signatureFactory);

		for (int i = 0; i < document.getDocumentElement().getElementsByTagName(NFE).getLength(); i++) {
			assinar(NFE, signatureFactory, transformList, privateKey, keyInfo, document, i);
		}

		return outputXML(document);
	}

	/**
	 * Assintaruda do XML de Eventos da NF-e utilizando Certificado
	 * Digital A1.
	 * @param xml
	 * @param certificado
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public String assinaEnvEvento(String xml) throws Exception {
		Document document = documentFactory(xml);
		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactory(signatureFactory);
		loadCertificates(signatureFactory);

		for (int i = 0; i < document.getDocumentElement().getElementsByTagName(EVENTO).getLength(); i++) {
			assinar(EVENTO, signatureFactory, transformList, privateKey, keyInfo, document, i);
		}

		return outputXML(document);
	}

	/**
	 * Assintaruda do XML de Cancelamento da NF-e utilizando Certificado
	 * Digital A1.
	 * @param xml
	 * @param certificado
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public String assinaCancNFe(String xml) throws Exception {
		return assinaCancInutDpec(xml, INFCANC);
	}

	/**
	 * Assinatura do XML de Inutilizacao de sequenciais da NF-e utilizando
	 * Certificado Digital A1.
	 * @param xml
	 * @param certificado
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public String assinaInutNFe(String xml) throws Exception {
		return assinaCancInutDpec(xml, INFINUT);
	}

	private void loadCertificates(XMLSignatureFactory signatureFactory) throws Exception {
		if (keyStore != null) {
			KeyStore.PrivateKeyEntry pkEntry = null;
			if (keyStore.isKeyEntry(alias)) {
				char[] pin = (senha == null ? "" : senha).toCharArray();
				pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
						new KeyStore.PasswordProtection(pin));
				privateKey = pkEntry.getPrivateKey();
				X509Certificate cert = (X509Certificate) pkEntry.getCertificate();

				KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
				List<X509Certificate> x509Content = new ArrayList<X509Certificate>();

				x509Content.add(cert);
				X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
				keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
			}
			else {
				throw new Exception("Alias do certificado inválido.");
			}
		}
		else {
			throw new Exception("Informações do Certificado inválidas.");
		}
	}

	private void assinar(String tipo, XMLSignatureFactory fac,
			ArrayList<Transform> transformList, PrivateKey privateKey,
			KeyInfo ki, Document document, int index) throws Exception {
		NodeList elements = null;

		if (EVENTO.equals(tipo)) {
			elements = document.getElementsByTagName("infEvento");
		}
		else {
			elements = document.getElementsByTagName("infNFe");
		}
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(index);
		String id = el.getAttribute("Id");

		Reference ref = fac.newReference("#" + id,
				fac.newDigestMethod(DigestMethod.SHA1, null), transformList,
				null, null);

		SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(
				CanonicalizationMethod.INCLUSIVE,
				(C14NMethodParameterSpec) null), fac
				.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
				Collections.singletonList(ref));

		XMLSignature signature = fac.newXMLSignature(si, ki);

		DOMSignContext dsc = new DOMSignContext(privateKey, 
				document.getDocumentElement().getElementsByTagName(tipo).item(index));
		signature.sign(dsc);
	}

	private String assinaCancInutDpec(String xml, String tag) throws Exception {
		Document document = documentFactory(xml);

		XMLSignatureFactory signatureFactory = XMLSignatureFactory
				.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactory(signatureFactory);
		loadCertificates(signatureFactory);

		NodeList elements = document.getElementsByTagName(tag);
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
		String id = el.getAttribute("Id");

		Reference ref = signatureFactory.newReference("#" + id,
				signatureFactory.newDigestMethod(DigestMethod.SHA1, null),
				transformList, null, null);

		SignedInfo si = signatureFactory.newSignedInfo(signatureFactory
				.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null), signatureFactory
				.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
				Collections.singletonList(ref));

		XMLSignature signature = signatureFactory.newXMLSignature(si, keyInfo);

		DOMSignContext dsc = new DOMSignContext(privateKey, document.getFirstChild());
		signature.sign(dsc);

		return outputXML(document);
	}

	private ArrayList<Transform> signatureFactory(
			XMLSignatureFactory signatureFactory)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		ArrayList<Transform> transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = signatureFactory.newTransform(
				Transform.ENVELOPED, tps);
		Transform c14NTransform = signatureFactory.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);
		return transformList;
	}

	private Document documentFactory(String xml) throws SAXException,
			IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(xml.getBytes()));
		return document;
	}

	private String outputXML(Document doc) throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		String xml = os.toString();
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}
		return xml;
	}

	public void setKeyStore(KeyStore keyStore) {
		this.keyStore = keyStore;
	}

}

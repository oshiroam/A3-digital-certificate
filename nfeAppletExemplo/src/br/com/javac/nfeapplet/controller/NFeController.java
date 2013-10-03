package br.com.javac.nfeapplet.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.List;
import java.lang.reflect.*;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import br.com.javac.nfeapplet.business.service.AssinarXML;
import br.com.javac.nfeapplet.business.service.CertificadoWindows;
import br.com.javac.nfeapplet.business.service.SefazService;
import br.com.javac.nfeapplet.entity.Certificado;
import br.com.javac.nfeapplet.util.Utils;
import br.com.javac.nfeapplet.view.NFe;

public class NFeController {
	private final static String MSG_SELECIONE_UM_CERTIFIADO = "Selecione um Certificado Digital. Para exibir os Certiticados Digitais clique no botão Listar Certificados.";

	private NFe view;
	private CertificadoWindows certificadoWindows;
	private SefazService sefazService;
	private List<Certificado> listaDeCertificados;
	public String codigoDoEstado = "51"; // Mato grosso // Demais >> http://dtr2001.saude.gov.br/sas/decas/anexo01.mansia.htm
	
//	 WebServices de homologação
//	NfeRecepcao			2.00	https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeRecepcao2?wsdl
//	NfeRetRecepcao		2.00	https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeRetRecepcao2?wsdl
//	NfeInutilizacao		2.00	https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeInutilizacao2?wsdl
//	NfeConsultaProtocolo	2.00	https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeConsulta2?wsdl
//	NfeStatusServico	2.00	https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl
//	RecepcaoEvento		2.00	https://homologacao.sefaz.mt.gov.br/nfews/v2/services/RecepcaoEvento?wsdl
	
//	WebServices de produção
//	NfeRecepcao			2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeRecepcao2?wsdl
//	NfeRetRecepcao		2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeRetRecepcao2?wsdl
//	NfeInutilizacao		2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeInutilizacao2?wsdl
//	NfeConsultaProtocolo	2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeConsulta2?wsdl
//	NfeStatusServico	2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl
//	NfeConsultaCadastro	2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/CadConsultaCadastro2?wsdl
//	RecepcaoEvento		2.00	https://nfe.sefaz.mt.gov.br/nfews/v2/services/RecepcaoEvento?wsdl
	
	/**
	 * Construtor
	 * 
	 * @param view
	 */
	public NFeController(NFe view) {
		this.view = view;
	}
	
	/**
	 * Lista certificados
	 * 
	 */
	public void listaCerticados() {
		Thread processar = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					clearMessages();

					listaDeCertificados = getCertificadoWindows().listaCertificadoDisponiveis();
					if ((getListaDeCertificados() != null) && (!getListaDeCertificados().isEmpty())) {
						@SuppressWarnings("rawtypes")
						DefaultListModel listModel = new DefaultListModel();
						for (Certificado certificado : getListaDeCertificados()) {
							listModel.addElement(certificado.getEmitidoPara());
						}
						getView().getListaCertificados().setModel(listModel);
					}
				} catch (Exception e) {
					getView().getTextInformacao().append(e.toString());
				}
			}
		};
		processar.start();
	}

	/**
	 * Obtem dados do certificado
	 * -Emitido para
	 * - Alias
	 * - Validade 
	 */
	public void dadosDoCertificado() {
		Thread processar = new Thread() {
			@Override
			public void run() {
				try {
					clearMessages();
		            if ((getListaDeCertificados() != null) && (!getListaDeCertificados().isEmpty())) {
						int indexSelected = getView().getListaCertificados().getSelectedIndex();
		            	if (indexSelected != -1) {
			            	Certificado certificado = getListaDeCertificados().get(indexSelected);
							if (certificado != null) {
								getView().getTextInformacao().append("Emitido para: " + certificado.getEmitidoPara() + "\n\r");
								getView().getTextInformacao().append("Alias: " + certificado.getAlias() + "\n\r");
								getView().getTextInformacao().append("ValidoDe: " + certificado.getValidoDe() + "\n\r");
								getView().getTextInformacao().append("ValidoAte: " + certificado.getValidoAte());
							}
							else {
								throw new Exception("Certificado Digital não localizado.");
							}
		            	}
		            	else {
		            		throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
		            	}
					}
					else {
						throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
					}
				} catch (Exception e) {
					getView().getTextInformacao().append(e.getMessage());
				}
			}
		};
		processar.start();
	}
	
	/**
	 * Assinar xml fazendo verificações
	 * 
	 * Verifica se um certificado foi selecionado e se foi localizado.
	 * Chama método para assinar passando parâmetros 
	 */
	public void assinarXml() {
		Thread processar = new Thread() {
			@Override
			public void run() {
				try {
					clearMessages();
					startProgressBar("Aguarde! Assinando XML...");
		            if ((getListaDeCertificados() != null) && (!getListaDeCertificados().isEmpty())) {
						int indexSelected = getView().getListaCertificados().getSelectedIndex();
		            	if (indexSelected != -1) {
		            		Certificado certificado = getListaDeCertificados().get(indexSelected);
		            		if (certificado != null) {
								JFileChooser fileChooser = new JFileChooser();
								int retorno = fileChooser.showOpenDialog(null);
								if (retorno ==  JFileChooser.APPROVE_OPTION) {
									String xmlPath = fileChooser.getSelectedFile().getAbsolutePath();
									if ((xmlPath != null) && (!"".equals(xmlPath))) {
										assinatura(certificado, xmlPath);
									}
								}
		            		}
							else {
								throw new Exception("Certificado Digital não localizado.");
							}
		            	}
		            	else {
		            		throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
		            	}
		            }
					else {
						throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
					}
				} catch (Exception e) {
					getView().getTextInformacao().append(e.getMessage());
				} finally {
					stopProgressBar();
				}
			}
		};
		processar.start();
	}

	/**
	 * Consuilta status do serviço.
	 * Adicionei os links dos WS de homologação e de produção do estado MT
	 * 
	 * Faz verificações no certificado.
	 * @throws MalformedURLException 
	 */
	public void consultaStatusDoServico() throws MalformedURLException {
//		Produção
//        URL url = new URL("https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl");
		
//		Homologação
        URL url = new URL("https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl");
        
        Object data[] = new Object[20];
		startService("consultaStatusDoServico", url, data);
//		Thread processar = new Thread() {
//			@Override
//			public void run() {
//				try {
//					Produção
//		            URL url = new URL("https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl");
					
//					Homologação
//		            URL url = new URL("https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl");
//		            
//		            clearMessages();
//		            if ((getListaDeCertificados() != null) && (!getListaDeCertificados().isEmpty())) {
//						int indexSelected = getView().getListaCertificados().getSelectedIndex();
//		            	if (indexSelected != -1) {
//				            String senha = new String(getView().getEdtSenhaDoCertificado().getPassword());
//				            if ((senha == null) || ("".equals(senha))) {
//				            	throw new Exception("Digite a senha do Certificado Digital.");
//				            }
//
//							Certificado certificado = getListaDeCertificados().get(indexSelected);
//							if (certificado != null) {
//								startProgressBar("Aguarde! Consultando Status do Serviço...");
//								getCertificadoWindows().loadWsCerticates(url, certificado.getAlias(), senha);
//								
//								//Instancia um objeto do tipo Sefaz Service e invoca o método. 
//								String retorno = getSefazService().consultaStatusDoServico(codigoDoEstado, url);
//								getView().getTextInformacao().append(retorno);
//							}
//							else {
//								throw new Exception("Certificado Digital não localizado.");
//							}
//		            	}
//		            	else {
//		            		throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
//		            	}
//					}
//					else {
//						throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
//					}
//				} catch (Exception e) {
//					getView().getTextInformacao().append(e.getMessage());
//				} finally {
//					stopProgressBar();
//				}
//			}
//		};
//		processar.start();
	}
	
	public void startService(final String service, final URL url, Object data) {
//		SefazService serv = new SefazService();
		final Class<?> c1 = null;
		final Method method = null;
//		final Class[] paramTypes = new Class[]{String.class};
		final Object[] attValue = new Object[]{"Teste"};
		
		Thread processar = new Thread() {
			@Override
			public void run() {
				try {
		            clearMessages();
		            if ((getListaDeCertificados() != null) && (!getListaDeCertificados().isEmpty())) {
						int indexSelected = getView().getListaCertificados().getSelectedIndex();
		            	if (indexSelected != -1) {
				            String senha = new String(getView().getEdtSenhaDoCertificado().getPassword());
				            if ((senha == null) || ("".equals(senha))) {
				            	throw new Exception("Digite a senha do Certificado Digital.");
				            }

							Certificado certificado = getListaDeCertificados().get(indexSelected);
							if (certificado != null) {
								startProgressBar("Processando, aguarde...");
								getCertificadoWindows().loadWsCerticates(url, certificado.getAlias(), senha);
								
								//Instancia um objeto do tipo Sefaz Service e invoca o método. 
								c1 = Class.forName("br.com.javac.nfeapplet.business.service.SefazService");
//								method = c1.getMethod(service);
								String retorno = (String) c1.getClass().getMethod(service).invoke(c1.newInstance());
//								String retorno = (String) method.invoke(c1.newInstance(), attValue);
								
//								String retorno = getSefazService().service(codigoDoEstado, url);
								getView().getTextInformacao().append(retorno);
							}
							else {
								throw new Exception("Certificado Digital não localizado.");
							}
		            	}
		            	else {
		            		throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
		            	}
					}
					else {
						throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
					}
				} catch (Exception e) {
					getView().getTextInformacao().append(e.getMessage());
				} finally {
					stopProgressBar();
				}
			}
		};
		processar.start();
	}
	
	/**
	 * Inutilizaçao de nf-e
	 * 
	 * 
	 * Faz verificações no certificado.
	 */
//	public void nonUtilization() {
//		Thread processar = new Thread() {
//			@Override
//			public void run() {
//				try {
//					String codigoDoEstado = "51"; // Mato grosso // Demais >> http://dtr2001.saude.gov.br/sas/decas/anexo01.mansia.htm
////					Produção
////		            URL url = new URL("https://nfe.sefaz.mt.gov.br/nfews/v2/services/NfeInutilizacao2?wsdl");
//					
////					Homologação
//		            URL url = new URL("https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeInutilizacao2?wsdl");
//		            
//		            clearMessages();
//		            if ((getListaDeCertificados() != null) && (!getListaDeCertificados().isEmpty())) {
//						int indexSelected = getView().getListaCertificados().getSelectedIndex();
//		            	if (indexSelected != -1) {
//				            String senha = new String(getView().getEdtSenhaDoCertificado().getPassword());
//				            if ((senha == null) || ("".equals(senha))) {
//				            	throw new Exception("Digite a senha do Certificado Digital.");
//				            }
//
//							Certificado certificado = getListaDeCertificados().get(indexSelected);
//							if (certificado != null) {
//								startProgressBar("Aguarde! ...");
//								getCertificadoWindows().loadWsCerticates(url, certificado.getAlias(), senha);
//								
////								Instancia um objeto do tipo Sefaz Service e invoca o método.
////								TODO Fazer o método no SefazService
//								String retorno = getSefazService().nonUtilizationNfe(codigoDoEstado, url);
////								TODO Tem que descobrir como cada WS retorna as coisas, ver manual
//								getView().getTextInformacao().append(retorno);
//								
//							}
//							else {
//								throw new Exception("Certificado Digital não localizado.");
//							}
//		            	}
//		            	else {
//		            		throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
//		            	}
//					}
//					else {
//						throw new Exception(MSG_SELECIONE_UM_CERTIFIADO);
//					}
//				} catch (Exception e) {
//					getView().getTextInformacao().append(e.getMessage());
//				} finally {
//					stopProgressBar();
//				}
//			}
//		};
//		processar.start();
//	}
	
	/**
	 * 
	 * @param texto
	 */
	private void startProgressBar(String texto) {
		getView().getProgressBarStatus().setIndeterminate(true);
		getView().getProgressBarStatus().setString(texto);
		enableComponents(false);
	}

	/**
	 * 
	 */
	private void stopProgressBar() {
		getView().getProgressBarStatus().setIndeterminate(false);
		getView().getProgressBarStatus().setString("");
		enableComponents(true);
	}
	
	/**
	 * 
	 * @param enable
	 */
	private void enableComponents(boolean enable) {
		getView().getListaCertificados().setEnabled(enable);
		getView().getEdtSenhaDoCertificado().setEnabled(enable);
		getView().getBtnConsultarStatusServico().setEnabled(enable);
		getView().getBtnDadosDoCertificado().setEnabled(enable);
		getView().getBtnListarCertificados().setEnabled(enable);
		getView().getBtnAssinatura().setEnabled(enable);

		getView().getRbtLoteNfe().setEnabled(enable);
		getView().getRbtCancelamento().setEnabled(enable);
		getView().getRbtCce().setEnabled(enable);
		getView().getRbtDpec().setEnabled(enable);
		getView().getRbtInutilizacao().setEnabled(enable);
		getView().getTextInformacao().setEditable(enable);
	}

	/**
	 * Obtem dados do certificado para assinar o arquivo XML
	 * Dependedo da rotina selecionada, assina o arquivo correspondente
	 * 
	 * @param certificado
	 * @param xmlPath
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws Exception
	 */
	private void assinatura(Certificado certificado, String xmlPath) throws IOException, KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException, CertificateException, Exception {
		KeyStore keyStore = getCertificadoWindows().getKeyStore();
		String xmlAssinado = "";

		String xml = Utils.lerXML(xmlPath);
		xml = Utils.normalizeXML(xml);

		String alias = certificado.getAlias();
		String senha = new String(getView().getEdtSenhaDoCertificado().getPassword());
		AssinarXML assinarXML = new AssinarXML(keyStore, alias, senha);

		if (getView().getRbtLoteNfe().isSelected()) {
			xmlAssinado = assinarXML.assinaEnviNFe(xml);
		}
		else if (getView().getRbtCancelamento().isSelected()) {
			xmlAssinado = assinarXML.assinaCancNFe(xml);
		}
		else if (getView().getRbtCce().isSelected()) {
			xmlAssinado = assinarXML.assinaEnvEvento(xml);
		}
		else if (getView().getRbtDpec().isSelected()) {
			xmlAssinado = assinarXML.assinaEnvDPEC(xml);
		}
		else if (getView().getRbtInutilizacao().isSelected()) {
			xmlAssinado = assinarXML.assinaInutNFe(xml);
		}

		getView().getTextInformacao().append(xmlAssinado);
	}
	
	/**
	 * 
	 */
	private void clearMessages() {
		getView().getTextInformacao().setText("");
	}

	/**
	 * 
	 * @return
	 */
	public CertificadoWindows getCertificadoWindows() {
		if (certificadoWindows == null) {
			certificadoWindows = new CertificadoWindows();
		}
		return certificadoWindows;
	}

	/**
	 * Retorna um objeto do tipo SefazService. 
	 * 
	 * Verifica se o objeto sefazService foi instanciado, se for null, instância
	 * @return
	 */
	public SefazService getSefazService() {
		if (sefazService == null) {
			sefazService = new SefazService();
		}
		return sefazService;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Certificado> getListaDeCertificados() {
		return listaDeCertificados;
	}
	
	/**
	 * 
	 * @return
	 */
	public NFe getView() {
		return view;
	}
}


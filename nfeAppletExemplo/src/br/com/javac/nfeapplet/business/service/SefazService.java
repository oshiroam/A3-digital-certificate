package br.com.javac.nfeapplet.business.service;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsulta2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeCancelamento2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeInutilizacao2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeRecepcao2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.RecepcaoEventoStub;
//import br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.CadConsultaCadastro2Stub;

/**
 * 
 * @author alisson
 * 
 */
public class SefazService {

	/**
	 * Consulta status do serviço
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
	public String consultaStatusDoServico(String codigoDoEstado, URL url)
			throws XMLStreamException, RemoteException {
		StringBuilder xml = new StringBuilder();
		xml.append("")
			.append("<consStatServ versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
//				Homologação
				.append("<tpAmb>2</tpAmb>")
//				Produção
//				.append("<tpAmb>1</tpAmb>")
				
				.append("<cUF>")
					.append(codigoDoEstado)
				.append("</cUF>")
				
				.append("<xServ>STATUS</xServ>")
			.append("</consStatServ>");

		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
		NfeStatusServico2Stub.NfeDadosMsg dadosMsg = new NfeStatusServico2Stub.NfeDadosMsg();
		dadosMsg.setExtraElement(ome);

		NfeStatusServico2Stub.NfeCabecMsg nfeCabecMsg = new NfeStatusServico2Stub.NfeCabecMsg();
		/**
		 * Código do Estado.
		 */
		nfeCabecMsg.setCUF(codigoDoEstado);

		/**
		 * Versao do XML
		 */
		nfeCabecMsg.setVersaoDados("2.00");
		NfeStatusServico2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeStatusServico2Stub.NfeCabecMsgE();
		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

		NfeStatusServico2Stub stub = new NfeStatusServico2Stub(url.toString());
		NfeStatusServico2Stub.NfeStatusServicoNF2Result result = stub.nfeStatusServicoNF2(dadosMsg, nfeCabecMsgE);

		return result.getExtraElement().toString();
	}
	
	/**
	 * Consulta NF-e
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
	public String consultaNfe(String codigoDoEstado, URL url)
			throws XMLStreamException, RemoteException {
		StringBuilder xml = new StringBuilder();
		xml.append("")
				.append("<conSitNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
//					Homologação
					.append("<tpAmb>2</tpAmb>")
//					Produção
//					.append("<tpAmb>1</tpAmb>")
					
					.append("<cUF>")
						.append(codigoDoEstado)
					.append("</cUF>")
					.append("<xServ>CONSULTAR</xServ>")
//					TODO chave de acesso
					.append("<chNFe>Chave de acesso da NF-e</chNFe>")
				.append("</conSitNFe>");

		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
		NfeConsulta2Stub.NfeDadosMsg dadosMsg = new NfeConsulta2Stub.NfeDadosMsg();
		dadosMsg.setExtraElement(ome);

		NfeConsulta2Stub.NfeCabecMsg nfeCabecMsg = new NfeConsulta2Stub.NfeCabecMsg();
		/**
		 * Código do Estado.
		 */
		nfeCabecMsg.setCUF(codigoDoEstado);

		/**
		 * Versao do XML
		 */
		nfeCabecMsg.setVersaoDados("2.00");
		NfeConsulta2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeConsulta2Stub.NfeCabecMsgE();
		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

		NfeConsulta2Stub stub = new NfeConsulta2Stub(url.toString());
		NfeConsulta2Stub.NfeConsultaNF2Result result = stub.nfeConsultaNF2(dadosMsg, nfeCabecMsgE);

		return result.getExtraElement().toString();
	}
	
	/**
	 * Cancelamento do NF-e
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
	public String cancelamentoNfe(String codigoDoEstado, URL url)
			throws XMLStreamException, RemoteException {
		StringBuilder xml = new StringBuilder();
		xml.append("")
			.append("<cancNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
				.append("<infCanc>")
					.append("<Id>")
					.append("</Id>")
				
//					Homologação
					.append("<tpAmb>2</tpAmb>")
//					Produção
//					.append("<tpAmb>1</tpAmb>")
					.append("<xServ>CANCELAR</xServ>")
					
//					TODO Chave de acesso da NF-e (vide item 5.4)
					.append("<chNFe>")
					.append("</chNFe>")
					
//					TODO Informar o número do Protocolo de Autorização da NF-e a ser Cancelada (vide item 5.4).
					.append("<nProt>")
					.append("</nProt>")
					
//					TODO Informar a justificativa do cancelamento
					.append("<xJust>")
					.append("</xJust>")
				.append("</infCanc>")
				
//				TODO Assinatura XML do grupo identificado pelo atributo “Id”
				.append("<Signature>")
				.append("</Signature>")
			.append("</cancNFe>");

		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
		NfeCancelamento2Stub.NfeDadosMsg dadosMsg = new NfeCancelamento2Stub.NfeDadosMsg();
		dadosMsg.setExtraElement(ome);

		NfeCancelamento2Stub.NfeCabecMsg nfeCabecMsg = new NfeCancelamento2Stub.NfeCabecMsg();
		/**
		 * Código do Estado.
		 */
		nfeCabecMsg.setCUF(codigoDoEstado);

		/**
		 * Versao do XML
		 */
		nfeCabecMsg.setVersaoDados("2.00");
		NfeCancelamento2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeCancelamento2Stub.NfeCabecMsgE();
		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

		NfeCancelamento2Stub stub = new NfeCancelamento2Stub(url.toString());
		NfeCancelamento2Stub.NfeCancelamentoNF2Result result = stub.nfeCancelamentoNF2(dadosMsg, nfeCabecMsgE);

		return result.getExtraElement().toString();
	}
	
	/**
	 * Serviço destinado ao atendimento de solicitações de inutilização de numeração.
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
	public String inutilizacao(String codigoDoEstado, URL url)
			throws XMLStreamException, RemoteException {
		StringBuilder xml = new StringBuilder();
		xml.append("")
			.append("<inutNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
				.append("<infInut>")
					.append("<Id>")
					.append("</Id>")
//					Homologação
					.append("<tpAmb>2</tpAmb>")
//					Produção
//					.append("<tpAmb>1</tpAmb>")
					.append("<xServ>INUTILIZAR</xServ>")
					
					.append("<cUF>")
						.append(codigoDoEstado)
					.append("</cUF>")
					
					.append("<ano>")
					.append("</ano>")
					
					.append("<CNPJ>")
					.append("</CNPJ>")
					
					.append("<mod>")
					.append("</mod>")
					
					.append("<serie>")
					.append("</serie>")
					
					.append("<nNFIni>")
					.append("</nNFIni>")
					
					.append("<nNFFin>")
					.append("</nNFFin>")
					
					.append("<xJust>")
					.append("</xJust>")
				.append("</infInut>")
				
				.append("<Signature>")
				.append("</Signature>")
			.append("</inutNFe>");

		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
		NfeInutilizacao2Stub.NfeDadosMsg dadosMsg = new NfeInutilizacao2Stub.NfeDadosMsg();
		dadosMsg.setExtraElement(ome);

		NfeInutilizacao2Stub.NfeCabecMsg nfeCabecMsg = new NfeInutilizacao2Stub.NfeCabecMsg();
		/**
		 * Código do Estado.
		 */
		nfeCabecMsg.setCUF(codigoDoEstado);

		/**
		 * Versao do XML
		 */
		nfeCabecMsg.setVersaoDados("2.00");
		NfeInutilizacao2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeInutilizacao2Stub.NfeCabecMsgE();
		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

		NfeInutilizacao2Stub stub = new NfeInutilizacao2Stub(url.toString());
		NfeInutilizacao2Stub.NfeInutilizacaoNF2Result result = stub.nfeInutilizacaoNF2(dadosMsg, nfeCabecMsgE);

		return result.getExtraElement().toString();
	}

	/**
	 * Serviço destinado à recepção de mensagens de lote de NF-e.
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
	public String recepcao(String codigoDoEstado, URL url)
			throws XMLStreamException, RemoteException {
		StringBuilder xml = new StringBuilder();
		xml.append("")
			.append("<enviNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
				.append("<idLote>")
				.append("</idLote>")
				
				.append("<NFe>")
				.append("</NFe>")
			.append("</enviNFe>");

		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
		NfeRecepcao2Stub.NfeDadosMsg dadosMsg = new NfeRecepcao2Stub.NfeDadosMsg();
		dadosMsg.setExtraElement(ome);

		NfeRecepcao2Stub.NfeCabecMsg nfeCabecMsg = new NfeRecepcao2Stub.NfeCabecMsg();
		/**
		 * Código do Estado.
		 */
		nfeCabecMsg.setCUF(codigoDoEstado);

		/**
		 * Versao do XML
		 */
		nfeCabecMsg.setVersaoDados("2.00");
		NfeRecepcao2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeRecepcao2Stub.NfeCabecMsgE();
		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

		NfeRecepcao2Stub stub = new NfeRecepcao2Stub(url.toString());
		NfeRecepcao2Stub.NfeRecepcaoLote2Result result = stub.nfeRecepcaoLote2(dadosMsg, nfeCabecMsgE);

		return result.getExtraElement().toString();
	}
	
	/**
	 * Serviço destinado a retornar o resultado do processamento do lote de NF-e.
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
	public String retRecepcao(String codigoDoEstado, URL url)
			throws XMLStreamException, RemoteException {
		StringBuilder xml = new StringBuilder();
		xml.append("")
			.append("<consReciNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
//					Homologação
					.append("<tpAmb>2</tpAmb>")
//					Produção
//					.append("<tpAmb>1</tpAmb>")
				
				.append("<nRec>")
				.append("</nRec>")
			.append("</consReciNFe>");

		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
		RecepcaoEventoStub.NfeDadosMsg dadosMsg = new RecepcaoEventoStub.NfeDadosMsg();
		dadosMsg.setExtraElement(ome);

		RecepcaoEventoStub.NfeCabecMsg nfeCabecMsg = new RecepcaoEventoStub.NfeCabecMsg();
		/**
		 * Código do Estado.
		 */
		nfeCabecMsg.setCUF(codigoDoEstado);

		/**
		 * Versao do XML
		 */
		nfeCabecMsg.setVersaoDados("2.00");
		RecepcaoEventoStub.NfeCabecMsgE nfeCabecMsgE = new RecepcaoEventoStub.NfeCabecMsgE();
		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

		RecepcaoEventoStub stub = new RecepcaoEventoStub(url.toString());
		RecepcaoEventoStub.NfeRecepcaoEventoResult result = stub.nfeRecepcaoEvento(dadosMsg, nfeCabecMsgE);
				
		return result.getExtraElement().toString();
	}
	
	/**
	 * Serviço para consultar o cadastro de contribuintes do ICMS da unidade federada.
	 * 
	 * @param codigoDoEstado
	 * @param url
	 * @return
	 * @throws XMLStreamException
	 * @throws RemoteException
	 */
//	public String consultaCadastro(String codigoDoEstado, URL url)
//			throws XMLStreamException, RemoteException {
//		StringBuilder xml = new StringBuilder();
//		xml.append("")
//			.append("<enviNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
//				.append("<idLote>")
//				.append("</idLote>")
//				
//				.append("<NFe>")
//				.append("</NFe>")
//			.append("</enviNFe>");
//
//		OMElement ome = AXIOMUtil.stringToOM(xml.toString());
//		CadConsultaCadastro2Stub.NfeDadosMsg dadosMsg = new CadConsultaCadastro2Stub.NfeDadosMsg();
//		dadosMsg.setExtraElement(ome);
//
//		CadConsultaCadastro2Stub.NfeCabecMsg nfeCabecMsg = new CadConsultaCadastro2Stub.NfeCabecMsg();
//		/**
//		 * Código do Estado.
//		 */
//		nfeCabecMsg.setCUF(codigoDoEstado);
//
//		/**
//		 * Versao do XML
//		 */
//		nfeCabecMsg.setVersaoDados("2.00");
//		CadConsultaCadastro2Stub.NfeCabecMsgE nfeCabecMsgE = new CadConsultaCadastro2Stub.NfeCabecMsgE();
//		nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
//
//		CadConsultaCadastro2Stub stub = new CadConsultaCadastro2Stub(url.toString());
//		CadConsultaCadastro2Stub.consultaCadastro2Result result = stub.consultaCadastro2(dadosMsg, nfeCabecMsgE);
//
//		return result.getExtraElement().toString();
//	}
}


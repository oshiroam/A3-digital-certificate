package br.com.javac.nfeapplet.business.service;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2Stub;

public class SefazService {

	public String consultaStatusDoServico(String codigoDoEstado, URL url) throws XMLStreamException, RemoteException {
            StringBuilder xml = new StringBuilder();
            xml.append("")
                .append("<consStatServ versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
                .append("<tpAmb>2</tpAmb>")
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
	
}

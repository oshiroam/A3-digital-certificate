

/**
 * RecepcaoEvento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento;

    /*
     *  RecepcaoEvento java interface
     */

    public interface RecepcaoEvento {
          

        /**
          * Auto generated method signature
          * Recepção de mensagem de Evento da NF-e.
                    * @param nfeDadosMsg0
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.NfeRecepcaoEventoResult nfeRecepcaoEvento(

                        br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Recepção de mensagem de Evento da NF-e.
                * @param nfeDadosMsg0
            
                * @param nfeCabecMsg1
            
          */
        public void startnfeRecepcaoEvento(

            br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.RecepcaoEventoCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
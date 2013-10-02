

/**
 * NfeCancelamento2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2;

    /*
     *  NfeCancelamento2 java interface
     */

    public interface NfeCancelamento2 {
          

        /**
          * Auto generated method signature
          * Cancelamento de NF-e
                    * @param nfeDadosMsg0
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeCancelamentoNF2Result nfeCancelamentoNF2(

                        br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Cancelamento de NF-e
                * @param nfeDadosMsg0
            
                * @param nfeCabecMsg1
            
          */
        public void startnfeCancelamentoNF2(

            br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.nfecancelamento2.NfeCancelamento2CallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
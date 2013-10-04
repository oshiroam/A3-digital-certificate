

/**
 * NfeRecepcao2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2;

    /*
     *  NfeRecepcao2 java interface
     */

    public interface NfeRecepcao2 {
          

        /**
          * Auto generated method signature
          * Transmissao de Lote de NF-e
                    * @param nfeDadosMsg0
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeRecepcaoLote2Result nfeRecepcaoLote2(

                        br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Transmissao de Lote de NF-e
                * @param nfeDadosMsg0
            
                * @param nfeCabecMsg1
            
          */
        public void startnfeRecepcaoLote2(

            br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.nferecepcao2.NfeRecepcao2CallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
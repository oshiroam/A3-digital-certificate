

/**
 * NfeInutilizacao2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2;

    /*
     *  NfeInutilizacao2 java interface
     */

    public interface NfeInutilizacao2 {
          

        /**
          * Auto generated method signature
          * Inutilizacao de numeracao de NF-e
                    * @param nfeDadosMsg0
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeInutilizacaoNF2Result nfeInutilizacaoNF2(

                        br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Inutilizacao de numeracao de NF-e
                * @param nfeDadosMsg0
            
                * @param nfeCabecMsg1
            
          */
        public void startnfeInutilizacaoNF2(

            br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeInutilizacao2CallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
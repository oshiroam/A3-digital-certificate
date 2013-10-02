

/**
 * NfeStatusServico2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2;

    /*
     *  NfeStatusServico2 java interface
     */

    public interface NfeStatusServico2 {
          

        /**
          * Auto generated method signature
          * 
                    * @param nfeDadosMsg0
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServicoNF2Result nfeStatusServicoNF2(

                        br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param nfeDadosMsg0
            
                * @param nfeCabecMsg1
            
          */
        public void startnfeStatusServicoNF2(

            br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2CallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
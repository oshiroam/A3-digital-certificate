

/**
 * NfeConsulta2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2;

    /*
     *  NfeConsulta2 java interface
     */

    public interface NfeConsulta2 {
          

        /**
          * Auto generated method signature
          * Consulta situacao atual da NF-e
                    * @param nfeDadosMsg0
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsultaNF2Result nfeConsultaNF2(

                        br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Consulta situacao atual da NF-e
                * @param nfeDadosMsg0
            
                * @param nfeCabecMsg1
            
          */
        public void startnfeConsultaNF2(

            br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeDadosMsg nfeDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsulta2CallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
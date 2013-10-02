

/**
 * CadConsultaCadastro2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2;

    /*
     *  CadConsultaCadastro2 java interface
     */

    public interface CadConsultaCadastro2 {
          

        /**
          * Auto generated method signature
          * Consulta Cadastro de Contribuintes do ICMS
                    * @param consultaCadastro20
                
                    * @param nfeCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.ConsultaCadastro2Response consultaCadastro2(

                        br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.ConsultaCadastro2 consultaCadastro20,br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.NfeCabecMsgE nfeCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Consulta Cadastro de Contribuintes do ICMS
                * @param consultaCadastro20
            
                * @param nfeCabecMsg1
            
          */
        public void startconsultaCadastro2(

            br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.ConsultaCadastro2 consultaCadastro20,br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.NfeCabecMsgE nfeCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.CadConsultaCadastro2CallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
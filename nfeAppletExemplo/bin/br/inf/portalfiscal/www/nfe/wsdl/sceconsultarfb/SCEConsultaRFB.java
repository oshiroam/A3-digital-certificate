

/**
 * SCEConsultaRFB.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb;

    /*
     *  SCEConsultaRFB java interface
     */

    public interface SCEConsultaRFB {
          

        /**
          * Auto generated method signature
          * 
                    * @param sceDadosMsg0
                
                    * @param sceCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb.SceConsultaDPECResult sceConsultaDPEC(

                        br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb.SceDadosMsg sceDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb.SceCabecMsgE sceCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param sceDadosMsg0
            
                * @param sceCabecMsg1
            
          */
        public void startsceConsultaDPEC(

            br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb.SceDadosMsg sceDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb.SceCabecMsgE sceCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.sceconsultarfb.SCEConsultaRFBCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
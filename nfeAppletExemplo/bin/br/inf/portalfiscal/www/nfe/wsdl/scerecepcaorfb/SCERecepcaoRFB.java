

/**
 * SCERecepcaoRFB.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb;

    /*
     *  SCERecepcaoRFB java interface
     */

    public interface SCERecepcaoRFB {
          

        /**
          * Auto generated method signature
          * 
                    * @param sceDadosMsg0
                
                    * @param sceCabecMsg1
                
         */

         
                     public br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb.SceRecepcaoDPECResult sceRecepcaoDPEC(

                        br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb.SceDadosMsg sceDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb.SceCabecMsgE sceCabecMsg1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param sceDadosMsg0
            
                * @param sceCabecMsg1
            
          */
        public void startsceRecepcaoDPEC(

            br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb.SceDadosMsg sceDadosMsg0,br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb.SceCabecMsgE sceCabecMsg1,
                

            final br.inf.portalfiscal.www.nfe.wsdl.scerecepcaorfb.SCERecepcaoRFBCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
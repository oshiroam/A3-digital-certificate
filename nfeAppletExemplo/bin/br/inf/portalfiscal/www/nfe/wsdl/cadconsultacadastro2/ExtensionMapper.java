
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2".equals(namespaceURI) &&
                  "nfeDadosMsg_type0".equals(typeName)){
                   
                            return  br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.NfeDadosMsg_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2".equals(namespaceURI) &&
                  "consultaCadastro2Result_type0".equals(typeName)){
                   
                            return  br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.ConsultaCadastro2Result_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2".equals(namespaceURI) &&
                  "nfeCabecMsg".equals(typeName)){
                   
                            return  br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.NfeCabecMsg.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
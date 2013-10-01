package br.com.javac.nfeapplet.entity;

import java.util.Date;

public class Certificado {
	private String emitidoPara;
	private String alias;
	private Date validoDe;
	private Date validoAte;
	
	public Certificado() {

	}

	public String getEmitidoPara() {
		return emitidoPara;
	}

	public void setEmitidoPara(String emitidoPara) {
		this.emitidoPara = emitidoPara;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Date getValidoDe() {
		return validoDe;
	}

	public void setValidoDe(Date validoDe) {
		this.validoDe = validoDe;
	}

	public Date getValidoAte() {
		return validoAte;
	}

	public void setValidoAte(Date validoAte) {
		this.validoAte = validoAte;
	}

}

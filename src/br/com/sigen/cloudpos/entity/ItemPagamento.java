package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;

public class ItemPagamento {

	private BigDecimal valor;
	private String tipoPagamento;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

}

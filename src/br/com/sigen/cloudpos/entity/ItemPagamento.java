package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;

public class ItemPagamento {

	private BigDecimal valor;
	private String tipoPagamento;
	private String redeCartao;
	private Integer parcelas;

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

	public String getRedeCartao() {
		return redeCartao;
	}

	public void setRedeCartao(String redeCartao) {
		this.redeCartao = redeCartao;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

}

package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;

public class Produto {

	private String codigo;

	private String descricao;

	private BigDecimal valorUnitario;
	
	public Produto() {
	}

	public Produto(String codigo, String descricao, BigDecimal valorUnitario) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
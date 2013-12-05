package br.com.sigen.cloudpos.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String unidadeMedida;

	private BigDecimal valorUnitario;

	public Produto() {
	}

	public Produto(String codigo, String descricao, String unidadeMedida,
			BigDecimal valorUnitario) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.unidadeMedida = unidadeMedida;
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

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
}
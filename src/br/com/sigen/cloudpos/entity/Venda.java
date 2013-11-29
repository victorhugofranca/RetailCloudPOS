package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;

public class Venda {

	private BigDecimal valorTotal;

	public void realizarDesconto(BigDecimal valorDesconto) {
		setValorTotal(getValorTotal().subtract(valorDesconto));
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}

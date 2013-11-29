package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;

public class ItemVenda {

	private String indice;
	private Produto produto;
	private BigDecimal quantidade;
	private BigDecimal valorTotal;

	public ItemVenda() {
		// TODO Auto-generated constructor stub
	}

	public ItemVenda(Produto produto, BigDecimal quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorTotal = quantidade.multiply(produto.getValorUnitario());
	}

	public void realizarDesconto(BigDecimal valorDesconto) {
		produto.setValorUnitario(produto.getValorUnitario().subtract(
				valorDesconto));
		setValorTotal(produto.getValorUnitario().multiply(getQuantidade()));
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}

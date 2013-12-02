package br.com.sigen.cloudpos.business;

import java.math.BigDecimal;
import java.util.ArrayList;

import br.com.sigen.cloudpos.entity.ItemPagamento;
import br.com.sigen.cloudpos.entity.Pagamento;

public class PagamentoBuilder {

	public static final Pagamento createPagamento() {
		Pagamento pagamento = new Pagamento();
		pagamento.setValorTotal(BigDecimal.ZERO);
		pagamento.setSaldoPagamento(BigDecimal.TEN.multiply(BigDecimal.TEN));
		pagamento.setItensPagamento(new ArrayList<ItemPagamento>());
		return pagamento;
	}

}

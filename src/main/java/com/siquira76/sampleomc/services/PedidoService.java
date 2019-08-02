package com.siquira76.sampleomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.ItemPedido;
import com.siquira76.sampleomc.domain.PagamentoComBoleto;
import com.siquira76.sampleomc.domain.Pedido;
import com.siquira76.sampleomc.domain.enums.EstadoPagamento;
import com.siquira76.sampleomc.repositories.ItemPedidoRepository;
import com.siquira76.sampleomc.repositories.PagamentoRepository;
import com.siquira76.sampleomc.repositories.PedidoRepository;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repo;
	
	@Autowired
	BoletoService boletoService;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFondException("Objeto não emcontrado id: " 
				+ id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.buscar(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}

}

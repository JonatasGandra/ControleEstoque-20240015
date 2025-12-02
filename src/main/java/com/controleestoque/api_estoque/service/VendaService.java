package com.controleestoque.api_estoque.service;

import com.controleestoque.api_estoque.dto.ItemVendaDTO;
import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> buscarPorCliente(Long clienteId) {
        return vendaRepository.findByClienteId(clienteId);
    }

    @Transactional
    public Venda registrarVenda(VendaRequestDTO vendaRequest) {
        Cliente cliente = clienteRepository.findById(vendaRequest.getClienteId())
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (vendaRequest.getItens() == null || vendaRequest.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um item");
        }

        Venda venda = new Venda(cliente);

        for (ItemVendaDTO itemDTO : vendaRequest.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Produto com ID " + itemDTO.getProdutoId() + " não encontrado"));

            Estoque estoque = estoqueRepository.findByProdutoId(produto.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Estoque não encontrado para o produto: " + produto.getNome()));

            if (estoque.getQuantidade() < itemDTO.getQuantidade()) {
                throw new IllegalArgumentException(
                    "Estoque insuficiente para o produto: " + produto.getNome() + 
                    ". Disponível: " + estoque.getQuantidade() + 
                    ", Solicitado: " + itemDTO.getQuantidade());
            }

            estoque.setQuantidade(estoque.getQuantidade() - itemDTO.getQuantidade());
            estoqueRepository.save(estoque);

            ItemVenda itemVenda = new ItemVenda(produto, itemDTO.getQuantidade(), produto.getPreco());
            venda.adicionarItem(itemVenda);
        }

        venda.calcularValorTotal();
        return vendaRepository.save(venda);
    }
}

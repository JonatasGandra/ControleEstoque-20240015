package com.controleestoque.api_estoque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controleestoque.api_estoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNome (String nome);
    List<Produto> findByNomeContaining (String parteDoNome);

}
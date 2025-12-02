package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.repository.EstoqueRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        return estoqueRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<Estoque> buscarPorProduto(@PathVariable Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

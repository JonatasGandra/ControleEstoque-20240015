package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<Venda>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id) {
        return vendaService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venda>> buscarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vendaService.buscarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<?> registrarVenda(@RequestBody VendaRequestDTO vendaRequest) {
        try {
            Venda venda = vendaService.registrarVenda(vendaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(venda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

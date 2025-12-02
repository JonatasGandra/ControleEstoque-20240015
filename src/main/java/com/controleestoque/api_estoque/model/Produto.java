package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal preco;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;

    public Produto (){}

    public Produto(String nome, BigDecimal preco, Estoque estoque, Categoria categoria) {
            this.nome = nome;
            this.preco = preco;
            this.estoque = estoque;
            this.categoria = categoria;
        }

        public Long getId() {
            return id;
        }
        public void SetId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }

        public BigDecimal getPreco() {
            return preco;
        }
        public void setPreco(BigDecimal preco) {
            this.preco = preco;
        }

        public Estoque getEstoque() {
            return estoque;
        }
        public void setEstoque(Estoque estoque) {
            this.estoque = estoque;
        }

        public Categoria getCategoria() {
            return categoria;
        }
        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }
}

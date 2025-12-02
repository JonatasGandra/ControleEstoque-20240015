# API de Controle de Estoque com Módulo de Vendas

Sistema completo de gerenciamento de estoque e vendas desenvolvido com Spring Boot.

## Funcionalidades

### Módulo de Estoque (Existente)
- Gerenciamento de Produtos
- Controle de Categorias
- Gestão de Fornecedores
- Controle de Estoque

### Módulo de Vendas (Novo)
- **CRUD de Clientes**: Cadastro completo de clientes
- **Registro de Vendas**: Sistema de vendas com múltiplos produtos
- **Baixa Automática de Estoque**: Atualização automática do estoque após cada venda
- **Validação de Disponibilidade**: Verifica estoque antes de processar vendas
- **Transações Seguras**: Rollback automático em caso de erro

## Tecnologias

- Java 21
- Spring Boot 3.5.8
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Endpoints da API

### Clientes
- `GET /api/clientes` - Lista todos os clientes
- `GET /api/clientes/{id}` - Busca cliente por ID
- `POST /api/clientes` - Cria novo cliente
- `PUT /api/clientes/{id}` - Atualiza cliente
- `DELETE /api/clientes/{id}` - Remove cliente

### Vendas
- `GET /api/vendas` - Lista todas as vendas
- `GET /api/vendas/{id}` - Busca venda por ID
- `GET /api/vendas/cliente/{clienteId}` - Lista vendas de um cliente
- `POST /api/vendas` - Registra nova venda (com baixa automática de estoque)

## Exemplo de Uso

### Criar Cliente
```json
POST /api/clientes
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "telefone": "(11) 98765-4321",
  "endereco": "Rua A, 123"
}
```

### Registrar Venda
```json
POST /api/vendas
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 3,
      "quantidade": 1
    }
  ]
}
```

## Lógica de Negócios

O sistema implementa validações críticas:
- Verifica disponibilidade de estoque antes de processar vendas
- Dá baixa automática no estoque após confirmação da venda
- Retorna erro HTTP 400 se houver estoque insuficiente
- Realiza rollback completo da transação em caso de erro
- Armazena o preço do produto no momento da venda

## Como Executar

```bash
# Compilar
./mvnw clean install

# Executar
./mvnw spring-boot:run
```


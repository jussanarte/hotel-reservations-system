# 🏗️ Arquitetura do Sistema — Hotel Kwanza Palace

Documento técnico que descreve a arquitetura, padrões aplicados, relações entre componentes e decisões de design do sistema.

## 📑 Índice

- [Visão geral](#visão-geral)
- [Camadas da arquitetura](#camadas-da-arquitetura)
- [Diagrama de dependências](#diagrama-de-dependências)
- [Padrões de design](#padrões-de-design)
- [Modelo de domínio](#modelo-de-domínio)
- [Persistência de dados](#persistência-de-dados)
- [Fluxo de execução](#fluxo-de-execução)

## 🔍 Visão geral

O sistema é uma aplicação de consola (console) escrita em Java 24, organizada em camadas seguindo uma arquitetura em camadas simplificada. A comunicação entre camadas é unidirecional: apresentação → serviço → domínio.

```
┌─────────────────────────────────────┐
│        Camada de Apresentação       │
│   app.Main / utils.Menus            │
├─────────────────────────────────────┤
│          Camada de Serviço          │
│   ReservaService / FinanceiroService│
├─────────────────────────────────────┤
│        Camada de Persistência       │
│      SerializacaoService            │
├─────────────────────────────────────┤
│          Camada de Domínio          │
│   model.entities / model.enums      │
├─────────────────────────────────────┤
│         Camada de Utilitários       │
│   Validador / DomainException       │
│   ICalculadoraServico               │
└─────────────────────────────────────┘
```

## 📦 Camadas da arquitetura

### 1. Apresentação (`app/`, `utils/Menus.java`)

| Classe | Responsabilidade |
|--------|------------------|
| `app.Main` | Ponto de entrada. Carrega dados serializados, sincroniza contadores, lança o menu principal. |
| `utils.Menus` | Interface de consola interativa. Apresenta menus, lê input do utilizador, orquestra chamadas aos serviços. |

### 2. Serviço (`service/`)

| Classe | Responsabilidade |
|--------|------------------|
| `ReservaService` | Lógica de negócio de reservas: confirmar, cancelar, check-in, check-out, sobreposição de datas, adição de pagamentos e serviços. |
| `FinanceiroService` | Cálculos financeiros: total de serviços, total da reserva, saldo, total pago. |
| `SerializacaoService` | Persistência: gravar e carregar listas de objetos para/de ficheiros via `ObjectOutputStream`/`ObjectInputStream`. |

### 3. Domínio (`model/`)

| Camada | Conteúdo |
|--------|----------|
| `model.entities` | Entidades de negócio: `Cliente`, `Quarto`, `Reserva`, `Pagamento`, `ServicoAdicional`. |
| `model.enums` | Tipos enumerados: `TipoQuarto`, `TipoServico`, `MetodoPagamento`, `FormaCobranca`, `EstadoReserva`, `EstadoQuarto`, `EstadoPagamento`. |

### 4. Utilitários (`utils/`, `exceptions/`, `interfaces/`)

| Classe | Responsabilidade |
|--------|------------------|
| `Validador` | Validação estática de nomes, telefones, emails, documentos e datas. |
| `DomainException` | Exceção de domínio customizada (unchecked) para violações de regras de negócio. |
| `ICalculadoraServico` | Interface que define o contrato para cálculo de serviços adicionais. |

## 🔗 Diagrama de dependências

```
Main
 ├── SerializacaoService (gravar/carregar dados)
 └── Menus (menu principal)
      ├── ReservaService
      │    └── FinanceiroService
      ├── SerializacaoService
      ├── Validador
      ├── Cliente
      ├── Quarto
      ├── Reserva
      │    ├── Cliente
      │    ├── Quarto
      │    ├── Pagamento
      │    └── ServicoAdicional
      │         └── FormaCobranca
      │              └── ICalculadoraServico
      └── DomainException
```

**Regra:** as dependências são sempre descendentes. Nunca uma classe de domínio depende de uma classe de serviço, e nunca uma classe de serviço depende da camada de apresentação.

## 🧩 Padrões de design

### Strategy Pattern (via Enum)

`FormaCobranca` implementa `ICalculadoraServico`, onde cada constante do enum fornece uma implementação diferente do método `calcular()`:

| Constante | Cálculo | Parâmetros utilizados |
|-----------|---------|----------------------|
| `POR_NOITE` | `precoUnitario * noites` | `precoUnitario`, `noites` |
| `FIXO` | `precoUnitario` | `precoUnitario` |
| `POR_UNIDADE` | `precoUnitario * quantidade` | `precoUnitario`, `quantidade` |

Isto permite adicionar novas formas de cobranca sem modificar o código que invoca o cálculo.

### Custom Exception Pattern

Todas as violações de regras de negócio são sinalizadas com `DomainException`, uma exceção unchecked que transporta uma mensagem descritiva do erro.

### Serializable Persistence Pattern

Todas as entidades implementam `Serializable`. A `SerializacaoService` utiliza `ObjectOutputStream` / `ObjectInputStream` para gravar e carregar listas de objetos.

### Static Counter Pattern

`Quarto` e `Reserva` utilizam contadores estáticos (`contador`) para gerar IDs incrementais. No arranque, `Main.java` sincroniza estes contadores com o maior ID existente nos dados carregados.

## 🧬 Modelo de domínio

### Entidades e relações

```
Hotel ──(1..*)── Quarto
                  │
                  │  (1..*)
Hotel ──(1..*)── Reserva ──(1)── Cliente
                  │
            ┌─────┴─────┐
            │           │
     Pagamento   ServicoAdicional
     (0..*)        (0..*)
```

### Multiplicidades

| Relação | Multiplicidade | Tipo |
|---------|---------------|------|
| Hotel → Quarto | 1 para 0..* | Agregação |
| Reserva → Cliente | 0..* para 1 | Associação |
| Reserva → Quarto | 0..* para 1 | Associação |
| Reserva → Pagamento | 1 para 0..* | Composição |
| Reserva → ServicoAdicional | 1 para 0..* | Composição |

> Pagamentos e ServicosAdicionais são parte da Reserva (composição) — não existem fora dela.

### Enum `FormaCobranca` e `ICalculadoraServico`

```
ICalculadoraServico (interface)
  │
  └── FormaCobranca (enum)
        ├── POR_NOITE   → calcular(preco, qtd, noites) = preco * noites
        ├── FIXO        → calcular(preco, qtd, noites) = preco
        └── POR_UNIDADE → calcular(preco, qtd, noites) = preco * qtd
```

## 💾 Persistência de dados

### Mecanismo

Serialização Java (`ObjectOutputStream` / `ObjectInputStream`) — todos os dados são gravados em ficheiros binários `.ser`.

### Ficheiros

| Ficheiro | Conteúdo | Formato |
|----------|----------|---------|
| `data/clientes.ser` | `ArrayList<Cliente>` | Java Serialization |
| `data/quartos.ser` | `ArrayList<Quarto>` | Java Serialization |
| `data/reservas.ser` | `ArrayList<Reserva>` | Java Serialization |

### Ciclo de vida

1. **Arranque:** `Main` carrega os 3 ficheiros via `SerializacaoService.carregar()`
2. **Operação:**Dados são modificados em memória durante a sessão
3. **Gravação:** Após cada operação, `SerializacaoService.gravar()` persiste as listas atualizadas
4. **Reinício:** O ciclo repete-se a partir do passo 1

## 🔄 Fluxo de execução

```
Main.main()
  │
  ├─ SerializacaoService.carregar("data/quartos.ser")
  ├─ SerializacaoService.carregar("data/clientes.ser")
  ├─ SerializacaoService.carregar("data/reservas.ser")
  │
  ├─ Sincronizar contadores (Quarto.contador, Reserva.contador)
  │
  └─ Menus.executarMenuPrincipal()
       │
       ├─ [1] Menus.executarMenuQuartos()
       │     ├─ Registar → new Quarto() → lista.add()
       │     ├─ Listar → percorrer lista
       │     └─ Alterar estado → quarto.setEstado()
       │
       ├─ [2] Menus.executarMenuClientes()
       │     ├─ Registar → new Cliente() → lista.add()
       │     └─ Listar → percorrer lista
       │
       ├─ [3] Menus.executarMenuReservas()
       │     ├─ Criar → new Reserva() → ReservaService + FinanceiroService
       │     ├─ Confirmar → ReservaService.confirmarReserva()
       │     ├─ Cancelar → ReservaService.cancelarReserva()
       │     ├─ Check-in → ReservaService.fazerCheckIn()
       │     ├─ Check-out → ReservaService.fazerCheckOut()
       │     ├─ Pagamento → new Pagamento() → ReservaService.adicionarPagamento()
       │     └─ Servico → new ServicoAdicional() → ReservaService.adicionarServico()
       │
       └─ [0] SerializacaoService.gravar() → System.exit(0)
```

## 📐 Princípios aplicados

| Princípio | Aplicação |
|-----------|-----------|
| **Encapsulamento** | Atributos privados com getters/setters. Listas retornadas como imutáveis (`Collections.unmodifiableList`). |
| **Polimorfismo** | `FormaCobranca` implementa `ICalculadoraServico` com comportamento diferente por constante. |
| **Herança** | `DomainException extends RuntimeException` |
| **Separação de responsabilidades** | Camadas distintas para UI, lógica de negócio, persistência e modelo. |
| **Uso de `BigDecimal`** | Todos os valores monetários utilizam `BigDecimal` para evitar erros de precisão de ponto flutuante. |

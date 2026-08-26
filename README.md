# 🏨 Sistema de Reservas de Hotel — Kwanza Palace

Sistema de gestão hoteleira em **Java** que permite o registo e consulta de **quartos**, **clientes**, **reservas**, **pagamentos** e **serviços adicionais**, com persistência de dados por serialização de objetos.

🧪 Desenvolvido para o **Laboratório #08**, da disciplina de **Engenharia de Software I** do **ISPTEC**.

## 📑 Índice

- [Objetivo](#objetivo)
- [Contexto académico](#contexto-académico)
- [Funcionalidades](#funcionalidades)
- [Regras de negócio](#regras-de-negócio)
- [Estrutura do repositório](#estrutura-do-repositório)
- [Requisitos](#requisitos)
- [Como executar](#como-executar)
- [Exemplo de execução](#exemplo-de-execução)
- [Limitações conhecidas](#limitações-conhecidas)
- [Integrantes](#integrantes)
- [Licença](#licença)

## 🎯 Objetivo

O Hotel Kwanza Palace pretende informatizar a gestão de quartos, clientes, reservas, pagamentos e serviços adicionais. O sistema será utilizado por rececionistas e pelo gestor do hotel para registo e consulta.

O projeto foca na aplicação prática de **Orientação a Objetos**, abrangendo:

- 📐 **Modelagem conceitual** — Modelo de Domínio e Diagrama de Classes UML
- 🏗️ **Arquitetura em camadas** — separação entre apresentação, lógica de negócio e modelo de domínio
- 💾 **Persistência de dados** — serialização de objetos Java para ficheiros
- 🧮 **Regras de cálculo** — preços, descontos, saldos e serviços adicionais com polymorfismo via enum

## 🏫 Contexto académico

| Campo | Valor |
|---|---|
| Instituição | Instituto Superior Politécnico de Tecnologias e Ciências (ISPTEC) |
| Departamento | Engenharia e Tecnologias |
| Curso | Licenciatura em Engenharia Informática |
| Cadeira | Engenharia de Software I |
| Laboratório | #08 — Sistema de Reservas de Hotel |
| Professor | Judson Paiva |
| Ano letivo | 2025/2026 |

## ⚙️ Funcionalidades

O sistema apresenta um menu interativo com três sub-menus:

### 🏠 Gestão de Quartos

| Opção | Descrição |
|---|---|
| `1` | Registar novo quarto (número automático, tipo, preço, capacidade) |
| `2` | Listar todos os quartos |
| `3` | Alterar estado do quarto (ATIVO, MANUNTENCAO, INATIVO) |

### 👤 Gestão de Clientes

| Opção | Descrição |
|---|---|
| `1` | Registar novo cliente (nome, documento, telefone, email) |
| `2` | Listar todos os clientes |

### 📋 Gestão de Reservas

| Opção | Descrição |
|---|---|
| `1` | Criar nova reserva (selecionar cliente, quarto, datas, hóspedes) |
| `2` | Listar todas as reservas |
| `3` | Confirmar reserva (quando saldo <= 0) |
| `4` | Cancelar reserva |
| `5` | Efectuar check-in |
| `6` | Efectuar check-out |
| `7` | Registar pagamento |
| `8` | Adicionar serviço adicional |

## 📏 Regras de negócio

### Quartos e tipos

| Tipo | Multiplicador de preço |
|---|---|
| STANDARD | 1.00x |
| DELUXE | 1.15x |
| SUITE | 1.30x |

### Serviços adicionais

| Forma de cobranca | Cálculo |
|---|---|
| POR_NOITE | `precoUnitario * noites` |
| FIXO | `precoUnitario` |
| POR_UNIDADE | `precoUnitario * quantidade` |

### Estados da reserva

```
CRIADA ──→ CONFIRMADA ──→ CHECKED_IN ──→ CHECKED_OUT
  │              │
  └──→ CANCELADA ←┘
```

- **CRIADA → CONFIRMADA:** quando `saldo <= 0` (total pago >= total da reserva)
- **CRIADA/CONFIRMADA → CANCELADA:** antes do check-in
- **CONFIRMADA → CHECKED_IN:** no dia do check-in
- **CHECKED_IN → CHECKED_OUT:** na devolução do quarto

### Pagamentos e saldo

- `totalReserva = valorHospedagem + soma(totalServicos)`
- `totalPago = soma(valorPago de pagamentos CONFIRMADOS)`
- `saldo = totalReserva - totalPago`
- Se `saldo <= 0`, a reserva pode ser confirmada

## 📁 Estrutura do repositório

```
.
├── src/
│   ├── app/
│   │   └── Main.java                           # Ponto de entrada e arranque
│   ├── exceptions/
│   │   └── DomainException.java                # Exceção de domínio
│   ├── interfaces/
│   │   └── ICalculadoraServico.java            # Contrato de cálculo de serviços
│   ├── model/
│   │   ├── entities/
│   │   │   ├── Cliente.java                    # Hóspede
│   │   │   ├── Quarto.java                     # Quarto do hotel
│   │   │   ├── Reserva.java                    # Reserva
│   │   │   ├── Pagamento.java                  # Pagamento associado à reserva
│   │   │   └── ServicoAdicional.java           # Serviço extra (café, lavandaria...)
│   │   └── enums/
│   │       ├── TipoQuarto.java                 # STANDARD, DELUXE, SUITE
│   │       ├── TipoServico.java                # PEQUENO_ALMOCO, LAVANDARIA, etc.
│   │       ├── MetodoPagamento.java            # DINHEIRO, TPA, TRANSFERENCIA
│   │       ├── FormaCobranca.java              # POR_NOITE, FIXO, POR_UNIDADE (Strategy)
│   │       ├── EstadoReserva.java              # CRIADA, CONFIRMADA, CANCELADA, etc.
│   │       ├── EstadoQuarto.java               # ATIVO, MANUNTENCAO, INATIVO
│   │       └── EstadoPagamento.java            # PENDENTE, CONFIRMADO, ESTORNADO
│   ├── service/
│   │   ├── ReservaService.java                 # Lógica de negócio de reservas
│   │   ├── FinanceiroService.java              # Cálculos financeiros
│   │   └── SerializacaoService.java            # Persistência por serialização
│   └── utils/
│       ├── Menus.java                          # Interface de consola (UI)
│       └── Validador.java                      # Validação de inputs
├── test/                                       # Testes unitários (a implementar)
├── data/                                       # Dados serializados
│   ├── clientes.ser
│   ├── quartos.ser
│   └── reservas.ser
├── build.xml                                   # Build NetBeans
├── nbproject/                                  # Configuração do NetBeans
├── manifest.mf
├── ENUNCIADO.TXT                               # Enunciado do laboratório
├── LICENSE
├── .gitignore
└── .gitattributes
```

## 🧰 Requisitos

- **JDK 24** ou superior (com `--enable-preview`)
- **NetBeans** (IDE de desenvolvimento)

## 🚀 Como executar

### Pelo NetBeans

1. Abra o projeto (`File > Open Project`).
2. Selecione `hotel-reservations-system` e clique em **Run** (ou pressione `F6`).
3. Navegue pelos sub-menus de quartos, clientes e reservas.

### Por linha de comando

A partir da raiz do repositório:

```bash
javac --enable-preview -encoding UTF-8 -d build/classes -sourcepath src src/app/Main.java
java --enable-preview -cp build/classes app.Main
```

> O programa espera encontrar os ficheiros de dados (`clientes.ser`, `quartos.ser`, `reservas.ser`) no diretório `data/`; execute a partir da raiz do projeto.

## 🖥️ Exemplo de execução

```
=====================================
   SISTEMA DE GESTAO HOTEL KWANZA
=====================================

[1] - Gestao de Quartos
[2] - Gestao de Clientes
[3] - Gestao de Reservas
[0] - Sair
>> 1

--- Gestao de Quartos ---
[1] - Registar Quarto
[2] - Listar Quartos
[3] - Alterar Estado do Quarto
[0] - Voltar
>> 1

Tipo de quarto:
[1] STANDARD (multiplicador: 1.00x)
[2] DELUXE  (multiplicador: 1.15x)
[3] SUITE    (multiplicador: 1.30x)
>> 3

Preco diario base (Kz): 45000
Capacidade maxima: 2

Quarto 101 registado com sucesso!
```

## ⚠️ Limitações conhecidas

| Limitação | Estado | Descrição |
|-----------|--------|-----------|
| Validação de datas incompleta | Corrigido | O `Validador.validarDatas()` verificava apenas o ano do check-out. Corrigido para validar ambos. |
| Sobreposição de reservas entre sessões | Corrigido | A lista `reservasConfirmadasExistentes` era local ao menu. Movida para persistir corretamente. |
| Construtor de `Cliente` silencioso | Corrigido | O construtor 4-arg não lançava exceção em campos inválidos. Corrigido para lançar `DomainException`. |
| Testes unitários pendentes | Pendente | O diretório `test/` encontra-se vazio. Será implementado em fase futura. |
| Serialização binária frágil | Pendente | Alterações na estrutura das classes quebram a deserialização de dados anteriores. |
| `Menus.java` com responsabilidades múltiplas | Pendente | A classe concentra UI, orquestração e parsing de input. Decomposição em fases futuras. |

## 👥 Integrantes

| Nome | Número de estudante |
|---|---|
| Isabel Marques | 20231832 |
| Jussana Paim | 20230132 |
| Norberto Cassoma | 20230873 |
| Oldmar Filindo | 20231359 |

## 📄 Licença

Distribuído sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE).

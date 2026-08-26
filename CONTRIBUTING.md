# 🤝 Contribuir — Hotel Kwanza Palace

Guia de contribuição para a equipa do projeto de Engenharia de Software I.

## 📑 Índice

- [Regras gerais](#regras-gerais)
- [Fluxo de trabalho](#fluxo-de-trabalho)
- [Convenções de código](#convenções-de-código)
- [Estrutura de commits](#estrutura-de-commits)
- [Revisão de código](#revisão-de-código)

## 📏 Regras gerais

1. **Nunca commitar código que não compile.** Antes de fazer commit, executar o projeto pelo NetBeans e garantir que não há erros de compilação.
2. **Nunca alterar a estrutura de pacotes** sem comunicar ao grupo.
3. **Nunca remover funcionalidade existente** sem autorização do grupo.
4. **Manter consistência** com o estilo de código já existente no projeto.
5. **Não commitar dados de teste** — os ficheiros `data/*.ser` contêm dados reais do sistema.

## 🔄 Fluxo de trabalho

### 1. Criar uma branch

```bash
git checkout -b feature/nome-da-feature
```

Nomes de branch devem seguir o padrão:

| Tipo | Formato | Exemplo |
|------|---------|---------|
| Nova funcionalidade | `feature/descricao` | `feature/relatorio-reservas` |
| Correção de bug | `fix/descricao` | `fix/validacao-datas` |
| Refatoração | `refactor/descricao` | `refactor/decompor-menus` |
| Documentação | `docs/descricao` | `docs/readme` |

### 2. Desenvolver

- Escrever código seguindo as convenções abaixo.
- Testar manualmente a funcionalidade antes de commitar.

### 3. Commit

```bash
git commit -m "tipo: descrição curta"
```

### 4. Push e merge

```bash
git push origin feature/nome-da-feature
```

Fazer merge na `main` após revisão por pelo menos um outro membro da equipa.

## 🎨 Convenções de código

### Nomenclatura

| Elemento | Formato | Exemplo |
|----------|---------|---------|
| Classes | PascalCase | `ReservaService` |
| Interfaces | I + PascalCase | `ICalculadoraServico` |
| Métodos | camelCase | `confirmarReserva()` |
| Variáveis | camelCase | `saldoPositivo` |
| Constantes | UPPER_SNAKE_CASE | `MINIMO_PAGAMENTO` |
| Enums | UPPER_SNAKE_CASE | `ESTADO_RESERVA` |
| Pacotes | lowercase | `model.entities` |

### Formatação

- Indentação: **4 espaços** (sem tabs)
- Chave de abertura: **mesma linha** da declaração
- Chave de fecho: **linha própria**
- Max 120 caracteres por linha (preferencial)
-_encoding: **UTF-8**

### Exemplo de estilo

```java
package model.entities;

import java.math.BigDecimal;

public class Quarto implements Serializable {

    private Integer numero;
    private TipoQuarto tipo;
    private BigDecimal precoDiarioBase;

    public Quarto(TipoQuarto tipo, BigDecimal precoDiarioBase, int capacidade) {
        if (precoDiarioBase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Preco diario deve ser maior que zero");
        }
        this.tipo = tipo;
        this.precoDiarioBase = precoDiarioBase;
    }

    public BigDecimal getPrecoDiarioBase() {
        return precoDiarioBase;
    }
}
```

### Regras adicionais

- Usar `BigDecimal` para todos os valores monetários (nunca `double` ou `float`).
- Entidades devem implementar `Serializable`.
- Validar dados nos construtores, lançando `DomainException` em caso de erro.
- Retornar listas como `Collections.unmodifiableList()` quando apropriado.
- Usar `System.err.println()` para mensagens de erro.

## 📝 Estrutura de commits

| Tipo | Descrição | Exemplo |
|------|-----------|---------|
| `feat` | Nova funcionalidade | `feat: adicionar relatorio de reservas` |
| `fix` | Correção de bug | `fix: validacao de datas no check-in` |
| `refactor` | Refatoração sem alterar comportamento | `extrair metodos de input em Menus` |
| `docs` | Documentação | `docs: atualizar README` |
| `style` | Formatação (sem alterar lógica) | `style: corrigir indentacao em Menus` |
| `test` | Adicionar testes | `test: testes para Validador` |

## 🔍 Revisão de código

Antes de fazer merge na `main`:

1. Verificar que o projeto compila sem erros.
2. Verificar que o fluxo principal funciona (menu completo).
3. Verificar se a persistência funciona (gravar, fechar, reabrir, dados mantidos).
4. Verificar se não há código morto ou imports desnecessários.

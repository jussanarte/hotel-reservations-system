/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import exceptions.DomainException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.entities.*;
import model.enums.*;
import service.FinanceiroService;
import service.ReservaService;
import service.SerializacaoService;

/**
 *
 * @author Isabel Marques, Jussana Paim, Norberto Cassoma, Oldmar Filindo
 */
public class Menus {

    static Scanner sc = new Scanner(System.in);

    public static void executarMenuPrincipal(List<Quarto> quartos, List<Cliente> clientes, List<Reserva> reservas, String fileQuartos, String fileReservas, String fileClientes) throws IOException {
        int opcao;
        do {

            System.out.println("=== SISTEMA DE GESTAO HOTELEIRA - KWANZA PALACE ===");
            System.out.println("[01] GERIR QUARTOS");
            System.out.println("[02] GERIR CLIENTES");
            System.out.println("[03] GERIR RESERVAS");
            System.out.println("[00] SAIR");
            System.out.print(">> ESCOLHA UMA OPCAO: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    Menus.executarMenuQuartos(quartos, fileQuartos);

                }
                case 2 -> {
                    Menus.executarMenuClientes(clientes, fileClientes);
                }
                case 3 -> {
                    Menus.executarMenuReservas(quartos, clientes, reservas, fileReservas);
                }
                case 0 -> {
                    System.out.println("SAINDO..");
                    System.exit(0);
                }
                default -> {
                    System.err.println("Opcao invalida. Tente novamente!");
                }

            }

        } while (opcao != 0);

    }

    public static void executarMenuQuartos(List<Quarto> quartos, String fileQuartos) throws IOException {
        int opcao;
        Quarto q = null;
        do {
            System.out.println("\n=== GESTOR DE QUARTOS ===");
            System.out.println("[01] CRIAR QUARTOS");
            System.out.println("[02] VER QUARTOS");
            System.out.println("[03] ATIVAR QUARTO");
            System.out.println("[04] FAZER MANUTENCAO NO QUARTO");
            System.out.println("[00] VOLTAR AO MENU PRINCIPAL");
            System.out.print(">> ESCOLHA UMA OPCAO: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    try {
                        sc.nextLine();
                        System.out.println("\nQUARTO: ");
                        System.out.print("Tipo (STANDARD, SUITE, DELUXE): ");
                        TipoQuarto tipo = TipoQuarto.valueOf(sc.nextLine().toUpperCase());
                        System.out.print("Preco Diario Base: ");
                        BigDecimal preco = sc.nextBigDecimal();
                        System.out.print("Capacidade do Quarto: ");
                        int capacidade = sc.nextInt();

                        q = new Quarto(tipo, preco, capacidade);
                        System.out.println();
                        quartos.add(q);

                    } catch (DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileQuartos, quartos);
                    }

                }
                case 2 -> {
                    if (quartos.isEmpty()) {
                        System.out.println("\n> Lista vazia!");
                    } else {
                        System.out.println("\nLista de Quartos");
                        for (Quarto qr : quartos) {
                            System.out.println(qr);
                        }

                    }
                }
                case 3 -> {
                    try {
                        if (quartos.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int numero = verQuartos(quartos);
                            for (Quarto qr : quartos) {
                                if ((!quartos.isEmpty()) && qr.getNumero().equals(numero)) {
                                    q = qr;
                                }
                            }
                            if (q != null) {
                                q.ativarQuarto();
                                System.out.println("Quarto #" + q.getNumero() + " ativo.");
                            }
                        }

                    } catch (DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileQuartos, quartos);
                    }

                }
                case 4 -> {
                    try {
                        if (quartos.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int numero = verQuartos(quartos);
                            for (Quarto qr : quartos) {
                                if ((!quartos.isEmpty()) && qr.getNumero().equals(numero)) {
                                    q = qr;
                                }
                            }
                            if (q != null) {
                                q.repararQuarto();
                                System.out.println("Quarto #" + q.getNumero() + " em manutencao.");
                            }

                        }

                    } catch (DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileQuartos, quartos);
                    }
                }
                case 0 -> {
                    System.out.println("Voltando...");
                    return;
                }
                default -> {
                    System.err.println("Opcao invalida. Tente novamente!");
                    System.out.println("");
                }
            }
        } while (opcao != 0);

    }

    public static void executarMenuReservas(List<Quarto> quartos, List<Cliente> clientes, List<Reserva> reservas, String fileReservas) throws IOException {
        int opcao;
        ReservaService rservice = new ReservaService();
        FinanceiroService fs = new FinanceiroService();
        Reserva r = null;
        Quarto q = null;
        Cliente c = null;
        do {
            System.out.println("\n=== GESTOR DE RESERVAS ===");
            System.out.println("[01] CRIAR RESERVA");
            System.out.println("[02] VER RESERVAS");
            System.out.println("[03] CONFIRMAR RESERVA");
            System.out.println("[04] CANCELAR RESERVA");
            System.out.println("[05] FAZER CHECKIN");
            System.out.println("[06] FAZER CHECKOUT");
            System.out.println("[07] ADICIONAR PAGAMENTO");
            System.out.println("[08] ADICIONAR SERVICOS ADICIONAIS");
            System.out.println("[00] VOLTAR AO MENU PRINCIPAL");
            System.out.print(">> ESCOLHA UMA OPCAO: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    try {
                        if (quartos.isEmpty()) {
                            System.err.println("> Nao ha quartos disponiveis para reservas!");
                        } else {
                            sc.nextLine();
                            System.out.println("\nRESERVA");
                            System.out.print("DATA DE CHECK-IN (dd/MM/yyyy): ");
                            LocalDate checkin = LocalDate.parse(sc.nextLine(), Reserva.getFmt());

                            System.out.print("DATA DE CHECK-OUT (dd/MM/yyyy): ");
                            LocalDate checkout = LocalDate.parse(sc.nextLine(), Reserva.getFmt());

                            Validador.validarDatas(checkin, checkout);

                            System.out.print("QUANTIDADE DE HOSPEDES: ");
                            int qtdHospedes = sc.nextInt();

                            System.out.println("\nQUARTO ASSOCIADO: ");
                            int numero = verQuartos(quartos);
                            for (Quarto qr : quartos) {
                                if ((!quartos.isEmpty()) && qr.getNumero().equals(numero)) {
                                    q = qr;
                                    break;
                                }
                            }

                            System.out.println("\nCLIENTE ASSOCIADO: ");
                            int valor = 1;
                            for (Cliente cl : clientes) {
                                System.out.println(valor + ": " + cl);
                                valor++;
                            }
                            System.out.print("Insira o numero do cliente: ");
                            numero = sc.nextInt();

                            if (numero < 1 || numero > clientes.size()) {
                                throw new DomainException("Numero de cliente invalido.");
                            }
                            c = clientes.get(numero - 1);

                            r = new Reserva(checkin, checkout, qtdHospedes, q, c);

                            reservas.add(r);
                        }
                    } catch (DomainException | InputMismatchException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }
                }
                case 2 -> {
                    if (reservas.isEmpty()) {
                        System.out.println("\n> Lista vazia!");
                    } else {
                        System.out.println("\nLista de Reservas");
                        for (Reserva rs : reservas) {
                            System.out.println(rs);
                        }
                    }
                }
                case 3 -> {
                    try {
                        if (reservas.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int id = Menus.verReservas(reservas);
                            for (Reserva rs : reservas) {
                                if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                                    r = rs;
                                }
                            }
                            sc.nextLine();
                            List<Reserva> confirmadas = new ArrayList<>();
                            for (Reserva existing : reservas) {
                                if (existing.getEstado() == EstadoReserva.CONFIRMADA || existing.getEstado() == EstadoReserva.CHECKED_IN) {
                                    confirmadas.add(existing);
                                }
                            }
                            rservice.confirmarReserva(r, confirmadas);
                        }

                    } catch (InputMismatchException | DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }

                }
                case 4 -> {
                    try {
                        if (reservas.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int id = Menus.verReservas(reservas);
                            for (Reserva rs : reservas) {
                                if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                                    r = rs;
                                }
                            }
                            sc.nextLine();
                            rservice.cancelarReserva(r);
                        }
                    } catch (InputMismatchException | DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }

                }

                case 5 -> {
                    try {
                        if (reservas.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int id = Menus.verReservas(reservas);
                            for (Reserva rs : reservas) {
                                if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                                    r = rs;
                                }
                            }
                            sc.nextLine();
                            rservice.realizarCheckIn(r);
                        }
                    } catch (InputMismatchException | DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }

                }
                case 6 -> {
                    try {
                        if (reservas.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int id = Menus.verReservas(reservas);
                            for (Reserva rs : reservas) {
                                if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                                    r = rs;
                                }
                            }
                            sc.nextLine();
                            rservice.realizarCheckOut(r);
                        }
                    } catch (InputMismatchException | DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }

                }
                case 7 -> {
                    try {
                        if (reservas.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int id = Menus.verReservas(reservas);
                            for (Reserva rs : reservas) {
                                if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                                    r = rs;
                                }
                            }
                            sc.nextLine();
                            System.out.println("\nPAGAMENTO");
                            System.out.println("Reserva #" + r.getCodReserva() + " - Valor total a pagar: " + (fs.calcularSaldo(r)));
                            System.out.print("Valor: ");
                            BigDecimal valor = sc.nextBigDecimal();
                            sc.nextLine();
                            System.out.print("Data do Pagamento (dd/MM/yyyy): ");
                            LocalDate dataPagamento = LocalDate.parse(sc.nextLine(), Reserva.getFmt());
                            System.out.print("Metodo de Pagamento (TRANSFERENCIA, TPA, DINHEIRO): ");
                            MetodoPagamento metodo = MetodoPagamento.valueOf(sc.nextLine().toUpperCase());

                            rservice.processarPagamento(r, new Pagamento(valor, dataPagamento, metodo));

                            System.out.println("Saldo: " + fs.calcularSaldo(r));
                        }

                    } catch (InputMismatchException | DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }

                }
                case 8 -> {
                    try {
                        if (reservas.isEmpty()) {
                            System.out.println("\n> Lista vazia!");
                        } else {
                            int id = Menus.verReservas(reservas);
                            for (Reserva rs : reservas) {
                                if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                                    r = rs;
                                }
                            }
                            sc.nextLine();
                            System.out.println("\nSERVICO");
                            System.out.print("Forma de Cobranca (FIXO, POR NOITE, POR UNIDADE): ");
                            FormaCobranca formaCobranca = FormaCobranca.valueOf(sc.nextLine().toUpperCase());
                            System.out.print("Tipo (LAVANDARIA, PEQUENO_ALMOCO, TRANSPORTE, OUTRO): ");
                            TipoServico tipo = TipoServico.valueOf(sc.nextLine().toUpperCase());
                            System.out.print("Descricao: ");
                            String descricao = sc.nextLine();
                            System.out.print("Preco Unitario: ");
                            BigDecimal preco = sc.nextBigDecimal();
                            System.out.print("Quantidade: ");
                            Integer quantidade = sc.nextInt();

                            rservice.adicionarServico(r, new ServicoAdicional(descricao, tipo, preco, quantidade, formaCobranca));
                            System.out.println("Total Servicos: " + fs.calcularTotalServicos(r)); 
                        }

                    } catch (InputMismatchException | DomainException e) {
                        System.err.println(e.getMessage());
                    } catch (RuntimeException e) {
                        System.err.println("Erro Inesperado: " + e.getMessage());
                    } finally {
                        SerializacaoService.gravar(fileReservas, reservas);
                    }
                }
                case 0 -> {
                    System.out.println("Voltando...");
                    return;
                }
                default -> {
                    System.err.println("Opcao invalida. Tente novamente!");
                }

            }
        } while (opcao != 0);
    }

    public static void executarMenuClientes(List<Cliente> clientes, String fileClientes) throws IOException {
        int opcao;
        do {
            System.out.println("\n=== GESTOR DE CLIENTES ===");
            System.out.println("[01] ADICIONAR CLIENTES");
            System.out.println("[02] VER CLIENTES");
            System.out.println("[00] VOLTAR AO MENU PRINCIPAL");
            System.out.print(">> ESCOLHA UMA OPCAO: ");
            opcao = sc.nextInt();
            Cliente c = null;

            switch (opcao) {
                case 1 -> {
                    sc.nextLine();
                    System.out.print("Nome Completo: ");
                    String nome = sc.nextLine();
                    System.out.print("Documento: ");
                    String documento = sc.nextLine();

                    c = new Cliente(nome, documento);
                    clientes.add(c);

                    SerializacaoService.gravar(fileClientes, clientes);
                }
                case 2 -> {
                    if (clientes.isEmpty()) {
                        System.out.println("\n> Lista vazia!");
                    } else {
                        System.out.println("\nLista de Clientes");
                        for (Cliente l : clientes) {
                            System.out.println(l);
                        }
                    }

                }
                case 0 -> {
                    System.out.println("Voltando...");
                    return;
                }

            }
        } while (opcao != 0);

    }

    private static int verReservas(List<Reserva> reservas) {
        for (Reserva rs : reservas) {
            System.out.println(rs.getCodReserva() + " - " + rs.getCliente().getNomeCompleto());
        }

        System.out.print("Insira o ID da reserva: ");
        Integer IDreserva = sc.nextInt();

        return IDreserva;

    }

    private static int verQuartos(List<Quarto> quartos) {
        for (Quarto q : quartos) {
            System.out.println(q);
        }
        System.out.print("Insira o numero do Quarto: ");
        Integer numero = sc.nextInt();

        return numero;
    }

}

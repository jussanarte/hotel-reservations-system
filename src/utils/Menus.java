/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.entities.*;
import model.enums.*;
import service.FinanceiroService;
import service.ReservaService;

/**
 *
 * @author juuhl
 */
public class Menus {

    ReservaService rservice = new ReservaService();
    FinanceiroService fs = new FinanceiroService();
    static Scanner sc = new Scanner(System.in);

    public static void executarMenuPrincipal(List<Quarto> quartos, List<Cliente> clientes, List<Reserva> reservas) {
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
                    Menus.executarMenuQuartos(quartos, clientes, reservas);
                }
                case 2 -> {
                    Menus.executarMenuClientes(quartos, clientes, reservas);
                }
                case 3 -> {
                    Menus.executarMenuReservas(quartos, clientes, reservas);
                }
                case 0 -> {
                    System.out.println("SAINDO..");
                }
                default -> {
                    System.out.println("Opcao invalida. Tente novamente!");
                }

            }
        } while (opcao != 0);

    }

    public static void executarMenuQuartos(List<Quarto> quartos, List<Cliente> clientes, List<Reserva> reservas) {
        int opcao;

        do {
            System.out.println("=== GESTOR DE QUARTOS ===");
            System.out.println("[01] CRIAR QUARTOS");
            System.out.println("[02] VER QUARTOS");
            System.out.println("[03] ATIVAR QUARTO");
            System.out.println("[04] DESATIVAR QUARTO");
            System.out.println("[00] VOLTAR AO MENU PRINCIPAL");
            System.out.print(">> ESCOLHA UMA OPCAO: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    sc.nextLine();
                    System.out.println("QUARTO: ");
                    System.out.print("Tipo (STANDARD, SUITE, DELUXE): ");
                    TipoQuarto tipo = TipoQuarto.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Preco Diario Base: ");
                    BigDecimal preco = sc.nextBigDecimal();
                    System.out.print("Capacidade do Quarto: ");
                    int capacidade = sc.nextInt();

                    Quarto q = new Quarto(tipo, preco, capacidade);
                    System.out.println();

                    quartos.add(q);
                }
                case 2 -> {
                    for (Quarto q : quartos) {
                        System.out.println(q);
                    }
                }
                case 3 -> {

                }
                case 0 -> {
                    Menus.executarMenuPrincipal(quartos, clientes, reservas);
                }
                default -> {
                    System.out.println("Opcao invalida. Tente novamente!");
                }
            }
        } while (opcao != 0);

    }

    public static void executarMenuReservas(List<Quarto> quartos, List<Cliente> clientes, List<Reserva> reservas) {
        int opcao;
        List<Reserva> reservasConfirmadasExistentes = new ArrayList();
        Reserva r = null;
        Quarto q = null;
        Cliente c = null;
        do {
            System.out.println("=== GESTOR DE RESERVAS ===");
            System.out.println("[01] CRIAR RESERVA");
            System.out.println("[02] VER RESERVAS");
            System.out.println("[03] CANCELAR RESERVA");
            System.out.println("[04] FAZER CHECKIN");
            System.out.println("[05] FAZER CHECKIN");
            System.out.println("[06] ADICIONAR PAGAMENTO");
            System.out.println("[07] ADICIONAR SERVICOS ADICIONAIS");
            System.out.println("[00] VOLTAR AO MENU PRINCIPAL");
            System.out.print(">> ESCOLHA UMA OPCAO: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                     sc.nextLine();
                    System.out.print("DATA DE CHECK-IN (dd/MM/yyyy): ");
                    LocalDate checkin = LocalDate.parse(sc.nextLine(), Reserva.getFmt());

                    System.out.print("DATA DE CHECK-OUT (dd/MM/yyyy): ");
                    LocalDate checkout = LocalDate.parse(sc.nextLine(), Reserva.getFmt());

                    System.out.print("QUANTIDADE DE HOSPEDES: ");
                    int qtdHospedes = sc.nextInt();

                    System.out.println("QUARTO ASSOCIADO: ");
                    int numero = verQuartos(quartos);
                    for (Quarto qr : quartos) {
                        if ((!quartos.isEmpty()) && qr.getNumero().equals(numero)) {
                            q = qr;
                        }
                    }

                    System.out.println("CLIENTE ASSOCIADO: ");
                    int valor = 1;
                    for (Cliente cl : clientes) {
                        System.out.println(valor + ": " + cl);
                        valor++;
                    }
                    System.out.print("Insira o numero do cliente: ");
                    numero = sc.nextInt();

                    c = clientes.get(numero - 1);
                    r = new Reserva(checkin, checkout, qtdHospedes, q, c);

                    reservas.add(r);
                }
                case 2 -> {
                    for (Reserva rs : reservas) {
                        System.out.println(rs);
                    }
                }
                case 3 -> {
                    int id = Menus.verReservas(reservas);
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                            r = rs;
                        }
                    }
                     sc.nextLine();

                }

                case 4 -> {
                    int id = Menus.verReservas(reservas);
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                            r = rs;
                        }
                    }
                     sc.nextLine();

                }
                case 5 -> {
                    int id = Menus.verReservas(reservas);
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                            r = rs;
                        }
                    }
                     sc.nextLine();
                }
                case 6 -> {
                    int id = Menus.verReservas(reservas);
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                            r = rs;
                        }
                    }
                     sc.nextLine();

                    System.out.println("PAGAMENTO: ");
                    System.out.print("Valor: ");
                    BigDecimal valorP = sc.nextBigDecimal();
                    sc.nextLine();
                    System.out.print("Data do Pagamento (dd/MM/yyyy): ");
                    LocalDate dataPagamento = LocalDate.parse(sc.nextLine(), Reserva.getFmt());
                    System.out.print("Metodo de Pagamento (TRANSFERENCIA, TPA, DINHEIRO): ");
                    MetodoPagamento metodo = MetodoPagamento.valueOf(sc.nextLine().toUpperCase());
                }
                case 7 -> {
                    int id = Menus.verReservas(reservas);
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(id)) {
                            r = rs;
                        }
                    }
                    sc.nextLine();
                    System.out.println("SERVICO: ");
                    System.out.print("Forma de Cobranca (FIXO, POR NOITE, POR UNIDADE): ");
                    FormaCobranca formaCobranca = FormaCobranca.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Tipo (LAVANDARIA, PEQUENO_ALMOCO, TRANSPORTE, OUTRO): ");
                    TipoServico tipoS = TipoServico.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Descricao: ");
                    String descricao = sc.nextLine();
                    System.out.print("Preco Unitario: ");
                    BigDecimal precoS = sc.nextBigDecimal();
                    System.out.print("Quantidade: ");
                    Integer quantidade = sc.nextInt();
                    
                    
                }
                case 0 -> {
                    Menus.executarMenuPrincipal(quartos, clientes, reservas);
                }
                default -> {
                    System.out.println("Opcao invalida. Tente novamente!");
                }

            }
        } while (opcao != 0);
    }

    public static void executarMenuClientes(List<Quarto> quartos, List<Cliente> clientes, List<Reserva> reservas) {
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
                }
                case 2 -> {
                    for (Cliente l : clientes) {
                        System.out.println(l);
                    }
                }
                case 0 -> {
                    Menus.executarMenuPrincipal(quartos, clientes, reservas);
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
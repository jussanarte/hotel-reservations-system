/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.entities.*;
import model.enums.*;
import service.FinanceiroService;
import service.ReservaService;
import service.SerializacaoService;

/**
 *
 * @author juuhl
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);

        final String fileNameCliente = "clientes.txt";
        final String fileNameQuarto = "quartos.txt";
        final String fileNameReserva = "reservas.txt";
        final String fileNamePagamento = "pagamentos.txt";
        final String fileNameServicoAdicional = "servicosAdicionais.txt";

        //CARREGAMENTO DOS DADOS ANTERIORES
        List<Reserva> reservas = SerializacaoService.carregarFicheiros(fileNameReserva);
        List<Cliente> clientes = SerializacaoService.carregarFicheiros(fileNameCliente);
        List<Quarto> quartos = SerializacaoService.carregarFicheiros(fileNameQuarto);

        List<ServicoAdicional> servicosAdicionais = SerializacaoService.carregarFicheiros(fileNameServicoAdicional);
        List<Pagamento> pagamentos = SerializacaoService.carregarFicheiros(fileNamePagamento);

        if (!quartos.isEmpty()) {
            int maiorId = 0;
            for (Quarto q : quartos) {
                if (q.getNumero() > maiorId) {
                    maiorId = q.getNumero();
                }
            }
            Quarto.sincronizarContador(maiorId + 1);
        }

        if (!reservas.isEmpty()) {
            int maiorId = 0;
            for (Reserva r : reservas) {
                if (r.getCodReserva() > maiorId) {
                    maiorId = r.getCodReserva();
                }
            }
            Reserva.sincronizarContador(maiorId + 1);
        }

        //MENU INICIAL
        ReservaService rservice = new ReservaService();
        FinanceiroService fs = new FinanceiroService();
        List<Reserva> reservasConfirmadasExistentes = new ArrayList();
        int opcao;
        do {
            System.out.println("=== SISTEMA DE GESTAO HOTELEIRA - KWANZA PALACE ===");
            System.out.println("[1] CRIAR RESERVA");
            System.out.println("[2] CANCELAR RESERVA");
            System.out.println("[3] ADICIONAR SERVICO ADICIONAL");
            System.out.println("[4] PROCESSAR PAGAMENTO");
            System.out.println("[5] VER FICHEIRO");
            System.out.println("[0] SAIR");
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

                    sc.nextLine();
                    System.out.println("CLIENTE: ");
                    System.out.print("Nome Completo: ");
                    String nome = sc.nextLine();
                    System.out.print("Documento: ");
                    String documento = sc.nextLine();

                    Cliente c = new Cliente(nome, documento);

                    System.out.print("DATA DE CHECK-IN (dd/MM/yyyy): ");
                    LocalDate checkin = LocalDate.parse(sc.nextLine(), Reserva.getFmt());

                    System.out.print("DATA DE CHECK-OUT (dd/MM/yyyy): ");
                    LocalDate checkout = LocalDate.parse(sc.nextLine(), Reserva.getFmt());

                    System.out.print("QUANTIDADE DE HOSPEDES: ");
                    int qtdHospedes = sc.nextInt();

                    Reserva r = new Reserva(checkin, checkout, qtdHospedes, q);
                    r.setCliente(c);

                    r.validar();
                    System.out.println(r);

                    quartos.add(q);
                    clientes.add(c);
                    reservas.add(r);

                    SerializacaoService.gravar(fileNameQuarto, quartos);
                    SerializacaoService.gravar(fileNameCliente, clientes);
                    SerializacaoService.gravar(fileNameReserva, reservas);
                }
                case 2 -> {
                    System.out.println();
                    System.out.println("RESERVAS:");
                    for (Reserva rs : reservas) {
                        System.out.println(rs.getCodReserva() + " - " + rs.getCliente().getNomeCompleto());
                    }

                    System.out.print("Insira o ID da reserva: ");
                    Integer IDreserva = sc.nextInt();
                    Reserva reservaP = null;
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(IDreserva)) {
                            reservaP = rs;
                        }
                    }

                    rservice.cancelarReserva(reservaP);

                    SerializacaoService.gravar(fileNameReserva, reservas);
                    SerializacaoService.gravar(fileNameServicoAdicional, servicosAdicionais);
                    SerializacaoService.gravar(fileNamePagamento, pagamentos);

                    System.out.println();
                }
                case 3 -> {
                    System.out.println();
                    System.out.println("RESERVAS:");
                    for (Reserva rs : reservas) {
                        System.out.println(rs.getCodReserva() + " - " + rs.getCliente().getNomeCompleto());
                    }

                    System.out.println();
                    System.out.print("Insira o ID da reserva: ");
                    Integer IDreserva = sc.nextInt();
                    Reserva reservaP = null;
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(IDreserva)) {
                            reservaP = rs;
                        }
                    }

                    if (reservaP != null) {
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

                        rservice.adicionarServico(reservaP, new ServicoAdicional(descricao, tipoS, precoS, quantidade, formaCobranca));

                        for (int i = 0; i < reservaP.getServicosAdicionais().size(); i++) {
                            servicosAdicionais.add(reservaP.getServicosAdicionais().get(i));
                        }

                    }

                    service.SerializacaoService.gravar(fileNameServicoAdicional, servicosAdicionais);
                    service.SerializacaoService.gravar(fileNameReserva, reservas);
                }
                case 4 -> {
                    System.out.println();
                    System.out.println("RESERVAS:");
                    for (Reserva rs : reservas) {
                        System.out.println(rs.getCodReserva() + " - " + rs.getCliente().getNomeCompleto());
                    }

                    System.out.print("Insira o ID da reserva: ");
                    Integer IDreserva = sc.nextInt();
                    Reserva reservaP = null;
                    for (Reserva rs : reservas) {
                        if ((!reservas.isEmpty()) && rs.getCodReserva().equals(IDreserva)) {
                            reservaP = rs;
                        }
                    }

                    if (reservaP != null) {
                        System.out.println();
                        System.out.println(reservaP);

                        System.out.println();
                        System.out.println("PAGAMENTO: ");
                        System.out.print("Valor: ");
                        BigDecimal valorP = sc.nextBigDecimal();
                        sc.nextLine();
                        System.out.print("Data do Pagamento (dd/MM/yyyy): ");
                        LocalDate dataPagamento = LocalDate.parse(sc.nextLine(), Reserva.getFmt());
                        System.out.print("Metodo de Pagamento (TRANSFERENCIA, TPA, DINHEIRO): ");
                        MetodoPagamento metodo = MetodoPagamento.valueOf(sc.nextLine().toUpperCase());

                        rservice.processarPagamento(reservaP, new Pagamento(valorP, dataPagamento, metodo));

                        System.out.println("Saldo: " + fs.calcularSaldo(reservaP));

                        for (int i = 0; i < reservaP.getPagamentos().size(); i++) {
                            pagamentos.add(reservaP.getPagamentos().get(i));
                        }

                    }

                    SerializacaoService.gravar(fileNamePagamento, pagamentos);
                    SerializacaoService.gravar(fileNameReserva, reservas);

                    System.out.println("");
                }
                case 5 -> {
                    System.out.println("=== Arquivo Cliente ===");
                    for (Cliente l : clientes) {
                        System.out.println(l);
                    }

                    System.out.println("== Arquivo Pagamentos ==");
                    for (Pagamento p : pagamentos) {
                        System.out.println(p);
                    }

                    System.out.println("== Arquivo Quartos ==");
                    for (Quarto q : quartos) {
                        System.out.println(q);
                    }

                    for (Reserva r : reservas) {
                        System.out.println(r);
                    }

                    System.out.println("== Arquivo Servicos Adicionais ==");
                    for (ServicoAdicional s : servicosAdicionais) {
                        System.out.println(s);
                    }

                }
                case 0 -> {
                    System.out.println("SAINDO..");
                }
            }

        } while (opcao != 0);

    }

}

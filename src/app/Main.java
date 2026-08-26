/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.entities.*;
import service.*;
import utils.Menus;

/**
 *
 * @author Isabel Marques, Jussana Paim, Norberto Cassoma, Oldmar Filindo
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
        final String fileNameCliente = "clientes.txt";
        final String fileNameQuarto = "quartos.txt";
        final String fileNameReserva = "reservas.txt";

        //CARREGAMENTO DOS DADOS ANTERIORES
        List<Reserva> reservas = SerializacaoService.carregarFicheiros(fileNameReserva);
        List<Cliente> clientes = SerializacaoService.carregarFicheiros(fileNameCliente);
        List<Quarto> quartos = SerializacaoService.carregarFicheiros(fileNameQuarto);

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
        Menus.executarMenuPrincipal(quartos, clientes, reservas, fileNameQuarto, fileNameReserva, fileNameCliente);
        

    

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Isabel Marques, Jussana Paim, Norberto Cassoma, Oldmar Filindo
 */
public interface ICalculadoraServico {
    BigDecimal calcular(BigDecimal precoUnitario, int quantidade, long noites);
}

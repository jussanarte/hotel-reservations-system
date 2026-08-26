/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.enums;
import java.math.BigDecimal;

/**
 *
 * @author Isabel Marques, Jussana Paim, Norberto Cassoma, Oldmar Filindo
 */
public enum TipoQuarto {
    STANDARD(new BigDecimal("1.00")), 
    DELUXE(new BigDecimal("1.15")), 
    SUITE(new BigDecimal("1.30"));

    private final BigDecimal multiplicador;
    TipoQuarto(BigDecimal m) { this.multiplicador = m; }
    public BigDecimal getMultiplicador() { return multiplicador; }
}
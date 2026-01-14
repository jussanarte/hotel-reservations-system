/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model.enums;

import interfaces.ICalculadoraServico;
import java.math.BigDecimal;

/**
 *
 * @author juuhl
 */
public enum FormaCobranca implements ICalculadoraServico{
    POR_NOITE{
        @Override
        public BigDecimal calcular(BigDecimal precoUnitario, int qtd, long noites) {
            return precoUnitario.multiply(BigDecimal.valueOf(noites));
        }
    },
    FIXO{
        @Override
        public BigDecimal calcular(BigDecimal precoUnitario, int qtd, long noites) {
            return precoUnitario;
        }
    }, 
    POR_UNIDADE{
        @Override
        public BigDecimal calcular(BigDecimal precoUnitario, int qtd, long noites) {
            return precoUnitario.multiply(BigDecimal.valueOf(qtd)); // [cite: 100]
        }
    };
    
    public ICalculadoraServico getCalculadora(){
        return this;
    }
}

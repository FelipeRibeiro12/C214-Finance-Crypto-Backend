package com.finance_crypto.service;

import com.finance_crypto.dto.RankingAtivoDTO;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RankingServiceTest {

    // Instancia o serviço que vamos testar
    RankingService rankingService = new RankingService();

    // TESTE 1: Cálculo de cenário de Lucro (Valorização)
    @Test
    void deveCalcularLucroCorretamenteQuandoPrecoAtualForMaior() {
        double precoCompra = 100.0;
        double precoAtual = 150.0;
        
        double resultado = rankingService.calcularPorcentagemLucro(precoCompra, precoAtual);
        
        assertEquals(50.0, resultado, "O lucro deveria ser de 50%");
    }

    // TESTE 2: Cálculo de cenário de Prejuízo (Desvalorização)
    @Test
    void deveCalcularPrejuizoCorretamenteQuandoPrecoAtualForMenor() {
        double precoCompra = 100.0;
        double precoAtual = 80.0;
        
        double resultado = rankingService.calcularPorcentagemLucro(precoCompra, precoAtual);
        
        assertEquals(-20.0, resultado, "O prejuízo deveria ser de -20%");
    }

    // TESTE 3: Tratamento de erro (Preço de compra zero para evitar divisão por zero)
    @Test
    void deveRetornarZeroQuandoPrecoCompraForInvalido() {
        double precoCompra = 0.0;
        double precoAtual = 150.0;
        
        double resultado = rankingService.calcularPorcentagemLucro(precoCompra, precoAtual);
        
        assertEquals(0.0, resultado, "Deve retornar 0 para evitar divisão por zero");
    }

    // TESTE 4: Validação do retorno da lista (Garantir que não vem vazia)
    @Test
    void deveRetornarListaDeRankingPreenchida() {
        List<RankingAtivoDTO> ranking = rankingService.obterRankingCalculado();
        
        assertNotNull(ranking, "A lista de ranking não pode ser nula");
        assertFalse(ranking.isEmpty(), "A lista de ranking não deve estar vazia");
    }
}
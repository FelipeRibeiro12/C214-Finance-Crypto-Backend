package com.finance_crypto.service;

import com.finance_crypto.dto.RankingAtivoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingServiceTest {

    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        rankingService = new RankingService();
    }

    //Teste de Fluxo Normal: Verifica se a lista traz exatamente os 3 ativos esperados
    @Test
    void deveRetornarListaComTresAtivos() {
        List<RankingAtivoDTO> resultado = rankingService.obterRankingCalculado();
        assertEquals(3, resultado.size(), "O ranking deve conter exatamente 3 ativos mockados.");
    }

    //Teste de Fluxo Normal: Verifica se os valores do Bitcoin (BTC) estão corretos
    @Test
    void deveRetornarBtcComoPrimeiroAtivoComDadosCorretos() {
        List<RankingAtivoDTO> resultado = rankingService.obterRankingCalculado();
        RankingAtivoDTO btc = resultado.get(0);

        // Obs: Ajuste o nome dos 'getters' caso estejam diferentes no seu RankingAtivoDTO
        assertEquals("BTC", btc.getNome());
        assertEquals(300000.0, btc.getValorAntigo());
        assertEquals(16.6, btc.getVariacao());
    }

    //Teste Inoportuno (Exceção): Garante que a lista não pode ser corrompida com novos itens
    @Test
    void deveLancarExcecaoAoTentarAdicionarNovoAtivoNoRanking() {
        List<RankingAtivoDTO> resultado = rankingService.obterRankingCalculado();
        RankingAtivoDTO ativoInvasor = new RankingAtivoDTO("ADA", 1.0, 1.5, 50.0);

        // Tentar dar um .add() numa lista gerada por Arrays.asList lança exceção nativa
        assertThrows(UnsupportedOperationException.class, () -> {
            resultado.add(ativoInvasor);
        }, "A lista do ranking deve ser estática e blindada contra adições.");
    }

    //Teste Inoportuno (Exceção): Garante que a lista não pode ter itens deletados
    @Test
    void deveLancarExcecaoAoTentarRemoverAtivoDoRanking() {
        List<RankingAtivoDTO> resultado = rankingService.obterRankingCalculado();

        assertThrows(UnsupportedOperationException.class, () -> {
            resultado.remove(0);
        }, "A lista do ranking deve ser estática e blindada contra remoções.");
    }
}
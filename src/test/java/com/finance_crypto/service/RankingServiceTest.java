package com.finance_crypto.service;

import com.finance_crypto.dto.RankingAtivoDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingServiceTest {

    private final RankingService rankingService = new RankingService();

    @Test
    void deveRetornarTresAtivosNoRanking() {
        List<RankingAtivoDTO> ranking = rankingService.obterRankingCalculado();

        assertNotNull(ranking);
        assertEquals(3, ranking.size());
    }

    @Test
    void deveRetornarBtcComoPrimeiroAtivo() {
        List<RankingAtivoDTO> ranking = rankingService.obterRankingCalculado();

        assertEquals("BTC", ranking.get(0).getSimbolo());
        assertEquals(16.6, ranking.get(0).getPercentualLucro(), 0.001);
    }

    @Test
    void deveConterAtivoComPrejuizo() {
        List<RankingAtivoDTO> ranking = rankingService.obterRankingCalculado();

        boolean existePrejuizo = ranking.stream()
                .anyMatch(ativo -> ativo.getPercentualLucro() < 0);

        assertTrue(existePrejuizo);
    }
}
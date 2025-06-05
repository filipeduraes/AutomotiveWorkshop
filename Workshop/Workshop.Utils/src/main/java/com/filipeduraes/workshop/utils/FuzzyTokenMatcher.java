package com.filipeduraes.workshop.utils;

import java.util.*;
import java.text.Normalizer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe utilitária que implementa busca difusa (fuzzy search) baseada em tokens.
 * Permite encontrar itens similares em uma coleção comparando strings de forma flexível,
 * considerando variações de escrita, acentuação e ordem das palavras.
 *
 * @author Filipe Durães
 */
public class FuzzyTokenMatcher
{
    private static final int EXACT_MATCH_SCORE = 3;
    private static final int PREFIX_SCORE = 2;
    private static final int APPROXIMATE_SCORE = 1;

    /**
     * Encontra itens em uma coleção que são similares a uma string de busca.
     * Utiliza processamento paralelo para melhor performance em grandes coleções.
     *
     * @param <T> Tipo dos itens na coleção
     * @param items Coleção de itens a serem pesquisados
     * @param search String de busca
     * @param minSimilarity Similaridade mínima (entre 0.0 e 1.0) para considerar um item como match
     * @param stringExtractor Função que extrai a string de comparação de cada item
     * @return Lista de itens encontrados, ordenados por similaridade (mais similares primeiro)
     */
    public static <T> List<T> findSimilarItems(Collection<T> items, String search, double minSimilarity, Function<T, String> stringExtractor)
    {
        return items.parallelStream()
            .map
            (item ->
                {
                    String value = stringExtractor.apply(item);
                    double score = fuzzyTokenSimilarity(search, value, minSimilarity);
                    return new ScoredItem<>(item, score);
                }
            )
            .filter(scored -> scored.score >= minSimilarity)
            .sorted(Comparator.comparingDouble((ScoredItem<T> s) -> s.score).reversed())
            .map(scored -> scored.item)
            .collect(Collectors.toList());
    }

    /**
     * Calcula a similaridade entre duas strings usando comparação por tokens.
     * O algoritmo divide as strings em tokens (palavras) e compara cada token usando:
     * 1. Comparação exata (score 1.0)
     * 2. Prefixo matching (score 0.9)
     * 3. Distância de Levenshtein para similaridade parcial
     *
     * @param a Primeira string para comparação
     * @param b Segunda string para comparação
     * @param minSimilarity Similaridade mínima para considerar tokens similares
     * @return Score de similaridade entre 0.0 e 1.0
     */
    public static double fuzzyTokenSimilarity(String a, String b, double minSimilarity)
    {
        List<String> tokensA = new ArrayList<>(tokenize(a));
        List<String> tokensB = new ArrayList<>(tokenize(b));

        int maxTokens = Math.max(tokensA.size(), tokensB.size());

        if (maxTokens == 0)
        {
            return 1.0;
        }

        double matchScore = 0;

        for (String tokenB : tokensB)
        {
            for (String tokenA : tokensA)
            {
                if (tokenA.equals(tokenB))
                {
                    matchScore += EXACT_MATCH_SCORE; // match exato
                    break;
                }
                else if (tokenB.startsWith(tokenA))
                {
                    matchScore += PREFIX_SCORE; // prefixo
                    break;
                }
                else if (Levenshtein.calculateSimilarity(tokenA, tokenB) >= minSimilarity)
                {
                    matchScore += APPROXIMATE_SCORE; // match aproximado
                    break;
                }
            }
        }

        return matchScore / tokensB.size();
    }

    /**
     * Converte uma string em um conjunto de tokens normalizados.
     * O processo inclui:
     * 1. Normalização de caracteres Unicode
     * 2. Remoção de acentos e caracteres não-ASCII
     * 3. Conversão para minúsculas
     * 4. Remoção de caracteres especiais
     * 5. Divisão em palavras únicas
     *
     * @param input String a ser tokenizada
     * @return Conjunto de tokens únicos normalizados
     */
    private static Set<String> tokenize(String input)
    {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String asciiOnly = normalized.replaceAll("[^\\p{ASCII}]", "");
        String cleaned = asciiOnly.toLowerCase().replaceAll("[^a-z0-9 ]", "");
        String[] tokens = cleaned.split("\\s+");

        return new HashSet<>(Arrays.asList(tokens));
    }

    /**
     * Classe interna que associa um item com sua pontuação de similaridade.
     * Utilizada para ordenar os resultados da busca.
     *
     * @param <T> Tipo do item armazenado
     */
    private static class ScoredItem<T>
    {
        private final T item;
        private final double score;

        private ScoredItem(T item, double score)
        {
            this.item = item;
            this.score = score;
        }
    }
}
package com.filipeduraes.workshop.utils;

/**
 * Implementa o algoritmo de Levenshtein para calcular a similaridade entre duas strings.
 * Este algoritmo mede a diferença entre duas sequências de caracteres calculando o
 * número mínimo de operações de edição necessárias para transformar uma string em outra.
 *
 * @author Filipe Durães
 */
public class Levenshtein
{
    /**
     * Calcula a similaridade entre duas strings usando o algoritmo de Levenshtein.
     * O resultado é um valor entre 0 e 1, onde 1 significa strings idênticas e
     * 0 significa strings completamente diferentes.
     *
     * @param a Primeira string a ser comparada
     * @param b Segunda string a ser comparada
     * @return Valor entre 0 e 1 representando a similaridade entre as strings
     */
    public static double calculateSimilarity(String a, String b)
    {
        int maxLength = Math.max(a.length(), b.length());

        if (maxLength == 0)
        {
            return 1.0;
        }

        int distance = calculateDistance(a, b);
        return 1.0 - ((double) distance / maxLength);
    }

    /**
     * Calcula a distância de Levenshtein entre duas strings.
     * A distância representa o número mínimo de operações (inserção, deleção ou substituição)
     * necessárias para transformar a string fonte na string alvo.
     *
     * @param source String fonte para comparação
     * @param target String alvo para comparação
     * @return Número inteiro representando a distância de Levenshtein entre as strings
     */
    public static int calculateDistance(String source, String target)
    {
        int sourceLength = source.length();
        int targetLength = target.length();

        int[][] distanceMatrix = new int[sourceLength + 1][targetLength + 1];

        for (int i = 0; i <= sourceLength; i++)
        {
            distanceMatrix[i][0] = i;
        }

        for (int j = 0; j <= targetLength; j++)
        {
            distanceMatrix[0][j] = j;
        }

        for (int row = 1; row <= sourceLength; row++)
        {
            char sourceChar = source.charAt(row - 1);

            for (int col = 1; col <= targetLength; col++)
            {
                char targetChar = target.charAt(col - 1);

                int substitutionCost = (sourceChar == targetChar) ? 0 : 1;

                int deletion = distanceMatrix[row - 1][col] + 1;
                int insertion = distanceMatrix[row][col - 1] + 1;
                int substitution = distanceMatrix[row - 1][col - 1] + substitutionCost;

                distanceMatrix[row][col] = Math.min(Math.min(deletion, insertion), substitution);
            }
        }

        return distanceMatrix[sourceLength][targetLength];
    }
}
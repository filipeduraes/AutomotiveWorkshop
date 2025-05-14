// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Responsável por gerenciar a persistência de dados da aplicação.
 * Fornece métodos para salvar e carregar dados em arquivos, com suporte opcional à ofuscação.
 *
 * @author Filipe Durães
 */
public final class Persistence
{
    private static boolean UseObfuscation = false;
    private static final byte Key = 12;
    private static Gson gson = new Gson();

    private Persistence()
    {
    } //Pure static class

    /**
     * Define se a ofuscação deve ser usada ao salvar e carregar arquivos.
     *
     * @param useObfuscation true para ativar ofuscação, false para desativar
     */
    public static void setUseObfuscation(boolean useObfuscation)
    {
        UseObfuscation = useObfuscation;
    }

    /**
     * Registra adaptadores personalizados para serialização de tipos específicos.
     *
     * @param adapters lista de grupos de adaptadores de serialização
     */
    public static void registerCustomSerializationAdapters(List<SerializationAdapterGroup> adapters)
    {
        GsonBuilder builder = new GsonBuilder();

        for (SerializationAdapterGroup group : adapters)
        {
            builder.registerTypeAdapter(group.getType(), group.getSerializer());
            builder.registerTypeAdapter(group.getType(), group.getDeserializer());
        }

        gson = builder.create();
    }

    /**
     * Salva dados em um arquivo no caminho especificado.
     *
     * @param <T> tipo dos dados a serem salvos
     * @param data dados a serem salvos
     * @param path caminho do arquivo
     */
    public static <T> void saveFile(T data, String path)
    {
        try
        {
            path = appendPathSuffix(path);
            ensureUsersDirectoriesAndFileExists(Path.of(path));
            FileWriter fileWriter = new FileWriter(path);

            String json = gson.toJson(data);
            String obfuscatedJson = UseObfuscation ? obfuscate(json) : json;

            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
            {
                bufferedWriter.write(obfuscatedJson);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace(System.out);
        }
    }

    /**
     * Carrega dados de um arquivo no caminho especificado.
     *
     * @param <T> tipo dos dados a serem carregados
     * @param path caminho do arquivo
     * @param type tipo parametrizado para deserialização
     * @param defaultValue valor padrão caso ocorra erro
     * @return dados carregados ou valor padrão em caso de erro
     */
    public static <T> T loadFile(String path, ParameterizedType type, T defaultValue)
    {
        try
        {
            path = appendPathSuffix(path);
            Path filePath = Path.of(path);
            ensureUsersDirectoriesAndFileExists(filePath);
            String obfuscatedUsers = Files.readString(filePath, StandardCharsets.UTF_8);

            String json = UseObfuscation ? deobfuscate(obfuscatedUsers) : obfuscatedUsers;
            T result = gson.fromJson(json, type);

            return result == null ? defaultValue : result;
        }
        catch (IOException exception)
        {
            exception.printStackTrace(System.out);
            return defaultValue;
        }
    }

    /**
     * Gera um UUID único que não existe no mapa fornecido.
     *
     * @param <T> tipo dos valores no mapa
     * @param idMap mapa de IDs existentes
     * @return UUID único gerado
     */
    public static <T> UUID generateUniqueID(Map<UUID, T> idMap)
    {
        UUID uniqueID = UUID.randomUUID();

        while (idMap.containsKey(uniqueID))
        {
            uniqueID = UUID.randomUUID();
        }

        return uniqueID;
    }

    /**
     * Cria um tipo parametrizado para uso em serialização/deserialização.
     *
     * @param raw classe base
     * @param args tipos dos parâmetros genéricos
     * @return tipo parametrizado criado
     */
    public static ParameterizedType createParameterizedType(Class<?> raw, Type... args)
    {
        return new ParameterizedType()
        {
            @Override
            public Type[] getActualTypeArguments()
            {
                return args;
            }

            @Override
            public Type getRawType()
            {
                return raw;
            }

            @Override
            public Type getOwnerType()
            {
                return null;
            }
        };
    }

    private static String appendPathSuffix(String path)
    {
        int extensionIndex = path.lastIndexOf(".");

        final String pathWithoutExtension = path.substring(0, extensionIndex);
        final String pathSuffix = UseObfuscation ? "_Obfuscated" : "_Regular";
        final String pathExtension = path.substring(extensionIndex, path.length());
        return pathWithoutExtension + pathSuffix + pathExtension;
    }

    private static void ensureUsersDirectoriesAndFileExists(Path path) throws IOException
    {
        Path directoryPath = path.getParent();

        if (!Files.exists(directoryPath))
        {
            Files.createDirectories(directoryPath);
        }

        if (!Files.exists(path))
        {
            Files.createFile(path);
        }
    }

    private static String obfuscate(String text)
    {
        byte[] buffer = text.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < buffer.length; i++)
        {
            buffer[i] ^= Key;
        }

        return Base64.getEncoder().encodeToString(buffer);
    }

    private static String deobfuscate(String base64)
    {
        byte[] buffer = Base64.getDecoder().decode(base64);

        for (int i = 0; i < buffer.length; i++)
        {
            buffer[i] ^= Key;
        }

        return new String(buffer, StandardCharsets.UTF_8);
    }
}

// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.console;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.catalog.ProductCatalog;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.client.ClientEmailComparator;
import com.filipeduraes.workshop.core.client.ClientNameComparator;
import com.filipeduraes.workshop.core.employee.AuthModule;
import com.filipeduraes.workshop.core.employee.EmployeeRole;
import com.filipeduraes.workshop.core.employee.LocalEmployee;
import com.filipeduraes.workshop.core.financial.Sale;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceOrderModule;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Classe de testes que demonstra funcionalidades específicas do sistema da oficina.
 * Implementa exemplos práticos de uso das principais funcionalidades, incluindo
 * iteração, ordenação, busca e operações de CRUD completas.
 *
 * @author Filipe Durães
 */
public class TestQuestions
{
    private final Workshop testWorkshop = new Workshop(false);

    /**
     * Executa todos os testes demonstrativos do sistema.
     * Inclui exemplos de iteração, ordenação, busca e operações completas de CRUD.
     */
    public void test()
    {
        CrudRepository<Client> clientRepository = testWorkshop.getClientRepository();
        List<Client> allClients = clientRepository.getAllEntities();

        showTitle("Questao 15");

        // Questão 15
        {

            Iterator<Client> allClientsIterator = allClients.iterator();

            while (allClientsIterator.hasNext())
            {
                Client client = allClientsIterator.next();
                System.out.printf("Cliente: %s%n", client.getName());
            }

            for(Client client : allClients)
            {
                System.out.printf("Cliente: %s%n", client.getName());
            }

            /*
            O Iterator, é um padrão de projeto que tem como principal objetivo
            esconder como percorrer uma coleção, enquanto expõe métodos para
            fazer essa iteração. No Java, ele é implementado usando a interface
            Iterator, que toda coleção tem, já que Collection implementa a
            interface Iterable, que retorna pela função iterator(), a interface
            Iterator<T> do tipo do elemento daquela coleção. Nessa interface há
            duas funções:
                - hasNext: retorna um booleano dizendo se ainda dá para percorrer.
                - next: retorna o elemento atual e move o ponteiro para o próximo.
             Assim, é possível percorrer todos os tipos de coleções usando essa
             interface, sem precisar saber da implementação.
             O foreach é uma ferramenta da linguagem que já chama essa estrutura
             toda do iterator de forma mais simplificada e fácil de ler.
             */
        }

        showTitle("Questao 16");

        // Questão 16
        {
            Collections.sort(allClients, new ClientNameComparator());

            System.out.println("Clientes ordenados por nome:");

            for(Client client : allClients)
            {
                System.out.printf("Cliente: %s%n", client.getName());
            }

            Collections.sort(allClients, new ClientEmailComparator());

            System.out.println("Clientes ordenados por email:");

            for(Client client : allClients)
            {
                System.out.printf("Cliente: %s%n", client.getEmail());
            }
        }

        showTitle("Questao 17");

        // Questão 17
        {
            clientRepository.registerEntity(new Client("Eduardo Pelli", "pellie@ufvjm.edu.br", "9999999999", "Rua do Eduardo", "123456789"));
            allClients = clientRepository.getAllEntities();

            // Implementação da função find com foreach
            Client foundClientFirstCase = clientRepository.findFirstEntityWithPredicate(client -> client.getName().equals("Eduardo Pelli"));
            System.out.printf("Cliente encontrado (Primeiro caso): %s%n", foundClientFirstCase);

            // Implementação da função find com binary search
            Collections.sort(allClients, new ClientNameComparator());
            int foundClientIndex = Collections.binarySearch(allClients, foundClientFirstCase, new ClientNameComparator());

            System.out.printf("Cliente encontrado (Segundo caso): %s%n", allClients.get(foundClientIndex));

            /*
            Uma implementação com binary search geralmente vai ser mais rápida do que uma simples iteração.
            E isso só é possível de fazer com objetos complexos usando comparators, já que eles não possuem
            uma ordem natural de comparação. Assim, definindo se dois elementos são (<, ==, >) entre si,
            conseguimos ordenar a coleção e fazer pesquisas utilizando-se dessa ordem.
             */
        }

        showTitle("Questao 18");

        // Questão 18
        {
            Random random = new Random();

            // Cadastro de funcionários
            int fistUserPasswordHash = "123456".hashCode();
            int secondUserPasswordHash = "789101".hashCode();

            LocalEmployee firstUser = new LocalEmployee("Usuario 01", "email01@gmail.com", EmployeeRole.MECHANIC, fistUserPasswordHash);
            LocalEmployee secondUser = new LocalEmployee("Usuario 02", "email02@gmail.com", EmployeeRole.SPECIALIST_MECHANIC, secondUserPasswordHash);
            AuthModule authModule = testWorkshop.getAuthModule();

            authModule.registerUser(firstUser);
            authModule.registerUser(secondUser);

            // Login de funcionário
            authModule.tryLogIn(firstUser.getEmail(), fistUserPasswordHash);

            for(int i = 0; i < 10; i++)
            {
                // Cadastrar Cliente
                String name = String.format("Cliente %d", i);
                String email = String.format("email%d@gmail.com", i);
                String phoneNumber = generateRandomNumberWithSize(random, 11);
                String address = String.format("Casa do cliente %d", i);
                String cpf = generateRandomNumberWithSize(random, 11);

                Client client = new Client(name, email, phoneNumber, address, cpf);
                UUID clientID = clientRepository.registerEntity(client);

                System.out.printf("Cliente %d registrado com ID: %s%n", i, clientID);


                // Cadastrar Veículo
                String model = String.format("Modelo %d", i);
                int year = random.nextInt(1950, 2025);
                String vinNumber = generateRandomNumberWithSize(random, 17);
                String licensePlate = String.format("ABC-%s", generateRandomNumberWithSize(random, 4));

                Vehicle vehicle = new Vehicle(clientID, model, "Verde-Claro", vinNumber, licensePlate, year);

                CrudRepository<Vehicle> vehicleRepository = testWorkshop.getVehicleRepository();
                UUID vehicleID = vehicleRepository.registerEntity(vehicle);

                System.out.printf("Veiculo %d registrado com ID: %s%n", i, vehicleID);


                // Iniciar Ordem de Serviço
                String shortDescription = String.format("Veiculo %d esta com problema", i);
                String detailedDescription = String.format("Veiculo %d esta com problema com uma descricao mais detalhada", i);

                ServiceOrderModule maintenanceModule = testWorkshop.getMaintenanceModule();
                UUID serviceOrderID = maintenanceModule.registerNewAppointment(clientID, vehicleID, shortDescription, detailedDescription);


                // Cadastrar Produto Utilizado
                String itemName = String.format("Item usado em %d", i);
                String itemDescription = String.format("Esse item e muito especial pois foi usado na manutencao de %d", i);
                int stockAmount = random.nextInt(2, 20);
                BigDecimal price = BigDecimal.valueOf(random.nextFloat(2.0f, 100.0f));

                StoreItem usedItem = new StoreItem(itemName, itemDescription, price, stockAmount);

                ProductCatalog catalog = testWorkshop.getStore().getCatalog();
                UUID itemID = catalog.getStoreItemsRepository().registerEntity(usedItem);


                // Cadastrar Venda do Produto Utilizado
                int usedItemsAmount = random.nextInt(1, stockAmount);
                Sale itemSale = testWorkshop.getStore().registerSale(itemID, usedItemsAmount);


                // Cadastrar Serviço Prestado
                String serviceName = String.format("Servico prestado para %d", i);
                String serviceDescription = String.format("Esse servico e muito especial pois foi feito na manutencao de %d", i);
                BigDecimal servicePrice = BigDecimal.valueOf(random.nextFloat(2.0f, 100.0f));

                PricedItem service = new PricedItem(serviceName, serviceDescription, servicePrice);
                CrudRepository<PricedItem> servicesRepository = catalog.getServicesRepository();
                servicesRepository.registerEntity(service);


                // Iniciar Inspeção
                maintenanceModule.startInspection(serviceOrderID);


                // Finalizar Inspeção
                String shortInspectionDescription = String.format("Deu problema em %d", i);
                String detailedInspectionDescription = String.format("Deu problema mesmo detalhadamente em %d", i);
                maintenanceModule.finishInspection(serviceOrderID, secondUser.getID(), shortInspectionDescription, detailedInspectionDescription);


                // Iniciar Manutenção
                maintenanceModule.startMaintenance(serviceOrderID);


                // Cadastrar Peças Utilizadas
                ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(serviceOrderID);
                serviceOrder.registerSale(itemSale);


                // Cadastrar Serviços Prestados
                serviceOrder.registerService(service);


                // Finalizar Manutenção
                maintenanceModule.finishMaintenance(serviceOrderID, shortDescription, detailedDescription);

                System.out.printf("Ordem de servico finalizada com valor total de %s%n", TextUtils.formatPrice(serviceOrder.getTotalPrice()));
            }
        }
    }

    private static void showTitle(String title)
    {
        StringBuilder builder = new StringBuilder();
        TextUtils.appendSectionTitle(builder, title);
        System.out.println(builder);
    }

    private String generateRandomNumberWithSize(Random random, int size)
    {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < size; i++)
        {
            builder.append(random.nextInt(10));
        }

        return builder.toString();
    }
}

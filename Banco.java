import java.io.*;
import java.util.*;

public class Banco {
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Gerente> gerentes = new ArrayList<>();
    private static Diretor diretor;

    public static void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Dados.txt"))) {
            writer.write("Diretor:admin,123\n");
            for (Gerente gerente : gerentes) {
                writer.write("Gerente:" + gerente.getCpf() + "," + gerente.getSenha() + "\n");
            }
            for (Cliente cliente : clientes) {
                writer.write("Cliente:" + cliente.getCpf() + "," + cliente.getSenha() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Dados.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    System.out.println("Linha mal formatada: " + line);
                    continue;
                }
                String role = parts[0];
                String[] credentials = parts[1].split(",");
                if (credentials.length != 2) {
                    System.out.println("Credenciais mal formatadas: " + line);
                    continue;
                }
                String cpf = credentials[0];
                String senha = credentials[1];

                switch (role) {
                    case "Diretor":
                        diretor = new Diretor("Admin", "Endereço Admin", "00000000", cpf, "0000-0000", "000", senha);
                        System.out.println("Diretor carregado: " + cpf);
                        break;
                    case "Gerente":
                        Gerente gerente = new Gerente("Gerente Nome", "Endereço Gerente", "12345678", cpf, "1111-1111", "G001", senha);
                        gerentes.add(gerente);
                        System.out.println("Gerente carregado: " + cpf);
                        break;
                    case "Cliente":
                        Cliente cliente = new Cliente("Cliente Nome", "Endereço Cliente", "87654321", cpf, "2222-2222", null, senha);
                        clientes.add(cliente);
                        System.out.println("Cliente carregado: " + cpf);
                        break;
                    default:
                        System.out.println("Role desconhecido: " + role);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inicializarDados() {
        diretor = new Diretor("Admin", "Endereço Admin", "00000000", "admin", "0000-0000", "000", "123");

        Gerente gerente = new Gerente("Gerente Nome", "Endereço Gerente", "12345678", "11111111111", "1111-1111", "G001", "123");
        gerentes.add(gerente);

        Cliente cliente = new Cliente("Cliente Nome", "Endereço Cliente", "87654321", "22222222222", "2222-2222", gerente, "123");
        clientes.add(cliente);

        salvarDados();
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static List<Gerente> getGerentes() {
        return gerentes;
    }

    public static Diretor getDiretor() {
        return diretor;
    }
}

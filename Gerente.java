import java.util.ArrayList;
import java.util.List;

public class Gerente extends Funcionario {
    private List<Cliente> clientes;

    public Gerente(String nome, String endereco, String rg, String cpf, String telefone, String matricula, String senha) {
        super(nome, endereco, rg, cpf, telefone, matricula, senha);
        this.clientes = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void cadastrarConta(Cliente cliente, Conta conta) {
        cliente.getContas().add(conta);
    }
}

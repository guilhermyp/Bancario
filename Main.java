import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Inicializa os dados se o arquivo não existir
        File file = new File("Dados.txt");
        if (!file.exists()) {
            System.out.println("Arquivo não encontrado. Inicializando dados.");
            Banco.inicializarDados();
        } else {
            System.out.println("Carregando dados do arquivo.");
            Banco.carregarDados();
        }

        JFrame frame = new JFrame("Sistema de Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel lblUsername = new JLabel("CPF:");
        JTextField txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Senha:");
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        frame.add(lblUsername);
        frame.add(txtUsername);
        frame.add(lblPassword);
        frame.add(txtPassword);
        frame.add(new JLabel()); // Empty cell
        frame.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = txtUsername.getText();
                String senha = new String(txtPassword.getPassword());
                System.out.println("Tentando logar com CPF: " + cpf + " e Senha: " + senha);

                if (Banco.getDiretor() != null && Banco.getDiretor().getCpf().equals(cpf) && Banco.getDiretor().getSenha().equals(senha)) {
                    System.out.println("Login como Diretor bem-sucedido.");
                    mostrarMenuDiretor();
                } else {
                    Gerente gerente = Banco.getGerentes().stream().filter(g -> g.getCpf().equals(cpf) && g.getSenha().equals(senha)).findFirst().orElse(null);
                    if (gerente != null) {
                        System.out.println("Login como Gerente bem-sucedido.");
                        mostrarMenuGerente(gerente);
                    } else {
                        Cliente cliente = Banco.getClientes().stream().filter(c -> c.getCpf().equals(cpf) && c.getSenha().equals(senha)).findFirst().orElse(null);
                        if (cliente != null) {
                            System.out.println("Login como Cliente bem-sucedido.");
                            mostrarMenuCliente(cliente);
                        } else {
                            System.out.println("CPF ou senha incorretos.");
                            JOptionPane.showMessageDialog(frame, "CPF ou senha incorretos.");
                        }
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private static void mostrarMenuDiretor() {
        JFrame frame = new JFrame("Menu do Diretor");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JButton btnCadastrarGerente = new JButton("Cadastrar Gerente");
        JButton btnExcluirGerente = new JButton("Excluir Gerente");

        frame.add(btnCadastrarGerente);
        frame.add(btnExcluirGerente);

        btnCadastrarGerente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarGerente();
            }
        });

        btnExcluirGerente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirGerente();
            }
        });

        frame.setVisible(true);
    }

    private static void cadastrarGerente() {
        JFrame frame = new JFrame("Cadastrar Gerente");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 2));

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblEndereco = new JLabel("Endereço:");
        JTextField txtEndereco = new JTextField();
        JLabel lblRg = new JLabel("RG:");
        JTextField txtRg = new JTextField();
        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField();
        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JTextField txtSenha = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        frame.add(lblNome);
        frame.add(txtNome);
        frame.add(lblEndereco);
        frame.add(txtEndereco);
        frame.add(lblRg);
        frame.add(txtRg);
        frame.add(lblCpf);
        frame.add(txtCpf);
        frame.add(lblTelefone);
        frame.add(txtTelefone);
        frame.add(lblSenha);
        frame.add(txtSenha);
        frame.add(new JLabel()); // Empty cell
        frame.add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String endereco = txtEndereco.getText();
                String rg = txtRg.getText();
                String cpf = txtCpf.getText();
                String telefone = txtTelefone.getText();
                String senha = txtSenha.getText();
                String matricula = "G" + (Banco.getGerentes().size() + 1);

                Gerente gerente = new Gerente(nome, endereco, rg, cpf, telefone, matricula, senha);
                Banco.getDiretor().cadastrarGerente(gerente);
                Banco.salvarDados();
                JOptionPane.showMessageDialog(frame, "Gerente cadastrado com sucesso!");
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private static void excluirGerente() {
        JFrame frame = new JFrame("Excluir Gerente");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));

        JComboBox<String> cbGerentes = new JComboBox<>();
        for (Gerente gerente : Banco.getGerentes()) {
            cbGerentes.addItem(gerente.getNome() + " - " + gerente.getCpf());
        }
        JButton btnExcluir = new JButton("Excluir");

        frame.add(cbGerentes);
        frame.add(btnExcluir);

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cbGerentes.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Gerente gerente = Banco.getGerentes().get(selectedIndex);
                    Banco.getDiretor().excluirGerente(gerente);
                    Banco.salvarDados();
                    JOptionPane.showMessageDialog(frame, "Gerente excluído com sucesso!");
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private static void mostrarMenuGerente(Gerente gerente) {
        JFrame frame = new JFrame("Menu do Gerente");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        JButton btnCadastrarConta = new JButton("Cadastrar Conta");
        JButton btnListarClientes = new JButton("Listar Clientes");

        frame.add(btnCadastrarCliente);
        frame.add(btnCadastrarConta);
        frame.add(btnListarClientes);

        btnCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente(gerente);
            }
        });

        btnCadastrarConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarConta(gerente);
            }
        });

        btnListarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarClientes(gerente);
            }
        });

        frame.setVisible(true);
    }

    private static void cadastrarCliente(Gerente gerente) {
        JFrame frame = new JFrame("Cadastrar Cliente");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 2));

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblEndereco = new JLabel("Endereço:");
        JTextField txtEndereco = new JTextField();
        JLabel lblRg = new JLabel("RG:");
        JTextField txtRg = new JTextField();
        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField();
        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JTextField txtSenha = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        frame.add(lblNome);
        frame.add(txtNome);
        frame.add(lblEndereco);
        frame.add(txtEndereco);
        frame.add(lblRg);
        frame.add(txtRg);
        frame.add(lblCpf);
        frame.add(txtCpf);
        frame.add(lblTelefone);
        frame.add(txtTelefone);
        frame.add(lblSenha);
        frame.add(txtSenha);
        frame.add(new JLabel()); // Empty cell
        frame.add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String endereco = txtEndereco.getText();
                String rg = txtRg.getText();
                String cpf = txtCpf.getText();
                String telefone = txtTelefone.getText();
                String senha = txtSenha.getText();

                Cliente cliente = new Cliente(nome, endereco, rg, cpf, telefone, gerente, senha);
                gerente.cadastrarCliente(cliente);
                Banco.salvarDados();
                JOptionPane.showMessageDialog(frame, "Cliente cadastrado com sucesso!");
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private static void cadastrarConta(Gerente gerente) {
        JFrame frame = new JFrame("Cadastrar Conta");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel lblCpfCliente = new JLabel("CPF do Cliente:");
        JTextField txtCpfCliente = new JTextField();
        JLabel lblSaldoInicial = new JLabel("Saldo Inicial:");
        JTextField txtSaldoInicial = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        frame.add(lblCpfCliente);
        frame.add(txtCpfCliente);
        frame.add(lblSaldoInicial);
        frame.add(txtSaldoInicial);
        frame.add(new JLabel()); // Empty cell
        frame.add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpfCliente = txtCpfCliente.getText();
                double saldoInicial = Double.parseDouble(txtSaldoInicial.getText());

                Cliente cliente = gerente.getClientes().stream().filter(c -> c.getCpf().equals(cpfCliente)).findFirst().orElse(null);
                if (cliente != null) {
                    String numeroConta = "C" + (cliente.getContas().size() + 1);
                    Conta conta = new Conta(numeroConta, saldoInicial);
                    gerente.cadastrarConta(cliente, conta);
                    Banco.salvarDados();
                    JOptionPane.showMessageDialog(frame, "Conta cadastrada com sucesso!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Cliente não encontrado!");
                }
            }
        });

        frame.setVisible(true);
    }

    private static void listarClientes(Gerente gerente) {
        JFrame frame = new JFrame("Lista de Clientes");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(gerente.getClientes().size(), 1));

        for (Cliente cliente : gerente.getClientes()) {
            JButton btnCliente = new JButton(cliente.getNome() + " - " + cliente.getCpf());
            btnCliente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarMenuCliente(cliente);
                }
            });
            frame.add(btnCliente);
        }

        frame.setVisible(true);
    }

    private static void mostrarMenuCliente(Cliente cliente) {
        JFrame frame = new JFrame("Menu do Cliente");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));

        JButton btnAcessarContas = new JButton("Acessar Contas");

        frame.add(btnAcessarContas);

        btnAcessarContas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acessarContas(cliente);
            }
        });

        frame.setVisible(true);
    }

    private static void acessarContas(Cliente cliente) {
        JFrame frame = new JFrame("Contas do Cliente");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(cliente.getContas().size(), 1));

        for (Conta conta : cliente.getContas()) {
            JButton btnConta = new JButton("Conta " + conta.getNumero() + " - Saldo: " + conta.getSaldo());
            frame.add(btnConta);
        }

        frame.setVisible(true);
    }
}

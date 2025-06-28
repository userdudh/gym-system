package br.upe;
import java.sql.Time;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int entrada;

        do {

            System.out.println("================ MENU ================");
            System.out.println("[1] Cadastros");
            System.out.println("[2] Importar indicadores biométricos");
            System.out.println("[3] Exportar relatórios ou indicadores");
            System.out.println("[0] Encerrar programa");
            System.out.print("Opção: ");

            entrada = scanner.nextInt();
            scanner.nextLine();

            switch(entrada){

                case 1 -> {
            
                    int entradaCadastro;

                    System.out.println("Qual cadastro deseja realizar?");
                    System.out.println("[1] Plano de treino");
                    System.out.println("[2] Exercício físico");
                    System.out.println("[3] Seção de treino");
                    System.out.println("[4] Indicadores biomédicos associados");
                    System.out.print("Opção: ");

                    entradaCadastro = scanner.nextInt();

                    switch(entradaCadastro){

                        case 1 -> {

                            private static void cadastrarUsuario() {
                                System.out.print("Nome: ");
                                String nome = scanner.nextLine();
                                System.out.print("Email: ");
                                String email = scanner.nextLine();
                                Usuario usuario = new Usuario(nome, email, TipoUsuario.COMUM);
                                usuarioService.cadastrarUsuario(usuario);
                                System.out.println("Usuário cadastrado com sucesso!");
                            }

                        case 2 -> {

                            private static void cadastrarExercicio() {
                                System.out.print("Nome do exercício: ");
                                String nome = scanner.nextLine();
                                System.out.print("Descrição: ");
                                String descricao = scanner.nextLine();
                                System.out.print("Caminho do GIF: ");
                                String gif = scanner.nextLine();
                                Exercicio exercicio = new Exercicio(nome, descricao, gif);
                                exercicioService.adicionarExercicio(exercicio);
                                System.out.println("Exercício cadastrado com sucesso!");
                            }
                        }

                        
                        }
                    }
                }

                case 2 -> {
                
                    System.out.println(" ");

                    break;
                }


                case 3 -> {

                System.out.println(" ");

                break;
                }

                case 0 -> {

                    System.out.println("Encerrando programa...");
                    Time.sleep(1000);

                    break;
                }  
                
                default -> System.out.println("Opção inválida! ");
            }

        } while(entrada != 0);
        
    }
}

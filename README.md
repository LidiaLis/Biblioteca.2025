# Biblioteca.2025

Este é um sistema desktop desenvolvido em Java para gerenciamento de uma biblioteca comunitária, criado como projeto pessoal por Lidia Lisboa utilizando o NetBeans Apache.

---

## Descrição do Projeto

O sistema realiza o controle completo de uma biblioteca, incluindo cadastro de livros, gestão de usuários, controle de empréstimos e devoluções, além de consultas e geração de relatórios. O projeto visa proporcionar uma experiência intuitiva, utilizando os recursos visuais do NetBeans para criação de interfaces gráficas (frames Swing).

---

## Tecnologias e Ferramentas

- **Linguagem:** Java (JDK 8+)
- **Interface Gráfica:** Java Swing (JFrame, JPanel, JTable, etc.)
- **Banco de Dados:** MySQL utilizando o Dbeaver.
- **Padrões de Projeto:**
  - DAO (Data Access Object)
  - Factory
    
---

## Estrutura do Projeto

```
src/
├── aplicacao/
│   ├── Biblioteca.java           # Tela principal do sistema
│   ├── DialogAcervo.java         # Tela principal do sistema
│   ├── DialogUsuarios.java       # Tela principal do sistema
│   ├── DialogEmprestimo.java     # Tela principal do sistema
│   ├── DialogDevolucao.java      # Tela principal do sistema
│   ├── Valid.java                # Valida telefone e e-mail dos úsuarios
│   ├── frmPrincipal1.java        # Tela principal do sistema
│   ├── frmAcervo.java            # Gestão de livros
│   ├── frmUsuario.java           # Gestão de usuários
│   ├── frmEmprestimo.java        # Controle de empréstimos
│   └── frmDevolucao.java         # Controle de devoluções
│
├── modelo/
│   ├── Livro.java                # Entidade Livro
│   ├── Usuario.java              # Entidade Usuário
│   ├── Emprestimo.java           # Entidade Empréstimo
│   └── Devolucao.java            # Entidade Devolução
│
├── dao/
│   ├── DAOGenerico.java          # Conexão com banco de dados
│   ├── DAOFactory.java           # Fábrica de DAOs
│   ├── LivroDAO.java             # Interface DAO de Livro
│   ├── LivroDAOJDBC.java         # Classe DAO de Livro
│   ├── UsuarioDAO.java           # Interface DAO de Usuário
│   ├── UsuarioDAOJDBC.java       # Classe DAO de Usuário
│   ├── EmprestimoDAO.java        # Interface DAO de Empréstimo
│   ├── EmprestimoDAOJDBC.java    # Classe DAO de Empréstimo
│   ├── DevolucaoDAO.java         # Interface DAO de Devolução
│   └── DevolucaoDAOJDBC.java     # Classe DAO de Devolução
│
└── recurso/
│   ├── ScripBiblioteca.sql       # Script do banco de dados
│   ├── Images.png                # Todos icons utilizadas na interface
│   └── Config.properties         # Configuração do banco de dados

---

## Funcionalidades

1. **Gestão de Usuários**
   - Cadastro de usuários
   - Alteração de senha
   - Níveis de acesso

2. **Gestão de Livros**
   - Cadastro de livros
   - Consulta de acervo
   - Edição e remoção de registros

3. **Empréstimos e Devoluções**
   - Registro de empréstimo de livros
   - Controle de devolução
   - Histórico de movimentações

---

## Configuração e Instalação

**Pré-requisitos:**
- Java JDK 8 ou superior
- NetBeans Apache (recomendado)
- Banco de Dados (MySQL)

**Configuração do Banco de Dados:**
- Crie o banco de dados desejado
- Configure o arquivo `src/recurso/config.properties`:
  ```
  db.url=jdbc:mysql://localhost:3306/biblioteca
  db.user=seu_usuario
  db.senha=sua_senha
  db.driver=com.mysql.jdbc.Driver
  ```

**Executando o Projeto:**
1. Clone o repositório:
   ```
   git clone https://github.com/LidiaLis/Biblioteca.2025.git
   ```
2. Abra o projeto no NetBeans
3. Configure as dependências do projeto
4. Execute a classe `src/aplicacao/Biblioteca.java`

---

## Estrutura do Banco de Dados (Exemplo MySQL)

Principais Tabelas:
- usuarios - Dados dos usuários
- livros - Cadastro de livros
- emprestimos - Registro de empréstimos
- devolucoes - Registro de devoluções
  
---

## Padrões de Projeto Utilizados

- **DAO (Data Access Object):**
  - Interfaces DAO para cada entidade
  - Implementações JDBC
  - DAOFactory para instanciação
- **Factory:**
  - DAOFactory para criar instâncias DAO
  - Isolamento da criação de objetos

---

## Contexto

Projeto desenvolvido com foco em boas práticas de Programação Orientada a Objetos, aplicando conceitos como:

- Encapsulamento
- Herança
- Polimorfismo
- Interfaces
- Tratamento de Exceções
- Persistência de Dados
- Interface Gráfica (frames Swing via NetBeans)
- Banco de Dados

---

## Autor

**Lidia Lisboa**  
[GitHub](https://github.com/LidiaLis)


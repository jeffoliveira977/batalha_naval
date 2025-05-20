# 🛳️ Batalha Naval – Projeto de Faculdade

Este é um jogo de **Batalha Naval** desenvolvido como parte de um projeto acadêmico. O jogo é executado no terminal e permite que um jogador humano enfrente uma inteligência artificial (IA).

---

## 📂 Estrutura do Projeto

- `src/main/java/com/batalha/naval/` – Código principal do jogo.
- `target/classes` – Pasta padrão de compilação.
- `pom.xml` – Arquivo de configuração do Maven.

---

## ⚙️ Requisitos

- Java 17 ou superior (recomendado: Java 24)
- [Apache Maven](https://maven.apache.org/) (opcional, mas recomendado)

---

## 🧪 Compilando o Projeto


Abra o terminal no diretório do projeto (onde se encontra o arquivo `pom.xml`) e execute os comandos abaixo.


### ✅ Usando Maven

Para compilar o projeto:

```bash
mvn compile
```
Para rodar o jogo:

```bash
mvn exec:java
```
### ☕ Usando Java diretamente

Compile o código:

```bash
javac -d target/classes src/main/java/com/batalha/naval/*.java
```

Execute o jogo:

```bash
java -cp target/classes com.batalha.naval.BatalhaNaval
```

### 📄 Funcionalidades
- Tabuleiro com navios posicionados aleatoriamente

- Turnos alternados entre jogador e IA

- Verificação de acertos e afundamento de navios

- Interface no terminal

- Possibilidade de gravar o jogo para retomada posterior
# Movies Battle

### Compilação

É necessário compilar o projeto com maven antes de rodá-lo.

Execute o comando abaixo a partir da raiz do projeto, onde está o arquivo *pom.xml*.

```shell
mvn clean compile
```

### Autenticação

O projeto está utilizando **Basic Access Authentication** como forma de auntenticação.

Veja mais sobre **Basic Access Authentication** [neste link](http://https://www.ibm.com/docs/en/cics-ts/5.4?topic=concepts-http-basic-authentication "neste link").


### Usuários

|  Usuário | Senha  |
| ------------ | ------------ |
| rapadura |rapadura |
| jovemnerd | jovemnerd |
| load | load |


### Sequência de utilização dos endpoints

- **POST** http://localhost:8080/quiz/iniciar
	- Iniciar um novo quiz
- **GET** http://localhost:8080/quiz/{{id_quiz}}
	- Retornar rodada atual do quiz com seus filmes
- **POST** http://localhost:8080/quiz/{{id_quiz}}/rodada/{{id_rodada}}/filme/{{idFilme}}
	- Escolher qual filme possui maior pontuação para o quiz e rodada
- **POST** http://localhost:8080/quiz/{{id_quiz}}/encerrar
	- Encerrar quiz a qualquer momento
- **GET** http://localhost:8080/quiz/ranking
	- Retonar ranking dos 10 melhores resultados no quiz

### Open API

-  http://localhost:8080/swagger-ui/index.html
-  http://localhost:8080/v3/api-docs

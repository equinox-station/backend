# Gerenciador de Tripulação da Estação Equinox (Backend)

### A aplicação
Essa aplicação é um backend cuja função é disponibilizar um CRUD que manipula o banco de dados com os registros da tripulação da Estação Equinox. Além do backend, esse projeto também conta com uma interface em JSF que o usuário pode usar para interagir com a aplicação.

O banco de dados utilizado é PostgreSQL. Utilizamos o serviço do [ElephantSQL](https://www.elephantsql.com/) para hospedar nossos dados.

### Requisitos implementados
- A. Aplicação Java Web em JSF
- B. Persistência em um banco de dados
- C. Hibernate e JPA
- D. Spring Framework 5 e Sprint Boot 2
- E. Bootstrap 4
- F. Primefaces
- G. Testes de unidades (para cada operação do CRUD)

Uma aplicação single-page em Angular (item H) que consome esse backend pode ser [encontrado aqui](https://github.com/equinox-station/single-page).

Esse aplicativo também está publicado no Heroku (item I) e pode ser acessado [clicando aqui](https://eegi.herokuapp.com/).

### Executando a aplicação
- Baixe a aplicação (clone ou zip) e importe na sua IDE de preferência como um projeto Maven (utilizamos o Spring Tool Suite).
- Para rodar os testes, escolha **Run As -> JUnit Teste**.
- Para executar a aplicação em si, escolha **Run As -> Spring Boot App**.
  - Após executar a aplicação, abra um browser e vá em http://localhost:8080/. A aplicação JSF deve carregar.
  - Em vez de usar a aplicação JSF, outros programas podem consumir essa API, uma vez que o endpoint **/usuarios** está exposto e aceita requisições GET, POST, PUT e DELETE. A nossa [aplicação single-page](https://github.com/equinox-station/single-page) usa essa estratégia.
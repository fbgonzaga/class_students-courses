# [2022_01] Redes de Computadores / Computer Networks

[PT-BR] O objetivo do trabalho é desenvolver as operações de CRUD relativas a um sistema básico para gerenciar estudantes e cursos.

Os requisitos são:

 - CRUD para estudantes;
 - CRUD para cursos;
 - Cada estudante pode se matricular em até 5 cursos;
 - Cada curso pode ter até 50 estudantes matriculados;
 - Listar os estudantes não matriculados em nenhum curso;
 - Listar cursos que não possuem estudantes matriculados;
 - Cada requisição para registro (seja de estudante ou curso) pode conter até 50 itens;

Technology stack:
 - Java
 - Maven
 - Spring Boot
 - Docker (docker-compose)
 - JUnit
 - MySQL

Author: Flavio B. Gonzaga

flavio.gonzaga@unifal-mg.edu.br

## Endpoints disponíveis ##

As tabelas a seguir resumem os endpoints (com os respectivos métodos HTTP) que devem estar disponíveis no
sistema para a gerência tanto dos estudantes quanto dos cursos.

**Estudantes:**

![students_tb_img](https://user-images.githubusercontent.com/30641015/182287758-7b75d6a6-c16e-448f-9715-4b74b0d18117.png)

**Cursos:**

![courses_tb_img](https://user-images.githubusercontent.com/30641015/182287871-5ec02e42-9e4d-4707-bb9b-dd4a64ffe406.png)

## Documentação dos endpoints ##

A documentação dos endpoints está disponível em:

[Postman documentation](https://documenter.getpostman.com/view/19854346/Uzs9y2fs)

Ob.: Nem todo endpoint retornará uma respostá válida. Alguns retornarão que o método precisa ser 
implementado.

## Executando o projeto ##

### "manualmente" ###

Assumindo que você já tenha instalado e configurado:
- OpenJDK (versão 11) juntamente da sua IDE favorita
- MariaDB

1. Crie o schema school.
2. Crie o usuário school_admin.
3. Conceda privilégios para o usuário school_admin no schema school.

`CREATE DATABASE school;`

`CREATE USER 'school_admin'@'%' IDENTIFIED BY 'school_admin';`

`GRANT ALL PRIVILEGES ON school.* TO 'school_admin'@localhost IDENTIFIED BY 'school_admin';`

Você está pronto para executar a aplicação :)

## Execução do projeto completo usando Docker ##

Para fins de gabarito, está também disponível no Docker Hub os contâineres 
capazes de realizar a execução completa do trabalho proposto.

Assumindo que você já tenha instalado o Docker Desktop na sua máquina Linux, você pode agora fazer o pull dos contâiners.

`docker pull fbgonzaga/school-application:latest`

`docker pull fbgonzaga/school-database:latest`

Você encontrará na raiz deste projeto o arquivo **docker-compose.yml**.

Executando a aplicação:

`docker compose up`

A aplicação deve estar disponível em:

`http://localhost:8080/`

## Referências ##
Acesse as seguintes páginas para aprender mais sobre boas práticas no desenvolvimento de APIs RESTFUL.

 - [HTTP API Design Guide](https://geemus.gitbooks.io/http-api-design/content/en/)
 - [Zalando RESTful API and Event Guidelines](https://opensource.zalando.com/restful-api-guidelines/index.html)

## Agradecimentos ##

Muito obrigado pelo interesse no projeto.
Todo feedback é bem vindo.

---

[EN] The goal is to develop a CRUD as a basic system for managing students and courses.

The requirements are:

 - CRUD for students;
 - CRUD for courses;
 - Each student can enroll in up to 5 courses;
 - Each course can have up to 50 students enrolled;
 - List students not enrolled in any course;
 - List courses without enrolled students;
 - Each registering request (for students or courses) can have up to 50 "items";

Technology stack:
- Java
- Maven
- Spring Boot
- Docker (docker-compose)
- JUnit
- MySQL

Author: Flavio B. Gonzaga

flavio.gonzaga@unifal-mg.edu.br

## Available endpoints ##

The following tables summarize the available endpoints and HTTP methods for both (students and courses).

**Students:**

![students_tb_img](https://user-images.githubusercontent.com/30641015/182287758-7b75d6a6-c16e-448f-9715-4b74b0d18117.png)

**Courses:**

![courses_tb_img](https://user-images.githubusercontent.com/30641015/182287871-5ec02e42-9e4d-4707-bb9b-dd4a64ffe406.png)

## Endpoints documentation ##

The endpoints documentation is available at:

[Postman documentation](https://documenter.getpostman.com/view/19854346/Uzs9y2fs)

Ob.: Some endpoints will return an invalid response. For these endpoints you must implement the method.

## Running the project ##

### "manually" ###

Assuming you have already installed and configured:
- OpenJDK (version 11) with your prefered JAVA IDE
- MariaDB

1. Create the school schema.
2. Create the user school_admin.
3. Grant privileges for the school_admin user on the school schema.

`CREATE DATABASE school;`

`CREATE USER 'school_admin'@'%' IDENTIFIED BY 'school_admin';`

`GRANT ALL PRIVILEGES ON school.* TO 'school_admin'@localhost IDENTIFIED BY 'school_admin';`

You are now ready do execute the application using your JAVA IDE. :)


### Running the complete project using docker compose ###

The complete system implementation is available at Docker Hub.

Assuming you have already installed the Docker Desktop on Linux, pull the containers from the Docker Hub:

`docker pull fbgonzaga/school-application:latest`

`docker pull fbgonzaga/school-database:latest`

You can find in the root path of this repository the file **docker-compose.yml**.

Running the application:

`docker compose up`

The application must be available at:

`http://localhost:8080/`

## References ##
Visit the following pages to learn more about best practices in developing RESTFUL APIs.

 - [HTTP API Design Guide](https://geemus.gitbooks.io/http-api-design/content/en/)
 - [Zalando RESTful API and Event Guidelines](https://opensource.zalando.com/restful-api-guidelines/index.html)

## Acknowledgements ##

Thank you very much for your time in evaluating my project.
Feel free to contact me and share your thoughts!

# Currency Converter

Um sistema web de conversão de moedas em tempo real com autenticação de usuários e histórico de conversões, desenvolvido com Spring Boot.

## 🚀 Funcionalidades

- **Autenticação e Autorização**: Sistema completo de registro e login de usuários com Spring Security
- **Conversão de Moedas**: Conversão em tempo real utilizando a API ExchangeRate
- **Histórico de Conversões**: Armazena as últimas 10 conversões de cada usuário
- **Validação de Dados**: Validação robusta de formulários com Jakarta Validation
- **Segurança**: Senhas criptografadas com BCrypt

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate**
- **MySQL/PostgreSQL** (configurável)
- **Thymeleaf** (template engine)
- **Lombok**
- **Jakarta Validation**
- **RestTemplate** (para consumo de API externa)

## 📋 Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- MySQL 8.0+ ou PostgreSQL 12+
- Conexão com a internet (para API de taxas de câmbio)

## ⚙️ Configuração do Ambiente

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd currency-converter
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto (veja o exemplo em `.env.example`):

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=currency_converter
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

### 3. Configure o application.properties

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:currency_converter}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Thymeleaf
spring.thymeleaf.cache=false

# Server
server.port=8080
```

### 4. Crie o banco de dados

```sql
CREATE DATABASE currency_converter CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 🚀 Executando a Aplicação

### Usando Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Usando JAR

```bash
mvn clean package
java -jar target/currency-converter-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Estrutura do Projeto

```
currency-converter/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/build/currency_converter/
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── HomeController.java
│   │   │       ├── entity/
│   │   │       │   ├── User.java
│   │   │       │   └── ConversionHistory.java
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.java
│   │   │       │   └── ConversionHistoryRepository.java
│   │   │       └── service/
│   │   │           ├── UserService.java
│   │   │           ├── CustomUserDetailsService.java
│   │   │           └── ConversionService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/
│   │       └── static/
│   └── test/
├── .env.example
├── .gitignore
├── pom.xml
└── README.md
```

## 🔐 Endpoints

### Públicos
- `GET /login` - Página de login
- `POST /perform_login` - Processa o login
- `GET /register` - Página de registro
- `POST /register` - Processa o registro

### Protegidos (requer autenticação)
- `GET /home` - Página principal com conversor e histórico
- `POST /convert` - Realiza conversão de moeda
- `POST /perform_logout` - Logout do sistema

## 💾 Modelo de Dados

### User
- `id`: Long (PK)
- `name`: String (2-100 caracteres)
- `email`: String (único, formato válido)
- `password`: String (mínimo 6 caracteres, criptografada)
- `birthDate`: LocalDate
- `createdAt`: LocalDateTime
- `role`: String (padrão: ROLE_USER)

### ConversionHistory
- `id`: Long (PK)
- `user_id`: Long (FK)
- `sourceCurrency`: String
- `targetCurrency`: String
- `amount`: Double
- `convertedAmount`: Double
- `exchangeRate`: Double
- `conversionDate`: LocalDateTime

## 🌐 API Externa

Este projeto utiliza a [ExchangeRate API](https://www.exchangerate-api.com/) para obter taxas de câmbio em tempo real.

**Endpoint utilizado**: `https://api.exchangerate-api.com/v4/latest/{moeda_base}`

## 🔒 Segurança

- Senhas são criptografadas usando BCrypt
- Proteção CSRF habilitada (exceto para endpoints de login/logout)
- Sessões gerenciadas pelo Spring Security
- Endpoints protegidos por autenticação

## 📝 Validações

### Registro de Usuário
- Nome: obrigatório, entre 2-100 caracteres
- Email: obrigatório, formato válido (RFC 5322), único
- Senha: obrigatória, mínimo 6 caracteres
- Data de nascimento: obrigatória, deve ser no passado

### Conversão
- Moeda de origem: obrigatória
- Moeda de destino: obrigatória
- Valor: obrigatório, numérico positivo

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 👨‍💻 Autor

Alexander Costa - (https://github.com/alexanderbs3)

## 📞 Suporte

Para suporte, envie um email para alexander.cbss13@gmail.com.

---

⭐ Se este projeto te ajudou, considere dar uma estrela!# currency-converter

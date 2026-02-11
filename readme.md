

# Rubrica – Applicazione Desktop Java (Swing + MySQL + Hibernate)

Rubrica è un'applicazione desktop Java (Swing) per la gestione di contatti, con persistenza su database MySQL tramite Hibernate (JPA).

---

# Requisiti

- Java 17 (JDK o JRE 17)
- Maven (solo per build da sorgente)
- MySQL 8.x già installato **oppure** container MySQL Docker attivo

---

# Struttura del progetto

- GUI: Java Swing
- ORM: Hibernate (JPA)
- Database: MySQL
- Build: Maven
- Packaging: Fat JAR (maven-shade-plugin)

Il file eseguibile finale è:

```
target/Rubrica.jar
```

---

# 1️⃣ Creazione Database MySQL

L'applicazione si aspetta un database chiamato:

```
rubrica
```

## Accesso a MySQL

Da terminale:

```
mysql -u <utente> -p
```

Oppure (se Docker):

```
docker exec -it rubrica-mysql mysql -u rubrica_user -p
```

---

# 2️⃣ Creazione Tabella `persona`

Una volta dentro MySQL eseguire script _schema_database.sql_

Verifica:

```sql
SHOW TABLES;
DESCRIBE persona;
```

---

# 3️⃣ Configurazione Hibernate

La configurazione è presente in:

```
src/main/resources/hibernate.properties
```

Esempio configurazione:

```
hibernate.connection.driver_class=com.mysql.cj.jdbc.Driver
hibernate.connection.url=jdbc:mysql://localhost:3306/rubrica?useSSL=false&serverTimezone=UTC
hibernate.connection.username=rubrica_user
hibernate.connection.password=rubrica_pass
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
hibernate.format_sql=true
hibernate.highlight_sql=true
```

Note:
- `hibernate.hbm2ddl.auto=update` aggiorna automaticamente lo schema se necessario.
- L’utente MySQL deve avere permessi sul DB `rubrica`.

---

# 4️⃣ Build del progetto

Da root del progetto:

```
mvn clean package
```

Output:

```
target/Rubrica.jar
```

Se il build è corretto vedrai:

```
BUILD SUCCESS
```

---

# 5️⃣ Avvio Applicazione

Da terminale:

```
java -jar target/Rubrica.jar
```

Se la connessione è corretta, vedrai nei log:

```
HHH000412: Hibernate ORM core version
Loaded JDBC driver class: com.mysql.cj.jdbc.Driver
```

E query SQL tipo:

```
select ... from persona
insert into persona ...
update persona ...
delete from persona ...
```

---

# Operazioni Supportate

- Inserimento nuova persona
- Modifica persona esistente
- Eliminazione persona
- Caricamento automatico dati da DB all'avvio

---

# Problemi Comuni

## ❌ Table 'rubrica.persona' doesn't exist

La tabella non è stata creata.

Riesegui lo script SQL della sezione 2.

---

## ❌ NullPointerException su id

Assicurarsi che:

- L'id sia `Integer` (non `int`)
- In modalità "Nuovo" non venga passato un id manualmente
- Hibernate gestisca l'auto-increment

---

# Note Tecniche

- Hibernate utilizza il pool di connessioni interno (DriverManagerConnectionProvider)
- Le proprietà Hikari vengono ignorate se Hikari non è nel classpath
- MySQLDialect può essere omesso (Hibernate lo rileva automaticamente)

---

# Versione

Rubrica 1.0.0

---

# Autore

Federico D'Armini
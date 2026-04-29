# Sistema Gestionale Università

Progetto di una Web Application dinamica per la simulazione di un sistema universitario, focalizzata sulla gestione delle carriere degli studenti e degli appelli d'esame.

## 🛠 Stack Tecnologico
* **Linguaggio:** Java
* **Tecnologie Web:** JSP (JavaServer Pages), Servlet
* **Librerie:** JDBC (MySQL Connector) per la persistenza dei dati
* **Frontend:** HTML/CSS (JSP dinamiche)
* **IDE:** Eclipse Enterprise Edition

## 🚀 Funzionalità Principali
* **Autenticazione:** Sistema di login differenziato per Studenti, Professori e Segreteria.
* **Area Studente:** Visualizzazione esami e prenotazione appelli.
* **Area Segreteria:** Gestione degli archivi e registrazione nuovi utenti.
* **Integrazione Database:** Connessione robusta tramite classe `Connessione.java` per operazioni CRUD.

## 📂 Struttura del Progetto
Il progetto segue la struttura standard dei progetti Web Java:
* `src/main/java/mypackage/`: Contiene le Servlet e la logica di business.
* `src/main/webapp/`: Contiene le pagine JSP e le risorse statiche (CSS).
* `WEB-INF/lib/`: Include il driver JDBC per MySQL.

## 🗄️ Database e Modellazione
Il progetto include una base di dati relazionale per la persistenza delle informazioni.
* **Dump SQL:** Il file `Dump_universita.sql` nella cartella `/database` contiene la struttura delle tabelle e i dati di test.
* **Modello E-R:** È presente il file `.mwb` apribile con MySQL Workbench per visualizzare lo schema logico e le relazioni tra le entità (Studenti, Esami, Prenotazioni).

## ⚙️ Configurazione
Per avviare il progetto localmente:
1. Importare il dump SQL nel proprio DBMS MySQL.
2. Configurare i parametri di accesso (URL, username, password) nella classe `src/main/java/mypackage/Connessione.java`.
3. Associare il progetto a un server **Apache Tomcat** (consigliata versione 9 o superiore) in Eclipse.

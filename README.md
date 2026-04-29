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

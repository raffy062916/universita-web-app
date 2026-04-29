<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registrazione Università</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="card" style="width: 500px;"> <h3>Nuova Registrazione</h3>
	    <form action="RegistraUtente" method="post">
	        
	        <p>Seleziona Ruolo</p>
			<select name="ruolo" required>
			    <option value="studente">Studente</option>
			    <option value="professore">Professore</option>
			    <option value="segreteria">Segreteria</option> 
			</select>
	
	        <div class="input-group">
	            <div class="input-field">
	                <p>Nome</p>
	                <input type="text" name="nome" placeholder="Es: Mario" required>
	            </div>
	            <div class="input-field">
	                <p>Cognome</p>
	                <input type="text" name="cognome" placeholder="Es: Rossi" required>
	            </div>
	        </div>
	
	        <div class="input-group">
	            <div class="input-field">
	                <p>Username</p>
	                <input type="text" name="username" placeholder="User123" required>
	            </div>
	            <div class="input-field">
	                <p>Password</p>
	                <input type="password" name="password" placeholder="••••••••" required>
	            </div>
	        </div>
	
	        <input type="submit" value="Conferma Registrazione">
	    </form>
	    
	    <div style="text-align: center; margin-top: 15px;">
	        <a href="index.jsp" style="font-size: 0.85rem;">Hai già un account? <strong>Accedi</strong></a>
	    </div>
	</div>
<!--  
    <div class="card">
        <h3>Nuova Registrazione</h3>
        <form action="RegistraUtente" method="post">
            
            <p>Seleziona Ruolo</p>
            <select name="ruolo" required style="width: 100%; padding: 14px; border-radius: 10px; border: 2px solid #eee; margin-bottom: 15px; background: #f9f9f9;">
                <option value="studente">Studente</option>
                <option value="professore">Professore</option>
            </select>

            <p>Nome</p>
            <input type="text" name="nome" placeholder="Inserisci Nome" required>
            
            <p>Cognome</p>
            <input type="text" name="cognome" placeholder="Inserisci Cognome" required>
            
            <p>Username</p>
            <input type="text" name="username" placeholder="Scegli Username" required>
            
            <p>Password</p>
            <input type="password" name="password" placeholder="Scegli Password" required>

            <input type="submit" value="Conferma Registrazione">
        </form>
        
        <div style="text-align: center; margin-top: 15px;">
            <a href="index.jsp" style="color: var(--dark-blue); font-size: 0.8rem;">Hai già un account? Accedi</a>
        </div>
    </div>
    -->
</body>
</html>
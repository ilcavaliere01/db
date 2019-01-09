package starter;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import database.Database;
import interfacciaGrafica.Frame;
import query.Query;

public class Starter {

	public static void main(String[] args)
	{
		String nomedb = "takeyourcar";
		String user = "root";
		String pass = "";
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRORE!", JOptionPane.ERROR_MESSAGE);
		}
		
		Database db = new Database("jdbc:mysql://localhost:3306/" + nomedb + "?user=" + user + "&password=" + pass + "&autoReconnect=true&useSSL=false");
		try {
			db.connetti();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERRORE CONNESSIONE", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
		
		Query q1 = new Query("Tutti i dipendenti", "SELECT * FROM DIPENDENTE");
		Query q2 = new Query("Tutti i veicoli", "SELECT * FROM VEICOLO");
		Query q3 = new Query("Tutte le autovetture", "SELECT * FROM AUTOVETTURA");
		Query q4 = new Query("Tutti i motocicli", "SELECT * FROM MOTOCICLO");
		Query q5 = new Query("Tutte le ditte", "SELECT * FROM DITTA_MANUTENZIONE");
		Query q6 = new Query("Tutti gli abbonati", "SELECT * FROM ABBONATO");
		Query q7 = new Query("Tutti gli occasionali", "SELECT * FROM OCCASIONALE");
		Query q8 = new Query("Tutti i pagamenti", "SELECT * FROM PAGAMENTO");
		Query q9 = new Query("Tutti i viaggi", "SELECT * FROM VIAGGIO");
		Query q10 = new Query("Tutte le assicurazioni", "SELECT * FROM ASSICURAZIONE");
		Query q11 = new Query("Tutti i recapiti", "SELECT * FROM RECAPITO");
		Query q12 = new Query("Tutti i clienti", "SELECT * FROM CLIENTE");
		Query q13 = new Query("targa e nome modello dei veicoli che sono stati noleggiati dai clienti pi� giovani del pi� vecchio dipendente che gestisce il deposito con il maggior numero di veicoli", "SELECT V.Targa, V.Modello, timestampdiff(year, C.DataNascita, curdate()) AS Et�\r\n" + 
				"	FROM VEICOLO AS V NATURAL JOIN NOLEGGIATO_DA AS N NATURAL JOIN CLIENTE AS C\r\n" + 
				"    WHERE timestampdiff(year, C.DataNascita, curdate()) < (SELECT MAX(timestampdiff(year, D.DataNascita, curdate()))\r\n" + 
				"															   FROM DIPENDENTE AS D NATURAL JOIN DEPOSITO AS DEP\r\n" + 
				"															   WHERE D.Gestisce = DEP.Codice && DEP.NumeroVeicoli = (SELECT MAX(D.NumeroVeicoli)\r\n" + 
				"																														 FROM DEPOSITO AS D));");
		Query q14 = new Query("Viaggi del primo giorno del mese", "SELECT *\r\n" + 
				"					 												   FROM VIAGGIO AS V\r\n" + 
				"    				  												   WHERE V.InizioNoleggio LIKE \"____-__-01\";");
		Query q15 = new Query("nome, cognome e CF dei clienti che hanno effettuato un pagamento di importo maggiore del pi� alto premio assicurativo", "SELECT C.Nome, C.Cognome, C.CF, P.Importo\r\n" + 
				"	FROM CLIENTE AS C NATURAL JOIN PAGAMENTO AS P\r\n" + 
				"    WHERE P.Cliente = C.CF && P.Importo > (SELECT MAX(A.Premio)\r\n" + 
				"											   FROM ASSICURAZIONE AS A);");
		Query q16 = new Query("nome, cognome e CF dei dipendenti che gestiscono depositi con pi� di 50000 veicoli e che hanno almeno 40 anni", "SELECT D.Nome, D.Cognome, D.CF, D.DataNascita, DEP.NumeroVeicoli, DEP.Comune\r\n" + 
				"	FROM DIPENDENTE AS D NATURAL JOIN DEPOSITO AS DEP\r\n" + 
				"    WHERE D.Gestisce = DEP.Codice && DEP.NumeroVeicoli > 50000\r\n" + 
				"    HAVING timestampdiff(year, D.DataNascita, curdate()) >= 40;");
		Query q17 = new Query("numero dei numeri di telefono di ogni compagnia assicurativa", "SELECT A.Nome, A.P_IVA, COUNT(DISTINCT R.NumeroTelefonico) NumeriTelefonici\r\n" + 
				"	FROM ASSICURAZIONE AS A, RECAPITO AS R\r\n" + 
				"    WHERE A.NumeroPratica = R.NumeroPratica\r\n" + 
				"    GROUP BY A.NOME;");
		Query q18 = new Query("ordina i depositi secondo la capienza in ordine crescente", "SELECT *\r\n" + 
				"	FROM DEPOSITO AS D\r\n" + 
				"    ORDER BY D.Capienza;");
		Query q19 = new Query("per ogni assicurazione, nome, p.iva e numero auto assicurate", "SELECT A.Nome, A.P_IVA, COUNT(*) NumeroAutoAssicurate\r\n" + 
				"	FROM ASSICURAZIONE AS A NATURAL JOIN VEICOLO AS V\r\n" + 
				"    WHERE A.VeicoloAssicurato = V.Targa\r\n" + 
				"    GROUP BY A.Nome;");
		Query q20 = new Query("seleziona nome e cognome dei clienti che hanno effettuato pagamenti maggiori o uguali di 100�", "SELECT C.Nome, C.Cognome, C.CF, P.Importo\r\n" + 
				"	FROM CLIENTE AS C NATURAL JOIN PAGAMENTO AS P\r\n" + 
				"    WHERE C.CF = P.Cliente && P.Importo >= 100;");
		Query q21 = new Query("stampa clienti abbonati", "SELECT *\r\n" + 
				"    FROM ABBONATO AS A NATURAL JOIN CLIENTE AS C\r\n" + 
				"    WHERE A.CF = C.CF;");
		Query q22 = new Query("Stampa lista clienti occasionali", "SELECT *\r\n" + 
				"	FROM OCCASIONALE AS O NATURAL JOIN CLIENTE AS C\r\n" + 
				"    WHERE O.CF = C.CF;");
		Query q23 = new Query("stampa veicoli assicurati", "SELECT *\r\n" + 
				"	FROM VEICOLO AS V NATURAL JOIN ASSICURAZIONE AS A\r\n" + 
				"    WHERE A.VeicoloAssicurato = V.Targa;\r\n" + 
				"\r\n" + 
				"");
		/*Query q24 = new Query("", "");
		Query q25 = new Query("", "");
		Query q26 = new Query("", "");
		Query q27 = new Query("", "");
		Query q28 = new Query("", "");
		Query q29 = new Query("", "");
		Query q30 = new Query("", "");
		Query q31 = new Query("", "");
		Query q32 = new Query("", "");
		Query q33 = new Query("", "");
		Query q34 = new Query("", "");
		Query q35 = new Query("", "");
		Query q36 = new Query("", "");*/
		
		db.addQuery(q1);
		db.addQuery(q2);
		db.addQuery(q3);
		db.addQuery(q4);
		db.addQuery(q5);
		db.addQuery(q6);
		db.addQuery(q7);
		db.addQuery(q8);
		db.addQuery(q9);
		db.addQuery(q10);
		db.addQuery(q11);
		db.addQuery(q12);
		db.addQuery(q13);
		db.addQuery(q14);
		db.addQuery(q15);
		db.addQuery(q16);
		db.addQuery(q17);
		db.addQuery(q18);
		db.addQuery(q19);
		db.addQuery(q20);
		db.addQuery(q21);
		db.addQuery(q22);
		db.addQuery(q23);
		/*db.addQuery(q24);
		db.addQuery(q25);
		db.addQuery(q26);
		db.addQuery(q27);
		db.addQuery(q28);
		db.addQuery(q29);
		db.addQuery(q30);
		db.addQuery(q31);
		db.addQuery(q32);
		db.addQuery(q33);
		db.addQuery(q34);
		db.addQuery(q35);
		db.addQuery(q36);*/
		
		Frame frame = new Frame(db);
		frame.setVisible(true);
		/*try {
			db.connetti();
			
			res = db.eseguiQuery("SELECT V.Targa, V.Modello, timestampdiff(year, C.DataNascita, curdate()) AS Et�\r\n" + 
					"	FROM VEICOLO AS V NATURAL JOIN NOLEGGIATO_DA AS N NATURAL JOIN CLIENTE AS C\r\n" + 
					"    WHERE timestampdiff(year, C.DataNascita, curdate()) < (SELECT MAX(timestampdiff(year, D.DataNascita, curdate()))\r\n" + 
					"															   FROM DIPENDENTE AS D NATURAL JOIN DEPOSITO AS DEP\r\n" + 
					"															   WHERE D.Gestisce = DEP.Codice && DEP.NumeroVeicoli = (SELECT MAX(D.NumeroVeicoli)\r\n" + 
					"																														 FROM DEPOSITO AS D));");
			System.out.println("Tutti i Depositi");
			System.out.println("--- Inizio ---");
			while (res.next())
				System.out.println(res.getString("Targa") + ", " + res.getString("Modello") + ", " + res.getString("Et�"));
			System.out.println("--- Fine ---\n");
			
			res = db.eseguiQuery("SELECT * FROM CLIENTE");
			System.out.println("Tutti i Clienti");
			System.out.println("--- Inizio ---");
			while (res.next())
				System.out.println(res.getString("CF") + ", " + res.getString("Nome") + ", " + res.getString("Cognome") + " , "
								 + res.getString("LuogoNascita") + ", " + res.getInt("Telefono") + ", " + res.getInt("DataNascita")
								 + res.getString("N_CartadiCredito"));
			System.out.println("--- Fine ---\n");
			
			db.chiudiConnessione();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERRORE", JOptionPane.ERROR_MESSAGE);
		}*/
	}

}

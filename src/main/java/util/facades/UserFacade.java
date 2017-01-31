package util.facades;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import net.Communication;
import net.ServerRequest;
import util.Credentials;
import util.DataPackage;
import util.RuntimeDataHolder;
import util.User;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class UserFacade {

	private static UserFacade userFacade = null;

	private UserFacade() {
		super();
	}

	public static UserFacade getInstance() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}
		return userFacade;
	}

	public void addUser(){
		//TODO: dodawanie usera
		//gdzie beda przekazywane wartosci do konstruowania usera?
	}

	public boolean connectToServer() {
		while (true) {
			Socket socket = RuntimeDataHolder.getInstance().getSocket();
			if (socket != null) {
				return true;
			}
			try {
				socket = new Socket("127.0.0.1", 4000);
				RuntimeDataHolder.getInstance().setSocket(socket);
			} catch (IOException e) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Połączenie nie udane");
				alert.setHeaderText("Połączenie z serwerem nieudane");
				alert.setContentText("Czy chcesz spróbować ponownie?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					continue;
				} else {
					Platform.exit();
				}
				return false;
			}
			return true;
		}

	}

	private String credentialsLocalError(Credentials credentials) {

		String message = "";

		if (!credentials.loginHaveAllowedLength()) {
			message += "Minimalna długość nazwy użytkownika: 4 znaki"+System.getProperty("line.separator");
		}

		if (!credentials.passwordHaveAllowedLength()) {
			message += "Minimalna długość hasła: 4 znaki"+System.getProperty("line.separator");
		}

		if (credentials.loginContainsUnallowedChars()) {
			message += "Niedozwolone znaki w nazwie użytkownika"+System.getProperty("line.separator");
		}
		if (credentials.passwordContainsUnallowedChars()) {
			message += "Niedozwolone znaki w haśle"+System.getProperty("line.separator");
		}

		if (message.length() > 0) {
			message += System.getProperty("line.separator") + "Popraw błędy i spróbuj ponownie";
			return message;
		}

		return null;

	}

	/**
	 * Sprawdza czy użytkownik istnieje i dane się zgadzają, jak tak loguje
	 * Ustawia zalogowanego {@link User} w RuntimeDataHolder.loggedUser
	 * @return true jeśli zalogowano
	 */
	private String credentialsServerError(Credentials credentials)  {

		DataPackage receivedDataPackage;
		String          details     = "login";
		DataPackage     dataPackage = new DataPackage(details, credentials);
		ServerRequest request     = new ServerRequest(dataPackage);

		Communication.getInstance().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		receivedDataPackage = request.getDataPackage();
		details = receivedDataPackage.getDetails();
		if (details.equals("logged")) {
			//zalogowano pomyślnie
			User user = (User) receivedDataPackage.getObject();

			RuntimeDataHolder.getInstance().setLoggedUser(user);

			System.out.println("Zalogowano pomyślnie:");
			System.out.println("Imie:       "+user.getName());
			System.out.println("Nazwisko:   "+user.getSurname());
			return null;
		}
		return  details;

	}

	public String login(Credentials credentials){

		String result = null;

		result = credentialsLocalError(credentials);
		if (result != null) {
			return result;
		}

		result = credentialsServerError(credentials);
		return result;

	}

	public String register(Credentials credentials, User user) {

	    String              result;
		Communication 		c 			= Communication.getInstance();
		String				details		= "register";
		ArrayList<Object>	params		= new ArrayList<Object>();

		params.add(credentials);
		params.add(user);

		DataPackage		dataPackage = new DataPackage(details, params);
		ServerRequest	request		= new ServerRequest(dataPackage);

        result = credentialsLocalError(credentials);

        if (result != null) {
            return result;
        }

		c.addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		details = request.getDataPackage().getDetails();

		return details;
	}

	public void logout(){

		DataPackage		dataPackage		= new DataPackage("logout",null);
		ServerRequest	serverRequest	= new ServerRequest(dataPackage, false);
		Communication	communication	= Communication.getInstance();

		communication.addRequest(serverRequest);

		RuntimeDataHolder.getInstance().setLoggedUser(null);
	}


	public User addUser(User user){
		//TODO: cos tu mialo byc, chyba nadawanie id dodawanemu userowi, nie?
		return user;
	}
}


package DeliveryManSystem.GraphicalInterfaceClientSystem;

public class SetDMLanguage {
    static SetDMLanguage instance;
    private String language;

    private SetDMLanguage() {this.language = "english";}

    public static synchronized SetDMLanguage getInstance() {
        if (instance == null)
            instance = new SetDMLanguage();
        return instance;
    }

    public String[] setLoginPanel(){

        if(language.equals("italiano")){
            String[] s = {"1", "Italiano", "Inglese", "Accedi", "Lingua   :", "Password   :", "ID   :",
                          "<html><center>Messaggio importante</html>!", "Accedi", "<html><center>Password o Id errati</html>",
                          "<html><center>Servizio non disponibile</html>", "<html><center>Inserire ID e Password</html>"};
            return s;
        }else{
            String[] s = {"0", "English", "Italian", "Sign In", "Language   :", "Password   :", "ID   :",
                          "<html><center>Important Message!</html>", "Sign In", "<html><center>Incorrect Password or Id</html>",
                          "<html><center>Service unavailable</html>", "<html><center>Enter ID and Password</html>"};
            return s;
        }
    }

    public String[] setPackagePanel(){
        if(language.equals("italiano")){
            String[] s = {"Pacchi da consegnare", "Pacchi da ritirare", "ID Punto di Ritiro", "ID Pacco", "Indietro",
                          "Info Punti di Ritiro"};
            return s;
        } else {
            String[] s = {"Parcels to be delivered", "Parcels to collect", "Pickup Point ID", "Package ID", "Go back",
                          "Info Pickup Points"};
            return s;
        }
    }

    public String[] setPickupPointInfoPanel(){
        if(language.equals("italiano")){
            String[] s = {"Info Punti di Ritiro", "ID", "Luogo", "Indirizzo", "Indietro"};
            return s;
        } else {
            String[] s = {"Pickup Points Info", "ID", "Location", "Address", "Go Back"};
            return s;
        }
    }

    public String[] setStartingPanel(){
        if(language.equals("italiano")){
            String[] s = {"<html> <center> Impossibile connettersi al server</html>",
                          "<html><center>Credenziali inviate correttamente al punto di ritiro ",
                          "<html> <center> Impossibile connettersi al server di ",
                          "Mostra pacchi", "Sei sicuro?", "SI", "Disconnetti",
                          "<html> <center> Selezionare l'ID del punto di ritiro</html>"};
            return s;
        } else {
            String[] s = {"<html> <center> Can't connect to server</html>",
                          "<html><center>Credentials sents successfully to the pickup point ",
                          "<html> <center> Can't connect to the server of ",
                          "View Packages", "Are You Sure?", "YES", "Logout",
                          "<html> <center> Select the ID of the pickup point</html>"};
            return s;
        }
    }


    public void changeLanguage(String language){this.language=language;}

}

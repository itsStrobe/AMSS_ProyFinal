package amss.GUI.Controllers;

import amss.app.Connection.Inquilino_Model;
import amss.app.Individuos.Inquilino;
import amss.app.util.RandomUuidGenerator;
import amss.app.util.Time;
import amss.app.util.Uuid;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by German y Jose Zavala on 10/17/17.
 */
public class InquilinoForm_Controller implements Initializable {

  @FXML
  private TextField nombreField;
  @FXML
  private TextField direccionField;
  @FXML
  private TextField edadField;
  @FXML
  private TextField padecimientoField;
  @FXML
  private TextField cuartoField;

  private Uuid.Generator uuidGenerator;
  private final Inquilino_Model inquilino_model = new Inquilino_Model();
  private Inquilino inquilino;
  Stage prevStage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String fileName = location.getFile().substring(location.getFile().lastIndexOf('/') + 1, location.getFile().length());
    Uuid uuidBase = Uuid.NULL;
    try {
      uuidBase = Uuid.parse("101.0000000000");
    } catch (IOException ex) {
    }

    this.uuidGenerator = new RandomUuidGenerator(uuidBase, System.currentTimeMillis());
  }

  public void setPrevStage(Stage stage) {
    this.prevStage = stage;
  }

  public void setInquilino(Inquilino inquilino) {
    this.inquilino = inquilino;
  }

  public void transition_Back() throws Exception {
    Stage stage = new Stage();
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/inquilinos.fxml"));

    Pane myPane = (Pane) myLoader.load();
    Scene myScene = new Scene(myPane);
    stage.setScene(myScene);

    Inquilinos_Controller controller = (Inquilinos_Controller) myLoader.getController();
    controller.setPrevStage(stage);

    stage.setTitle("Inquilinos");

    prevStage.close();

    stage.show();
  }

  public void add_Inquilino() throws Exception {
    Uuid uuid = uuidGenerator.make();
    String nombre = nombreField.getText();
    String direccion = direccionField.getText();
    int edad = Integer.parseInt(edadField.getText());
    Time fechaN = Time.now();
    String cuarto = cuartoField.getText();

    Inquilino newInquilino = new Inquilino(uuid, nombre, direccion, edad, fechaN, cuarto);


    inquilino_model.add(newInquilino);

    transition_Back();

  }
}
package amss.app.Connection;

import amss.app.Common.Model;
import amss.app.Elementos.Receta;
import amss.app.Individuos.Familiar;
import amss.app.util.SQLFormatter;
import amss.app.util.Time;
import amss.app.util.Uuid;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jose Zavala on 18/10/17.
 */
public class Familiar_Model extends Model{
  private Familiar familiar;

  // FUNCIONES SET
  public void add(Familiar familiar)
  {
    this.familiar = familiar;
    add();
  }

  @Override
  public void add() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(SQLFormatter.sqlID(familiar.getId()));
      parameters.add(familiar.getNombre());
      parameters.add(familiar.getTelefono());
      parameters.add(familiar.getDireccion());
      query = "INSERT INTO RECETAS (ID,NOMBRE,TELEFONO,DIRECCION) " +
          "VALUES ( ?, ?, ?, ?);";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Nuevo Familiar (ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Nuevo Familiar - ERROR - Database insertion error ((ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public void update(Familiar familiar) {
    this.familiar = familiar;
    update();
  }

  @Override
  public void update() {
    String query;
    Vector<String> parameters = new Vector<>();

    try {
      parameters.add(familiar.getNombre());
      parameters.add(familiar.getTelefono());
      parameters.add(familiar.getDireccion());
      parameters.add(SQLFormatter.sqlID(familiar.getId()));
      query = "UPDATE FAMILIARES set" +
          " DOCTOR = ?," +
          " PACIENTE = ?," +
          " FECHA = ?" +
          " where ID = ?" +
          ";";

      dbConnection.dbUpdate(parameters, query);
      LOG.info(
          "Actualizar Familiar (ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
    } catch (Exception e) {
      LOG.info(
          "Actualizar Familiar - ERROR - Database insertion error (ID=%s Nombre=%s Fecha=%s)",
          familiar.getId(),
          familiar.getNombre(),
          Time.now());
    }
  }

  // FUNCIONES GET
  public Collection<Receta> getAllFamiliares() {
    String query;
    Vector<String> parameters = new Vector<>();

    query = "";
    return getFamiliares(parameters, query);
  }

  public Collection<Receta> getSingleFamiliarById(Uuid id) {
    String query;
    Vector<String> parameters = new Vector<>();

    parameters.add(SQLFormatter.sqlID(id));
    query = "ID = ?";

    return getFamiliares(parameters, query);
  }

  private Collection<Receta> getFamiliares(Vector<String> parameters, String where) {
    String query = "SELECT * FROM FAMILIARES";
    if (where != null)
      query += " where " + where;
    query += ";";
    return dbConnection.getRecetas(parameters, query);
  }
}
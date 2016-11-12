package models

import java.sql.{Connection, PreparedStatement, ResultSet}
import play.api.db._
import play.api.Play.current

/**
  * Created by ubuntu on 17/10/16.
  */
case class Docente(var nb_doc_id: Int, var vr_doc_celular: String, var vr_doc_nombre: String, var vr_doc_apellido: String)

object DocenteRepository {
  val Select = "SELECT nb_doc_id,vr_doc_celular,vr_doc_nombre,vr_doc_apellido FROM tb_docente ORDER BY vr_doc_apellido ASC"
  val SelectOne = "SELECT nb_doc_id,vr_doc_celular,vr_doc_nombre,vr_doc_apellido FROM tb_docente WHERE nb_doc_id= ?"
}


class Docentes {

  import DocenteRepository._


  def alguno(nb_doc_id: Int): Docente = {
    DB.withConnection() { connection =>
      val prepareStatement: PreparedStatement = connection.prepareStatement(SelectOne)
      prepareStatement.setInt(1, nb_doc_id)
      val resultSet = prepareStatement.executeQuery()
      var docente: Docente = null
      while (resultSet.next()) {
        docente = resultSetADocente(resultSet)
      }
      docente
    }
  }

  def traerTodo: Seq[Docente] = {
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      val rs = stmt.executeQuery("SELECT nb_doc_id,vr_doc_celular,vr_doc_nombre,vr_doc_apellido FROM tb_docente ORDER BY vr_doc_apellido ASC")

      var docentes: Seq[Docente] = Seq.empty

      while (rs.next) {
        val docente = new Docente(rs.getInt("nb_doc_id"),
          rs.getString("vr_doc_celular"),
          rs.getString("vr_doc_nombre"),
          rs.getString("vr_doc_apellido")
        )
        docentes = docentes :+ docente
      }
      docentes
    } finally {
      conn.close()
    }

  }

  private def resultSetADocente(fila: ResultSet): Docente = {
    new Docente(fila.getInt("nb_doc_id"),
      fila.getString("vr_doc_celular"),
      fila.getString("vr_doc_nombre"),
      fila.getString("vr_doc_apellido")
    )
  }

}
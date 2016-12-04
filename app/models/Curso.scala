package models

import java.sql.{Connection, Date, PreparedStatement, ResultSet}
import play.api.db._
import play.api.Play.current


/**
  * Created by acamilo.barrera on 28/11/16.
  */
case class Curso(var nb_cur_id: Int, var vr_cur_nombre: String, var active: Boolean, var nb_hora_curso: Int)
case class CursoInfo(var nb_cur_id: Int, var vr_cur_nombre: String, var active: Boolean, var nb_hora_curso: Int, var dt_fecha: Date, var nb_periodo: Int)


object CursoRepository {
  val Insert = "INSERT INTO curso(nb_cur_id,vr_cur_nombre,active,nb_hora_curso,dt_fecha,nb_periodo) values(?,?,?,?,?,?)"
}

class Cursos {

  import CursoRepository._

  def traerTodo: Seq[Curso] = {
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      val rs = stmt.executeQuery("SELECT nb_cur_id, vr_cur_nombre, active FROM tb_curso WHERE active = TRUE ORDER BY nb_cur_id DESC")

      var Curso: Seq[Curso] = Seq.empty

      while (rs.next) {
        val curso = new Curso(rs.getInt("nb_cur_id"),
          rs.getString("vr_cur_nombre"),
          rs.getBoolean("active"),
          rs.getInt ("nb_hora_curso")
        )
        Curso = Curso :+ curso
      }
      Curso
    } finally {
      conn.close()
    }

  }

  def traerNull: Seq[Curso] = {
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      val rs = stmt.executeQuery("SELECT nb_cur_id, vr_cur_nombre, active FROM tb_curso WHERE active = FALSE ORDER BY nb_cur_id DESC")

      var Curso: Seq[Curso] = Seq.empty

      while (rs.next) {
        val curso = new Curso(rs.getInt("nb_cur_id"),
          rs.getString("vr_cur_nombre"),
          rs.getBoolean("active"),
          rs.getInt ("nb_hora_curso")
        )
        Curso = Curso :+ curso
      }
      Curso
    } finally {
      conn.close()
    }



  }

  private def crearPrepareStatementGuardar(conexion: Connection, curso: Curso): PreparedStatement = {
    val preparedStatement = conexion.prepareStatement(Insert)
    preparedStatement.setInt(1, curso.nb_cur_id)
    preparedStatement.setString(2, curso.vr_cur_nombre)
    preparedStatement.setBoolean(3, curso.active)
    preparedStatement.setInt(4, curso.nb_hora_curso)
    preparedStatement
  }
}

class CursosInfo {

  import CursoRepository._

  def traerCursoInfo: Seq[CursoInfo] = {
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      val rs = stmt.executeQuery("SELECT nb_cur_id, vr_cur_nombre, active, nb_hora_curso, dt_fecha, nb_periodo FROM tb_curso LEFT JOIN tb_semestre_tb_curso ON tb_curso.nb_cur_id = tb_semestre_tb_curso.tb_curso_nb_cur_id LEFT JOIN tb_semestre ON tb_semestre_tb_curso.tb_curso_nb_cur_id = tb_semestre.nb_id_semestre ORDER BY nb_cur_id DESC")

      var CursoInfo: Seq[CursoInfo] = Seq.empty

      while (rs.next) {
        val cursoInfo = new CursoInfo(rs.getInt("nb_cur_id"),
          rs.getString("vr_cur_nombre"),
          rs.getBoolean("active"),
          rs.getInt ("nb_hora_curso"),
          rs.getDate ("dt_fecha"),
          rs.getInt ("nb_periodo")
        )
        CursoInfo = CursoInfo :+ cursoInfo
      }
      CursoInfo
    } finally {
      conn.close()
    }
  }

  private def crearPrepareStatementGuardar(conexion: Connection, cursoInfo: CursoInfo): PreparedStatement = {
    val preparedStatement = conexion.prepareStatement(Insert)
    preparedStatement.setInt(1, cursoInfo.nb_cur_id)
    preparedStatement.setString(2, cursoInfo.vr_cur_nombre)
    preparedStatement.setBoolean(3, cursoInfo.active)
    preparedStatement.setInt(4, cursoInfo.nb_hora_curso)
    preparedStatement.setBoolean(5, cursoInfo.active)
    preparedStatement.setInt(6, cursoInfo.nb_hora_curso)
    preparedStatement
  }
}
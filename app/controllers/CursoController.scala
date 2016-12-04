package controllers

import java.sql.Date

import models.Curso
import models.CursoInfo
import javax.inject._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._
import services.CursoService
import services.CursoInfoService

/**
  * Created by acamilo.barrera on 28/11/16.
  */
@Singleton
class CursoController @Inject()(cursoService: CursoService) extends Controller {
  implicit val cursoWrites: Writes[Curso] = (
    (JsPath \ "nb_cur_id").write[Int] and
      (JsPath \ "vr_cur_nombre").write[String] and
      (JsPath \ "active").write[Boolean] and
      (JsPath \ "nb_hora_curso").write[Int]
    ) (unlift(Curso.unapply))

  implicit val cursoReads: Reads[Curso] = (
    (JsPath \ "nb_cur_id").read[Int] and
      (JsPath \ "vr_cur_nombre").read[String] and
      (JsPath \ "active").read[Boolean] and
      (JsPath \ "nb_hora_curso").read[Int]
    ) (Curso.apply _)


  def curso = Action {
    val curso = cursoService.traerTodo
    val json = Json.toJson(curso)
    Ok(json)
  }

  def cursoNull = Action {
    val curso = cursoService.traerNull
    val json = Json.toJson(curso)
    Ok(json)
  }

}

@Singleton
class CursoInfoController @Inject()(cursoInfoService: CursoInfoService) extends Controller {
  implicit val cursoWrites: Writes[CursoInfo] = (
    (JsPath \ "nb_cur_id").write[Int] and
      (JsPath \ "vr_cur_nombre").write[String] and
      (JsPath \ "active").write[Boolean] and
      (JsPath \ "nb_hora_curso").write[Int] and
      (JsPath \ "dt_fecha").write[Date] and
      (JsPath \ "nb_periodo").write[Int]
    ) (unlift(CursoInfo.unapply))

  implicit val cursoReads: Reads[CursoInfo] = (
    (JsPath \ "nb_cur_id").read[Int] and
      (JsPath \ "vr_cur_nombre").read[String] and
      (JsPath \ "active").read[Boolean] and
      (JsPath \ "nb_hora_curso").read[Int] and
      (JsPath \ "dt_fecha").read[Date] and
      (JsPath \ "nb_periodo").read[Int]
    ) (CursoInfo.apply _)


  def cursoInfo = Action {
    val curso = cursoInfoService.traerCursoInfo
    val json = Json.toJson(curso)
    Ok(json)
  }
}
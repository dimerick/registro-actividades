package controllers

import javax.inject._

import models.{Docente, Profesor}
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import services.DocenteService

/**
  * Created by ubuntu on 17/10/16.
  */
@Singleton
class DocenteController @Inject()(docenteService: DocenteService) extends Controller {
  implicit val docenteWrites: Writes[Docente] = (
    (JsPath \ "nb_doc_id").write[Int] and
      (JsPath \ "vr_doc_celular").write[String] and
      (JsPath \ "vr_doc_nombre").write[String] and
      (JsPath \ "vr_doc_apellido").write[String] and
        (JsPath \ "vr_cur_nombre").write[String]
    ) (unlift(Docente.unapply))

  implicit val profesorWrites: Writes[Profesor] = (
    (JsPath \ "nb_doc_id").write[Int] and
      (JsPath \ "vr_doc_celular").write[String] and
      (JsPath \ "vr_doc_nombre").write[String] and
      (JsPath \ "vr_doc_apellido").write[String]

    ) (unlift(Profesor.unapply))

  implicit val docenteReads: Reads[Docente] = (
    (JsPath \ "nb_doc_id").read[Int] and
      (JsPath \ "vr_doc_celular").read[String] and
      (JsPath \ "vr_doc_nombre").read[String] and
      (JsPath \ "vr_doc_apellido").read[String] and
        (JsPath \ "vr_cur_nombre").read[String]
    ) (Docente.apply _)

  implicit val profesorReads: Reads[Profesor] = (
    (JsPath \ "nb_doc_id").read[Int] and
      (JsPath \ "vr_doc_celular").read[String] and
      (JsPath \ "vr_doc_nombre").read[String] and
      (JsPath \ "vr_doc_apellido").read[String]

    ) (Profesor.apply _)

  def consultarDocentes = Action {
    val docentes = docenteService.traerTodo
    val json = Json.toJson(docentes)
    Ok(json)
  }

  def consultarDocentesOrden = Action {
    val profesor = docenteService.traerTodoOrden
    val json = Json.toJson(profesor)
    Ok(json)
  }

  def consultarDocente(nb_doc_id:Int) = Action{
    val docente=docenteService.alguno(nb_doc_id)
    if(docente!=null)
      Ok(Json.toJson(docente))
    else
      NotFound(Json.obj("status" ->"NotFound", "message" -> ("No se encontro el docente "+ nb_doc_id)))
  }

}

package services

import javax.inject._

import models.{Docente, Docentes, Profesor}

/**
  * Created by ubuntu on 17/10/16.
  */
@Singleton
class DocenteService @Inject()(docentes: Docentes) {


  def alguno(nb_doc_id: Int): Docente = {
    docentes.alguno(nb_doc_id)
  }

  def traerTodo: Seq[Docente] = {
    docentes.traerTodo
  }

  def traerTodoOrden: Seq[Profesor] = {
    docentes.traerTodoOrden
  }

}

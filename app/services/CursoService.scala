package services

import javax.inject._
import java.sql.Date
import models.{Curso, Cursos}
import models.{CursoInfo, CursosInfo}

/**
  * Created by acamilo.barrera on 28/11/16.
  */
@Singleton
class CursoService @Inject()(cursos: Cursos) {

  def traerTodo: Seq[Curso] = {
    cursos.traerTodo
  }

  def traerNull: Seq[Curso] = {
    cursos.traerNull
  }

}

@Singleton
class CursoInfoService @Inject()(cursosInfo: CursosInfo) {

  def traerCursoInfo: Seq[CursoInfo] = {
    cursosInfo.traerCursoInfo
  }

}


package io.github.garook

import javax.xml.namespace.QName

trait PathMatcher {

  def matchPath(path: List[QName]): Boolean

}

class ExactLocalPathMatcher(path: String) extends PathMatcher {
  override def matchPath(path: List[QName]): Boolean = this.path == PathMatcher.toLocalStringPath(path)
}

class RegularExpressionLocalMatcher(expression: String) extends PathMatcher {
  private val regexp = expression.r

  override def matchPath(path: List[QName]): Boolean = regexp.findFirstMatchIn(PathMatcher.toLocalStringPath(path)).isDefined
}


object PathMatcher {

  private[garook] def toLocalStringPath(path: List[QName]) = path.map(_.getLocalPart).mkString(".")

}



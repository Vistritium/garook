package org.garook

import java.io.InputStream

import com.fasterxml.aalto.stax.InputFactoryImpl
import com.typesafe.scalalogging.LazyLogging
import javax.xml.namespace.QName
import javax.xml.stream.{XMLEventReader, XMLInputFactory}

import scala.collection.mutable

class Garook extends LazyLogging {

  def parse(
             inputStream: InputStream,
             pathMatcher: PathMatcher
           ): CloseableIterator[Array[Byte]] = {
    require(inputStream != null, "inputStream cannot be null")

    val fac = new InputFactoryImpl()
    fac.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
    val reader: XMLEventReader = fac.createXMLEventReader(inputStream)

    var nextElem: Option[Array[Byte]] = None

    var path = List.empty[QName]

    logger.trace(reader.hasNext.toString)

    def findNext(): Unit = {
      nextElem = None
      var finished = false
      while (reader.hasNext && !finished) {
        val event = new ElementWrapper(reader.nextEvent())

        event.asStartElement().foreach { startElement =>
          val name = startElement.getName.getLocalPart
          logger.trace(s"start $name. Current path: $path")
          path = startElement.getName :: path
        }

        event.asEndElement().foreach { endElement =>
          val name = endElement.getName.getLocalPart
          logger.trace(s"end $name. Current path: $path")
          path = path.tail
        }

        if (pathMatcher.matchPath(path.reverse)) {
          nextElem = Some(EventToBytes.readEvent(event, reader))
          path = path.tail
          finished = true
        }

      }

    }

    findNext()

    new CloseableIterator[Array[Byte]] {
      override def hasNext: Boolean = nextElem.nonEmpty

      override def next(): Array[Byte] = {
        if (!hasNext) throw new NoSuchElementException
        else {
          val r = nextElem.get
          findNext()
          r
        }

      }

      override def close(): Unit = reader.close()
    }

  }


}

package io.github.garook

import java.io.{ByteArrayOutputStream, OutputStreamWriter}

import javax.xml.stream.XMLEventReader

import scala.util.Using

private[garook] object EventToBytes {

  def readEvent(
                 startingEvent: ElementWrapper,
                 reader: XMLEventReader
               ): Array[Byte] = {
    var isFinished = false
    var depthCounter = 0

    val buffer = new ByteArrayOutputStream()
    Using(new OutputStreamWriter(buffer, "utf-8")) { writer =>
      try {
        startingEvent.event.writeAsEncodedUnicode(writer)
        while (!isFinished) {
          val event = new ElementWrapper(reader.nextEvent())
          if (event.asStartElement().isDefined) {
            depthCounter = depthCounter + 1
          } else if (event.asEndElement().isDefined && depthCounter == 0) {
            isFinished = true
          } else if (event.asEndElement().isDefined) {
            depthCounter = depthCounter - 1
          }

          event.event.writeAsEncodedUnicode(writer)

        }
      }
    }
    buffer.toByteArray
  }

}

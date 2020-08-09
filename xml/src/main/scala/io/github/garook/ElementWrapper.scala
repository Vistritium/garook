package io.github.garook

import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.events.{EndElement, StartElement, XMLEvent}

class ElementWrapper(val event: XMLEvent) {

  def name = {
    event.getEventType match {
      case XMLStreamConstants.START_ELEMENT => "START_ELEMENT"
      case XMLStreamConstants.END_ELEMENT => "END_ELEMENT"
      case XMLStreamConstants.SPACE => "SPACE"
      case XMLStreamConstants.CHARACTERS => "CHARACTERS"
      case XMLStreamConstants.PROCESSING_INSTRUCTION => "PROCESSING_INSTRUCTION"
      case XMLStreamConstants.CDATA => "CDATA"
      case XMLStreamConstants.COMMENT => "COMMENT"
      case XMLStreamConstants.ENTITY_REFERENCE => "ENTITY_REFERENCE"
      case XMLStreamConstants.START_DOCUMENT => "START_DOCUMENT"
      case n => s"unknown: $n"
    }
  }

  def asStartElement(): Option[StartElement] = {
    if (event.isStartElement) {
      Some(event.asInstanceOf[StartElement])
    } else None
  }

  def asEndElement(): Option[EndElement] = {
    if (event.isEndElement) {
      Some(event.asInstanceOf[EndElement])
    } else None
  }

}
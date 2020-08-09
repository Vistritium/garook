package org.garook

import java.io.ByteArrayInputStream

import org.scalatest.flatspec.AnyFlatSpec

import scala.jdk.CollectionConverters._
import scala.xml.XML
import org.scalatest.matchers.should.Matchers._

import scala.util.Using

class GarookTest extends AnyFlatSpec {

  case class TestCase(
                       resource: String,
                       matcher: PathMatcher,
                       testElemMatch: String,
                       expected: List[String]
                     )

  "testcases" should "work" in {
    val xml1case = TestCase(
      "1.xml",
      new ExactLocalPathMatcher("breakfast_menu.food"),
      "price",
      List("$5.95", "$7.95", "$8.95", "$4.50", "$6.95")
    )
    val cases: List[TestCase] = List(
      xml1case,
      xml1case.copy(resource = "2.xml"),

    )

    cases.foreach { testCase =>
      withClue(testCase.resource) {
        val garook = new Garook
        val iterator = garook.parse(
          getClass.getResourceAsStream(s"/xmls/${testCase.resource}"),
          testCase.matcher
        )
        iterator.asScala
          .map(xml => XML.load(new ByteArrayInputStream(xml)))
          .map(_ \ testCase.testElemMatch)
          .map(_.text)
          .toList should contain only (testCase.expected: _*)
      }
    }
  }

  "3.xml" should "work" in {

    val garook = new Garook
    val names = (Using(garook.parse(
      getClass.getResourceAsStream(s"/xmls/3.xml"),
      new RegularExpressionLocalMatcher(""".*\.component""")
    )) { iterator =>
      iterator.asScala
        .map(xml => XML.load(new ByteArrayInputStream(xml)))
        .flatMap { xml =>
          xml.attribute("name").getOrElse(Seq.empty).headOption.map(_.text)
        }.toSet
    }).get

    names should contain only ("FileTemplateManagerImpl", "RunManager", "TypeScriptGeneratedFilesManager", "AutoImportSettings", "ProjectCodeStyleSettingsMigration", "ExternalProjectsManager", "ChangeListManager", "RecentsManager", "ProjectViewState", "SpellCheckerSettings", "TaskManager", "PropertiesComponent", "CodeStyleSettingsInfer", "WindowStateProjectService", "ExternalProjectsData", "ProjectId")

  }

}

package Controller


import Model.{Coordinate, Goal, Jerm, LevelConfig, Objects, Obstacle, REPL, Spieler, Spielfeld, levelManager}
import org.scalamock.clazz.MockImpl.mock
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import os.Inherit.in

import scala.Right


class ControllerTest extends AnyWordSpec with Matchers with MockFactory {

  val controller = new Controller

  val expectedLevelConfig: LevelConfig = LevelConfig(
    "test-level",
    "A test level",
    "Collect all jerms",
    5, 5,
    Coordinate(0, 0),
    Goal(4, 4),
    Objects(List(Obstacle("stein", Coordinate(2, 3)), Obstacle("holz", Coordinate(2, 2))), List(Jerm(Coordinate(4, 3))))
  )

  "A Controller" should {

    "Starting and initialize correctly" should {

      "start a level correct" in {
        //val controller = new Controller

        controller.startLevel("test-level") shouldBe Right(expectedLevelConfig) // Erwartet ein erfolgreiches Laden des Levels (Right)
      }

      "initialize Player at correct coordinates from level config" in {
        controller.getGrid(expectedLevelConfig.start.x, expectedLevelConfig.start.y) shouldBe 'R'
      }

      "place obstacles at specified positions" in {
        expectedLevelConfig.objects.obstacles.foreach { obstacle =>
          controller.getGrid(obstacle.coordinates.x, obstacle.coordinates.y) shouldBe 'X'
        }
      }

      "place jerms at specified positions" in {
        expectedLevelConfig.objects.jerm.foreach { jerm =>
          controller.getGrid(jerm.coordinates.x, jerm.coordinates.y) shouldBe 'J'
        }
      }

      "place Goal marker at correct position" in {
        controller.getGrid(expectedLevelConfig.goal.x, expectedLevelConfig.goal.y) shouldBe ('G')
      }
    }

    "Move player correctly" should {

      "clear previous position with space" in {
        val posXOld = Spielfeld.getSpielerPos.get._1
        val posYOld = Spielfeld.getSpielerPos.get._2
        controller.movePlayer("forward")

        controller.getGrid(posXOld, posYOld) shouldBe ' '
      }

      "mark new position with R char" in {
        controller.getGrid(Spielfeld.getSpielerPos.get._1, Spielfeld.getSpielerPos.get._2) shouldBe('R')
      }

      "rotate player correctly" in {
        controller.turnRight()
        Spieler.direction shouldBe Spieler.Rechts
        controller.turnLeft()
        Spieler.direction shouldBe Spieler.Oben
      }

      "collect jerms when moving to their position" in {
        Spieler.eingesammelteJerms.size shouldBe 0

        expectedLevelConfig.objects.jerm.foreach { jerm =>
          Spielfeld.hinsetze(jerm.coordinates.x, jerm.coordinates.y - 1, 'R')
          Spieler.position = Some(Coordinate(jerm.coordinates.x, jerm.coordinates.y - 1))

          controller.movePlayer("forward")

          Spieler.einsammeln(Spieler.getPosition)
        }

        Spieler.eingesammelteJerms.size shouldBe 1
      }

      "remove jerm from grid" in {
        controller.movePlayer("forward")
        expectedLevelConfig.objects.jerm.foreach { jerm =>
          Spielfeld.get(jerm.coordinates.x, jerm.coordinates.y) shouldBe ' '
        }
      }

//      "collect multiple jerms" in {
//        expectedLevelConfig.objects.jerm.foreach { jerm =>
//          Spielfeld.hinsetze(jerm.coordinates.x, jerm.coordinates.y, 'R')
//
//          Spieler.einsammeln(Spieler.getPosition)
//        }
//
//        Spieler.eingesammelteJerms.size shouldBe 2
//      }

      "complete only if all jerms are collected" in {
        Spieler.eingesammelteJerms.size shouldBe expectedLevelConfig.objects.jerm.size //=1

        controller.isLevelComplete shouldBe true
      }

      "partial complection should return false" in {
        Spieler.eingesammelteJerms = Set() //setze zurück (eigentlich voll blöd, dass das geht -> da muss man sich was anderes überlegen)

        Spielfeld.hinsetze(expectedLevelConfig.goal.x, expectedLevelConfig.goal.y, 'R')

        controller.isLevelComplete shouldBe false
      }
    }
  }
}

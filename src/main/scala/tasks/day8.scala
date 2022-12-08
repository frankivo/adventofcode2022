package com.github.frankivo
package tasks

object day8 {
  def main(args: Array[String]): Unit = {
    println(s"Number of visible trees: ${visibleTrees.size}")
  }

  private case class Coordinate(x: Int, y: Int)

  private val grid: Seq[(Coordinate, Int)] = {
    util.get("day8.txt")
      .zipWithIndex
      .map(y => (y._2, y._1.zipWithIndex))
      .flatMap(i => i._2.map(j => (Coordinate(j._2, i._1), j._1.getNumericValue)))
  }

  private val gridSize: Int = grid.maxBy(_._1.x)._1.x

  private def visibleTrees: Set[Coordinate] = {
    (0 to gridSize).flatMap(i => {
      val x = visibleTreesRow(i)
      println(x.size)
      x
    }).toSet
  }

  private def visibleTreesRow(y: Int): Set[Coordinate] = {
    val row = grid.filter(_._1.y == y)

    val leftToRight = visibleTreesRow(row, 0)
    val rightToLeft = visibleTreesRow(row.reverse, gridSize)

    leftToRight ++ rightToLeft
  }

  private def visibleTreesRow(row: Seq[(Coordinate, Int)], edge: Int): Set[Coordinate] = {
    row
      .foldLeft((Seq[Coordinate](), 0)) {
        (result, current) => {
          if ((current._1.x == edge) || (current._2 > result._2))
            (result._1 :+ current._1, current._2)
          else result
        }
      }
      ._1.toSet
  }


}
package ru.timakden.sudoku

import org.tinylog.kotlin.Logger
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    try {
        require(args.size == 1) { "This program should be executed with 1 argument – file name" }

        val solution = SudokuSolutionExtractor.getSolutionFromFile(args.first())
        SudokuValidator.validate(solution)
    } catch (e: Exception) {
        Logger.error(e.message)
        exitProcess(1)
    }
}

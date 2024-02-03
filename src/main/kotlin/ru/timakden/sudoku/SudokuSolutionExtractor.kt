package ru.timakden.sudoku

import kotlin.io.path.*

object SudokuSolutionExtractor {
    private val ROW_REGEX = "([1-9],){8}[1-9]".toRegex()

    /**
     * Extracts a `List<List<Int>>` representing provided sudoku puzzle solution.
     *
     * @param filepath file name of the solution
     * @return `List<List<Int>>`
     * @exception SudokuException when failed to extract the solution or provided data is invalid
     */
    fun getSolutionFromFile(filepath: String): List<List<Int>> {
        val path = Path(filepath)
        if (path.notExists() || path.isDirectory())
            throw SudokuException("File ${path.name} doesn't exist or it's a directory")

        val solution = path.useLines { lines ->
            lines.mapIndexed { i, line ->
                if (!line.matches(ROW_REGEX))
                    throw SudokuException("Line ${i + 1} should match the regex \"$ROW_REGEX\", but it didn't: $line")
                line.split(',').map { it.toInt() }
            }.toList()
        }

        if (solution.size != 9)
            throw SudokuException("Input data has invalid number of lines: ${solution.size} (should be 9)")

        return solution
    }
}

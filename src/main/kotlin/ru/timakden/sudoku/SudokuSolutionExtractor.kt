package ru.timakden.sudoku

import java.io.File

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
        val file = File(filepath)
        if (!file.exists() || file.isDirectory)
            throw SudokuException("File ${file.name} doesn't exist or it's a directory")

        val solution = file.useLines { lines ->
            lines.mapIndexed { i, line ->
                if (!line.matches(ROW_REGEX))
                    throw SudokuException("Line ${i + 1} should match the regex \"$ROW_REGEX\", but it wasn't: $line")
                line.split(',').map { it.toInt() }
            }.toList()
        }

        if (solution.size != 9)
            throw SudokuException("Input data has invalid number of lines: ${solution.size} (should be 9)")

        return solution
    }
}

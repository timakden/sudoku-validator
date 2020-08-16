package ru.timakden.sudoku

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldNotThrowAnyUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage

class SudokuSpec : StringSpec({
    "SudokuSolutionExtractor should extract solution from file" {
        val filepath = this::class.java.classLoader.getResource("valid.txt")?.path ?: ""
        val expectedSolution = listOf(
            listOf(4, 3, 5, 2, 6, 9, 7, 8, 1),
            listOf(6, 8, 2, 5, 7, 1, 4, 9, 3),
            listOf(1, 9, 7, 8, 3, 4, 5, 6, 2),
            listOf(8, 2, 6, 1, 9, 5, 3, 4, 7),
            listOf(3, 7, 4, 6, 8, 2, 9, 1, 5),
            listOf(9, 5, 1, 7, 4, 3, 6, 2, 8),
            listOf(5, 1, 9, 3, 2, 6, 8, 7, 4),
            listOf(2, 4, 8, 9, 5, 7, 1, 3, 6),
            listOf(7, 6, 3, 4, 1, 8, 2, 5, 9)
        )

        shouldNotThrowAny { SudokuSolutionExtractor.getSolutionFromFile(filepath) } shouldBe expectedSolution
    }

    "SudokuSolutionExtractor should throw exception when file couldn't be opened" {
        val filepath = "abc.xyz"
        shouldThrow<SudokuException> {
            SudokuSolutionExtractor.getSolutionFromFile(filepath)
        } shouldHaveMessage "File abc.xyz doesn't exist or it's a directory"
    }

    "SudokuSolutionExtractor should throw exception when file contains invalid number of lines" {
        val filepath = this::class.java.classLoader.getResource("invalid_number_of_lines.txt")?.path ?: ""
        shouldThrow<SudokuException> {
            SudokuSolutionExtractor.getSolutionFromFile(filepath)
        } shouldHaveMessage "Input data has invalid number of lines: 8 (should be 9)"
    }

    "SudokuSolutionExtractor should throw exception when file contains invalid data" {
        val filepath = this::class.java.classLoader.getResource("contains_letter.txt")?.path ?: ""
        shouldThrow<SudokuException> {
            SudokuSolutionExtractor.getSolutionFromFile(filepath)
        } shouldHaveMessage "Line 6 should match the regex \"([1-9],){8}[1-9]\", but it wasn't: 9,5,1,7,X,3,6,2,8"
    }

    "SudokuValidator should validate solution" {
        val solution = listOf(
            listOf(4, 3, 5, 2, 6, 9, 7, 8, 1),
            listOf(6, 8, 2, 5, 7, 1, 4, 9, 3),
            listOf(1, 9, 7, 8, 3, 4, 5, 6, 2),
            listOf(8, 2, 6, 1, 9, 5, 3, 4, 7),
            listOf(3, 7, 4, 6, 8, 2, 9, 1, 5),
            listOf(9, 5, 1, 7, 4, 3, 6, 2, 8),
            listOf(5, 1, 9, 3, 2, 6, 8, 7, 4),
            listOf(2, 4, 8, 9, 5, 7, 1, 3, 6),
            listOf(7, 6, 3, 4, 1, 8, 2, 5, 9)
        )

        shouldNotThrowAnyUnit { SudokuValidator.validate(solution) }
    }

    "SudokuValidator should throw exception when solution contains duplicates in a row" {
        val solution = listOf(
            listOf(4, 3, 5, 2, 6, 9, 7, 8, 1),
            listOf(6, 8, 2, 5, 7, 1, 4, 9, 3),
            listOf(1, 9, 7, 7, 3, 4, 5, 6, 2),
            listOf(8, 2, 6, 1, 9, 5, 3, 4, 7),
            listOf(3, 7, 4, 6, 8, 2, 9, 1, 5),
            listOf(9, 5, 1, 8, 4, 3, 6, 2, 8),
            listOf(5, 1, 9, 3, 2, 6, 8, 7, 4),
            listOf(2, 4, 8, 9, 5, 7, 1, 3, 6),
            listOf(7, 6, 3, 4, 1, 8, 2, 5, 9)
        )

        shouldThrow<SudokuException> {
            SudokuValidator.validate(solution)
        } shouldHaveMessage "Row 3 contains duplicates: [1, 9, 7, 7, 3, 4, 5, 6, 2]"
    }

    "SudokuValidator should throw exception when solution contains duplicates in a column" {
        val solution = listOf(
            listOf(4, 3, 5, 2, 6, 9, 7, 8, 1),
            listOf(6, 8, 2, 5, 7, 1, 4, 9, 3),
            listOf(1, 9, 7, 8, 3, 4, 5, 6, 2),
            listOf(8, 2, 6, 1, 9, 5, 3, 4, 7),
            listOf(3, 7, 4, 6, 8, 2, 9, 1, 5),
            listOf(9, 5, 1, 7, 4, 3, 6, 2, 8),
            listOf(5, 1, 9, 2, 3, 6, 8, 7, 4),
            listOf(2, 4, 8, 9, 5, 7, 1, 3, 6),
            listOf(7, 6, 3, 4, 1, 8, 2, 5, 9)
        )

        shouldThrow<SudokuException> {
            SudokuValidator.validate(solution)
        } shouldHaveMessage "Column 4 contains duplicates: [2, 5, 8, 1, 6, 7, 2, 9, 4]"
    }

    "SudokuValidator should throw exception when solution contains duplicates in a 3x3 square" {
        val solution = listOf(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
            listOf(2, 3, 4, 5, 6, 7, 8, 9, 1),
            listOf(3, 4, 5, 6, 7, 8, 9, 1, 2),
            listOf(4, 5, 6, 7, 8, 9, 1, 2, 3),
            listOf(5, 6, 7, 8, 9, 1, 2, 3, 4),
            listOf(6, 7, 8, 9, 1, 2, 3, 4, 5),
            listOf(7, 8, 9, 1, 2, 3, 4, 5, 6),
            listOf(8, 9, 1, 2, 3, 4, 5, 6, 7),
            listOf(9, 1, 2, 3, 4, 5, 6, 7, 8)
        )

        shouldThrow<SudokuException> {
            SudokuValidator.validate(solution)
        } shouldHaveMessage "One of the 3x3 squares contains duplicates: [1, 2, 3, 2, 3, 4, 3, 4, 5]"
    }
})

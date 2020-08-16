package ru.timakden.sudoku

class SudokuException(
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

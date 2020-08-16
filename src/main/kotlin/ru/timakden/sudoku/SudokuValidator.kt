package ru.timakden.sudoku

object SudokuValidator {
    /**
     * Validates the provided sudoku puzzle solution using the following rules:
     *
     * * Rows shouldn't contain duplicates
     * * Columns shouldn't contain duplicates
     * * Internal 3x3 squares shouldn't contain duplicates
     *
     * @exception SudokuException when the provided solution is incorrect
     */
    fun validate(solution: List<List<Int>>) {
        // Validate rows
        solution.forEachIndexed { i, row ->
            if (row.size != row.distinct().size)
                throw SudokuException("Row ${i + 1} contains duplicates: $row")
        }

        // Validate columns
        for (col in 0..8) {
            val column = (0..8).map { row -> solution[row][col] }
            if (column.size != column.distinct().size)
                throw SudokuException("Column ${col + 1} contains duplicates: $column")
        }

        // Validate 3x3 squares
        for (i in 0..8 step 3) {
            for (j in 0..8 step 3) {
                val square = mutableListOf<Int>()
                for (row in i..i + 2) {
                    for (column in j..j + 2) {
                        square += solution[row][column]
                    }
                }

                if (square.size != square.distinct().size)
                    throw SudokuException("One of the 3x3 squares contains duplicates: $square")
            }
        }
    }
}

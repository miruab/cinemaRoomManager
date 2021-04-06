package cinema

import kotlin.math.round
import java.lang.Exception

fun main() {
    val hall = createBox()

    val sm = intArrayOf(0)

    do {
        showMenu()
        val selected = readLine()!!.toInt()
        if (selected == 1) printSchema(hall)
        if (selected == 2) buyTicket(hall, sm)
        if (selected == 3) showStats(hall, sm)
    } while (selected != 0)
}

fun createBox(): Array<CharArray> {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val columns = readLine()!!.toInt()

    var box = arrayOf<CharArray>()

    for (r in 1..rows) {
        val col = CharArray(columns)
        for (i in col.indices) {
            col[i] = 'S'
        }
        box += col
    }

    return box
}

fun showMenu(){
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun printSchema(box: Array<CharArray>) {
    println()
    println("Cinema:")
    var s = ""
    for (i in box[0].indices) {
        s += "${i+1} "
    }
    println("  ${s.trim()}")
    for (i in box.indices) {
        println("${i+1} ${box[i].joinToString(" ")}")
    }
    println()
}

fun calcCost(box: Array<CharArray>): Int{
    var cost = 0
    val rows = box.size
    val columns = box[0].size
    if (rows * columns <= 60) {
        cost = rows * columns * 10
    } else {
        val f = rows / 2
        cost = f * columns * 10 + (rows - f) * columns * 8
    }
    return cost
}

fun buyTicket(box: Array<CharArray>, sumArr: IntArray) {
    var row: Int = 1
    var column: Int = 1
    var rightInput = false
    do {
        try {
            println("Enter a row number:")
            row = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            column = readLine()!!.toInt()
            if (row > box.size || column > box[0].size){
                println("\nWrong input!\n")
            } else if (box[row-1][column-1] == 'B') {
                println("\nThat ticket has already been purchased!\n")
            }
            else {
                rightInput = true
            }
        } catch (e: Exception) {
            println("\nWrong input!\n")
        }
    } while(!rightInput)

    box[row-1][column-1] = 'B'
    println()
    val price = calcTicketPrice(box, row)
    sumArr[0] += price

    println("Ticket price: $$price")
    println()
}

fun calcTicketPrice(box: Array<CharArray>, row: Int ): Int {
    var cost = 0
    val rows = box.size
    val columns = box[0].size
    cost = if (rows * columns <= 60) {
        10
    } else {
        val f = rows / 2
        if (row <= f) 10 else 8
    }
    return cost
}

fun showStats(box: Array<CharArray>, sumArr: IntArray) {
    val allCntTickets = box.size * box[0].size
    val cntTickets = calcPurchasedTickets(box)
    val prcnt = if (allCntTickets == 0) 0.0 else round(1.0 * cntTickets / allCntTickets * 100 * 100) / 100.0
    println("Number of purchased tickets: $cntTickets")
    println("Percentage: ${"%.2f".format(prcnt)}%")
    println("Current income: $${sumArr[0]}")
    println("Total income: $${calcCost(box)}")
    println()
}

fun calcPurchasedTickets(box: Array<CharArray>): Int {
    var cnt = 0
    for (r in box) {
        for (c in r) {
            if(c == 'B'){
                cnt++
            }
        }
    }
    return cnt
}
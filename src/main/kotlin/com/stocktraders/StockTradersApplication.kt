package com.stocktraders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockTradersApplication

fun main(args: Array<String>) {
    runApplication<StockTradersApplication>(*args)
}

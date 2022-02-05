package com.stocktraders

import org.springframework.data.repository.CrudRepository

interface StockRepository : CrudRepository<Stock, Long>{
    fun findByStockCode(stockCode: String): Stock?
}
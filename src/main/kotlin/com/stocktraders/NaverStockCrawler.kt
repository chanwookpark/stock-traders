package com.stocktraders

import mu.KotlinLogging
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

const val URL_NAVER_STOCK: String = "https://finance.naver.com/item/main.nhn?code=%s"

@Service
class NaverStockCrawler @Autowired constructor(private val stockRepository: StockRepository) {

    private val logger = KotlinLogging.logger {}

    fun crawling(stockCode: String, baseAt: LocalDateTime): DailyStock {
        // 종목 정보 확인
        val stock = stockRepository.findByStockCode(stockCode) ?: throw RuntimeException("잘못된 종목 번호 - $stockCode")
        val document = Jsoup.connect(String.format(URL_NAVER_STOCK, stockCode)).get()
        val element = document.select("dl.blind").first()

        logger.debug { "[CrawlingRawSourceByNaver] 종목 시세 크롤링 - stockCode: $stockCode, data: ${element.html().trim()}" }

        return DailyStockBuilder(element.children(), stock).build(baseAt)
    }
}
package com.stocktraders

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@SpringBootTest
class NaverStockCrawlerTests @Autowired constructor(
    val naverStockCrawler: NaverStockCrawler,
    val stockRepository: StockRepository
) {
    @Test
    internal fun `네이버 크롤링 및 파싱 결과 확인 (성공 케이스)`() {
        // 삼성전자 데이터 임시 생성
        val stock = Stock(stockCode = "005930", stockName = "삼성전자", stockRegion = StockRegion.KOREA)
        stockRepository.save(stock)

        val result = naverStockCrawler.crawling(stock.stockCode, LocalDateTime.now())

        assertThat(result).isNotNull
    }
}
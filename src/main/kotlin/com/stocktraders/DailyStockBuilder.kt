package com.stocktraders

import mu.KotlinLogging
import org.jsoup.select.Elements
import java.time.LocalDateTime

class DailyStockBuilder(private val elements: Elements, private val stock: Stock) {

    private val logger = KotlinLogging.logger {}

    /**
     * 시세 정보 생성
     *
     * @param stockCode
     * @param baseAt
     * @return
     */
    fun build(baseAt: LocalDateTime): DailyStock {
        val stockIndex: String = elements[4].text()
        val indexes = stockIndex.split(" ") // 가격, dod는 한 텍스트로 내려와서 별도 처리

        logger.debug { "DailyStockParsed... ${indexes}" }

        return DailyStock(
            stock = stock,
            stockPrice = getNumberToDouble(indexes[1]),
            dodPrice = getDodPrice(indexes[3], indexes[4]),
            dodRate = getDodRate(indexes[5], indexes[6]),
            openPrice = getNumberToDouble(6, "시가"),
            todayUpperPrice = getNumberToDouble(7, "고가"),
            todayUnderPrice = getNumberToDouble(9, "저가"),
            volume = getNumberToLong(11, "거래량"),
            regAt = baseAt
        )
    }

    private fun getDodRate(symbol: String, rate: String): Double {
        return when (symbol) {
            "플러스" -> rate.toDouble()
            "마이너스" -> -rate.toDouble()
            else -> 0.0
        }
    }

    private fun getDodPrice(symbol: String, text: String): Double {
        val price = text.replace(",".toRegex(), "")
        return when (symbol) {
            "상승" -> price.toDouble()
            "하락" -> -price.toDouble() // 마이너스
            else -> 0.0
        }
    }

    private fun getNumberToDouble(index: Int, vararg prefix: String): Double {
        return getText(index, *prefix).toDouble()
    }

    private fun getNumberToDouble(text: String): Double {
        return text.replace(",", "").trim { it <= ' ' }.toDouble()
    }

    private fun getNumberToLong(index: Int, vararg prefix: String): Long {
        return getText(index, *prefix).toLong()
    }

    private fun getText(index: Int, vararg prefixes: String): String {
        var text: String = elements.get(index).text()
        for (prefix in prefixes) {
            text = text.replace(prefix, "")
        }
        return text.replace(",", "").trim { it <= ' ' }
    }
}
package com.stocktraders

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

/**
 * 종목
 */
@Entity
class Stock(
    @Id @GeneratedValue var id: Long? = null,
    var stockCode: String,
    var stockName: String,
    var stockRegion: StockRegion
)

enum class StockRegion {
    KOREA, USA
}

/**
 * 데일리 종목 변경 정보
 */
@Entity
class DailyStock (
    @Id @GeneratedValue var id: Long? = null,
    @ManyToOne var stock: Stock,
    var regAt: LocalDateTime,
    /**
     * 종가
     */
    var stockPrice: Double, // 미국 주식은 소수점까지라서
    /**
     * 전일대비가격
     */
    var dodPrice: Double,
    /**
     * 전일대비등락률
     */
    var dodRate: Double,
    /**
     * 시가
     */
    var openPrice: Double,
    /**
     * 고가
     */
    var todayUpperPrice: Double,
    /**
     * 저가
     */
    var todayUnderPrice: Double,
    /**
     * 거래량
     */
    var volume: Long,

)

/**
 * 개인 보유 종목.
 * 등록했을 때의 가격과 재무 정보 기록.
 */
@Entity
class MemberStock(
    @Id @GeneratedValue var id: Long? = null,
    @ManyToOne var stock: Stock,
    @ManyToOne var member: Member
)

/**
 * 개인
 */
@Entity
class Member(
    @Id @GeneratedValue var id: Long? = null,
    var memberName: String
)

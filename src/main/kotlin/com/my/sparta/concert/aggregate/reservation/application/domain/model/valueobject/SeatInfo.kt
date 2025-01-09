package com.my.sparta.concert.aggregate.reservation.application.domain.model.valueobject

import com.my.sparta.concert.aggregate.concert.application.domain.valueobject.SectorType

class SeatInfo(
    var sectorType: SectorType,
    var number: Int,
)

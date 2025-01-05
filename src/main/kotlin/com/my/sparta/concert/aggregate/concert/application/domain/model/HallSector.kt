package com.my.sparta.concert.aggregate.concert.application.domain.model

import com.my.sparta.concert.aggregate.concert.application.domain.valueobject.SectorType

class HallSector(
    sectorId: String,
    sectorType: SectorType,
    capacity: Int,
    hallId: String
)

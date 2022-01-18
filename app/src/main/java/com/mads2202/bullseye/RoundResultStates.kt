package com.mads2202.bullseye

enum class RoundResultStates(val points: Int) {
    BULLSEYE(10), HIT(5), ALMOST_MISSED(1), MISSED(-1)
}
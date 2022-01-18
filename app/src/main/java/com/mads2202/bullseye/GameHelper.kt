package com.mads2202.bullseye

import kotlin.math.absoluteValue

class GameHelper {
    companion object {
        fun generateNumber(): Int {
            return (0..100).random()
        }

        fun getRoundScore(result: Int, number: Int): Int {
            return when ((number - result).absoluteValue) {
                0 -> RoundResultStates.BULLSEYE.points
                in 1..3 -> RoundResultStates.HIT.points
                4, 5 -> RoundResultStates.ALMOST_MISSED.points
                else -> RoundResultStates.MISSED.points
            }
        }
    }
}
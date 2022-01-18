package com.mads2202.bullseye.data

object PlayersDao {
    private const val initScore = 0
    val players = mutableMapOf<String, Int>(
        "DeadShot" to 587,
        "HawkEye" to 1000,
        "Neo" to 9999
    )

    fun addPlayer(nickname: String) {
        if (!players.keys.contains(nickname)) {
            players[nickname] = initScore
        }
    }

    fun updatePlayerScore(name: String, score: Int) {
        if (score > players[name]!!) {
            players[name] = score
        }
    }

    fun clearAllPlayers() {
        players.clear()
    }
}
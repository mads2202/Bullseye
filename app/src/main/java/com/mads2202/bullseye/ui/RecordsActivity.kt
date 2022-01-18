package com.mads2202.bullseye.ui

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.bullseye.PlayersAdapter
import com.mads2202.bullseye.R
import com.mads2202.bullseye.data.PlayersDao

class RecordsActivity : Activity() {
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.records_activity)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rv = findViewById(R.id.records_rv)
        val adapter = PlayersAdapter(PlayersDao.players)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}
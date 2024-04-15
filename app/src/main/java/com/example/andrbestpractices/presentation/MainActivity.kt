package com.example.andrbestpractices.presentation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.andrbestpractices.R
import com.example.andrbestpractices.databinding.ActivityMainBinding
import com.example.andrbestpractices.domain.StarWarsViewModel
import com.example.andrbestpractices.model.StarWarsPeopleData
import com.example.andrbestpractices.ui.AirPlaneModeReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var starWarPeopleAdapter: StarWarPeopleAdapter

    private val viewModel: StarWarsViewModel by viewModels()

    private var airPlaneModeReceiver = AirPlaneModeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        starWarPeopleAdapter = StarWarPeopleAdapter(this)
        binding.rvStarWarPeople.adapter = starWarPeopleAdapter.withLoadStateAdapters(
                header = PagingHeaderLoadStateAdapter(starWarPeopleAdapter),
                footer = PagingLoadStateAdapter(starWarPeopleAdapter)
        )
        registerReceiver()
        setObserver()
    }

    private fun registerReceiver() {
        registerReceiver(airPlaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED)
            {
                viewModel.starWarFlow.collectLatest {
                    starWarPeopleAdapter.submitData(it)
                }
                viewModel.errorMessage.observe(this@MainActivity) {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
            header: PagingHeaderLoadStateAdapter<StarWarsPeopleData, StarWarPeopleAdapter.MyViewHolder> = PagingHeaderLoadStateAdapter(
                    starWarPeopleAdapter
            ),
            footer: PagingLoadStateAdapter<StarWarsPeopleData, StarWarPeopleAdapter.MyViewHolder> = PagingLoadStateAdapter(
                    starWarPeopleAdapter
            )
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.refresh
            footer.loadState = loadStates.append
        }

        return ConcatAdapter(header, this, footer)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneModeReceiver)
    }
}
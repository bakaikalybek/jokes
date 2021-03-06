package kg.bakai.sunrisejokes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.bakai.sunrisejokes.R
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator
import kg.bakai.sunrisejokes.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val jokesAdapter: Adapter by lazy { Adapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupAppbar()
        setupViews()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.refresh -> {
                jokesAdapter.submitList(null)
                viewModel.getJokes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAppbar() {
        with(binding) {
            setSupportActionBar(toolbar)
        }
    }


    private fun setupViews() {
        with(binding) {
            recycler.apply {
                setHasFixedSize(true)
                adapter = jokesAdapter
                itemAnimator = FadeInDownAnimator()
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            getJokes()

            jokes.observe(this@MainActivity, {
                jokesAdapter.submitList(it)
                binding.recycler.isVisible = true
            })

            loading.observe(this@MainActivity, {
                when(it) {
                    true -> {
                        binding.recycler.isVisible = false
                        binding.error.isVisible = false
                        binding.progress.isVisible = true
                    }
                    false -> binding.progress.isVisible = false
                }
            })

            error.observe(this@MainActivity, {
                binding.error.text = it
                binding.recycler.isVisible = false
                binding.error.isVisible = true
            })
        }
    }

}
package juniormourao.rickandmorty.presentation.character_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import juniormourao.rickandmorty.R
import juniormourao.rickandmorty.databinding.FragmentCharacterListBinding
import juniormourao.rickandmorty.presentation.adapters.CharacterListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.fragment_character_list) {
    private lateinit var characterListBinding: FragmentCharacterListBinding
    private val characterListViewModel: CharacterListViewModel by viewModels()
    private val characterListAdapter = CharacterListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListBinding = FragmentCharacterListBinding.bind(view)

        setupCharacterRecyclerView()
        collectFromViewModel()
    }

    private fun setupCharacterRecyclerView() {
        characterListBinding.apply {
            rvCharacters.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = characterListAdapter
            }
        }
    }

    private fun collectFromViewModel() {
        lifecycleScope.launch {
            characterListViewModel.charactersFlow
                .collectLatest {
                    characterListAdapter.submitData(it)
            }
        }
    }
}
import android.os.Bundle
import android.view.View
import com.ahuynh.muzimusicapp.databinding.FragmentArtistBinding
import com.ahuynh.muzimusicapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


class ArtistFragment : BaseFragment<FragmentArtistBinding>(FragmentArtistBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}